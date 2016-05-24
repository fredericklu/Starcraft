package eisbw;

import eis.EIDefaultImpl;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.configuration.Configuration;
import eisbw.translators.ParamEnumTranslator;
import eisbw.translators.RaceTypeTranslator;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StarcraftEnvironmentImpl extends EIDefaultImpl {

  private Logger logger = Logger.getLogger("StarCraft Logger");

  private static final long serialVersionUID = 1L;
  protected BwapiListener bwapiListener;
  private Configuration configuration;
  private Game game;

  /**
   * Constructor of the environment.
   */
  public StarcraftEnvironmentImpl() {
    super();
    installTranslators();
    game = new Game(this);
  }

  private void installTranslators() {
    Translator translatorfactory = Translator.getInstance();
    translatorfactory.registerParameter2JavaTranslator(new ParamEnumTranslator());
    translatorfactory.registerParameter2JavaTranslator(new RaceTypeTranslator());
  }

  @Override
  public void init(Map<String, Parameter> parameters) throws ManagementException {
    super.init(parameters);
    setState(EnvironmentState.PAUSED);
    Thread.currentThread().setPriority(3);

    try {
      configuration = new Configuration(parameters);
      addEntity("player");
      if (!"test".equals(configuration.getRace())) {
        bwapiListener = new BwapiListener(game, "true".equals(configuration.getDebugMode()));

        if (!WindowsTools.isProcessRunning("Chaoslauncher.exe")) {
          WindowsTools.startChaoslauncher(configuration.getRace(), configuration.getMap(),
              configuration.getScDir());
        }
      }
    } catch (Exception ex) {
      Logger.getLogger(StarcraftEnvironmentImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected LinkedList<Percept> getAllPerceptsFromEntity(String entity)
      throws NoEnvironmentException {
    return (LinkedList<Percept>) game.getPercepts(entity);
  }

  @Override
  protected boolean isSupportedByEnvironment(Action action) {
    return bwapiListener.getAction(action) != null;
  }

  @Override
  protected boolean isSupportedByType(Action action, String type) {
    return isSupportedByEnvironment(action);
  }

  @Override
  protected boolean isSupportedByEntity(Action action, String entity) {
    return bwapiListener.isSupportedByEntity(action, entity);
  }

  @Override
  protected Percept performEntityAction(String entity, Action action) throws ActException {
    bwapiListener.performEntityAction(entity, action);
    return null;
  }

  /**
   * Adds a unit to the environment.
   * 
   * @param unitName
   *          - the name of the unit
   * @param eisUnitType
   *          - the type of the unit
   */
  public void addToEnvironment(String unitName, String eisUnitType) {
    try {
      addEntity(unitName, eisUnitType);
    } catch (EntityException exception) {
      logger.log(Level.WARNING, "Could not add " + unitName + " to the environment", exception);
    }
  }

  /**
   * Deletes a unit from the environment.
   * 
   * @param unitName
   *          - the name of the unit
   */
  public void deleteFromEnvironment(String unitName) {
    try {
      Set<String> agents = getAssociatedAgents(unitName);
      deleteEntity(unitName);
      for (String agent : agents) {
        unregisterAgent(agent);
      }
    } catch (EntityException exception) {
      logger.log(Level.WARNING, "Could not delete " + unitName + " from the environment",
          exception);
    } catch (RelationException exception) {
      logger.log(Level.WARNING, "Exception when deleting entity from the environment", exception);
    } catch (AgentException exception) {
      logger.log(Level.WARNING, "Exception when deleting agent from the environment", exception);
    }
  }

}
