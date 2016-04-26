package eisbw;

import eis.EIDefaultImpl;
import eis.eis2java.translation.Translator;
import eis.exceptions.ActException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eis.iilang.Percept;

import eisbw.actions.ActionProvider;
import eisbw.actions.StarcraftAction;
import eisbw.configuration.Configuration;
import eisbw.percepts.GameStartPercept;
import eisbw.percepts.MineralFieldPercept;
import eisbw.percepts.UnitPercept;
import eisbw.percepts.VespeneGeyserPercept;
import eisbw.percepts.perceivers.TotalResourcesPerceiver;
import eisbw.translators.ParamEnumTranslator;
import eisbw.translators.RaceTypeTranslator;
import eisbw.units.StarcraftUnit;
import eisbw.units.StarcraftUnitFactory;

import jnibwapi.BWAPIEventListener;
import jnibwapi.BaseLocation;
import jnibwapi.ChokePoint;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;
import jnibwapi.util.BWColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BWAPIBridge class.
 * This class is the implementation of EIS, it extends the EIS default implementation.
 */
public class BwapiBridge extends EIDefaultImpl {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(BwapiBridge.class.getName());
  public static final int TRAINING_QUEUE_MAX = 5;
  private final Thread apiThread;
  private static JNIBWAPI bwapi;
  private BwapiUtility bwApiUtility;
  private final StarcraftUnitFactory unitFactory;
  private final Map<String, Unit> units;
  private final Map<Integer, String> unitNames;
  private List<Percept> mapPercepts = null;
  private final ActionProvider actionProvider;
  private boolean gameStarted = false;

  protected Configuration configuration;

  private Map<Unit, Action> pendingActions = new HashMap<>();

  public static JNIBWAPI getGame() {
    return bwapi;
  }

  public static void main(String[] args) throws ManagementException {
    System.out.println("This program can be built by Maven, "
        + "please run with mvn deploy to create the environment.");
  }

  /**
   * Constructor for the BWAPIBridge.
   */
  public BwapiBridge() {
    super();
    installTranslators();
    this.units = new HashMap<>();
    this.unitNames = new HashMap<>();
    bwapi = new JNIBWAPI(bwApiListener, true);
    this.bwApiUtility = new BwapiUtility(bwapi);
    this.unitFactory = new StarcraftUnitFactory(bwapi, this.bwApiUtility);
    this.actionProvider = new ActionProvider();
    actionProvider.loadActions(bwapi);

    apiThread = new Thread() {
      @Override
      public void run() {
        bwapi.start();
      }
    };
  }

  private void installTranslators() {
    Translator translatorfactory = Translator.getInstance();
    translatorfactory.registerParameter2JavaTranslator(new ParamEnumTranslator());
    translatorfactory.registerParameter2JavaTranslator(new RaceTypeTranslator());
  }

  @Override
  public void init(Map<String, Parameter> parameters) throws ManagementException {
    super.init(parameters);
    apiThread.start();
    setState(EnvironmentState.PAUSED);

    try {
      configuration = new Configuration(parameters);
      addEntity("player");

      if (!WindowsTools.isProcessRunning("Chaoslauncher.exe")) {
        WindowsTools.startChaoslauncher(configuration.getRace(), 
            configuration.getMap(), configuration.get_sc_dir());
      }
    } catch (Exception ex) {
      Logger.getLogger(BwapiBridge.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  protected LinkedList<Percept> getAllPerceptsFromEntity(String entity)
      throws PerceiveException, NoEnvironmentException {

    LinkedList<Percept> percepts = new LinkedList<>();
    if (!gameStarted) {
      return percepts;
    }

    if (mapPercepts != null) {
      percepts.addAll(mapPercepts);
    }
    Unit unit = units.get(entity);
    if (unit != null) {
      StarcraftUnit scu = this.unitFactory.create(unit);
      percepts.addAll(scu.perceive());
    }

    percepts.addAll(new TotalResourcesPerceiver(bwapi).perceive());

    Map<UnitType, Integer> count = new HashMap<>();
    for (Unit myUnit : bwapi.getMyUnits()) {
      UnitType unitType = myUnit.getType();
      if (!count.containsKey(unitType)) {
        count.put(unitType, 0);
      }
      count.put(unitType, count.get(unitType) + 1);
    }
    for (UnitType unitType : count.keySet()) {
      percepts.add(new UnitPercept(unitType.getName(), count.get(unitType)));
    }

    for (Unit u : bwapi.getNeutralUnits()) {
      UnitType unitType = u.getType();
      if (u.isVisible()) {
        if (UnitTypesEx.isMineralField(unitType)) {
          MineralFieldPercept mineralfield = new MineralFieldPercept(u.getID(), 
              u.getResources(), u.getResourceGroup(),
              u.getPosition().getBX(), u.getPosition().getBY());
          percepts.add(mineralfield);
        } else if (UnitTypesEx.isVespeneGeyser(unitType)) {
          VespeneGeyserPercept mineralfield = new VespeneGeyserPercept(u.getID(), 
              u.getResources(), u.getResourceGroup(),
              u.getPosition().getBX(), u.getPosition().getBY());
          percepts.add(mineralfield);

        }
      }
    }

    percepts.add(new GameStartPercept());

    return percepts;
  }

  @Override
  protected boolean isSupportedByEnvironment(Action action) {
    return getAction(action) != null;
  }

  @Override
  protected boolean isSupportedByType(Action action, String string) {
    return isSupportedByEnvironment(action);
  }

  private StarcraftAction getAction(Action action) {
    return actionProvider.getAction(action.getName() + "/" + action.getParameters().size());
  }

  @Override
  protected boolean isSupportedByEntity(Action act, String name) {
    Unit unit = units.get(name);

    StarcraftAction action = getAction(act);

    // if action is invalid, we need to provide a failure message
    // (which can only be provided by performEntityAction, so return true in
    // that case)
    return !action.isValid(act) || action.canExecute(unit, act);
  }

  @Override
  protected synchronized Percept performEntityAction(String name, Action act) throws ActException {
    Unit unit = units.get(name);

    // cant act during construction
    if (unit.isBeingConstructed()) {
      return null;
    }

    if (!pendingActions.containsKey(unit)) {
      StarcraftAction action = getAction(act);
      // Action might be invalid
      if (action.isValid(act)) {
        pendingActions.put(unit, act);
      }

    }
    return null;

  }

  /**
   * Register a unit in the environment.
   * @param unit - the unit to be registered.
   * @throws RuntimeException - throws a runtimeexception when fails to identify the unit.
   */
  public void register(Unit unit) throws RuntimeException {
    String unitName = bwApiUtility.getUnitName(unit);
    units.put(unitName, unit);
    unitNames.put(unit.getID(), unitName);
    try {
      addEntity(unitName, bwApiUtility.getEisUnitType(unit));
    } catch (EntityException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public String requiredVersion() {
    return "0.5";
  }

  private final BWAPIEventListener bwApiListener = new BWAPIEventListener() {
    @Override
    public void connected() {

    }

    @Override
    public void matchStart() {
      logger.info("Game started...");

      // set game speed to 30 (0 is the fastest. Tournament speed is 20)
      // You can also change the game speed from within the game by
      // "/speed X" command.
      bwapi.setGameSpeed(5);
      bwapi.enableUserInput();

      bwapi.drawIDs(true);
      bwapi.drawHealth(true);
      bwapi.drawTargets(true);

      mapPercepts = new ArrayList<>();
      gameStarted = true;
      // jnibwapi.Map map = bwapi.getMap();
    }

    @Override
    public void matchFrame() {
      synchronized (BwapiBridge.this) {
        Iterator<Unit> it = pendingActions.keySet().iterator();
        while (it.hasNext()) {
          Unit unit = it.next();
          Action act = pendingActions.get(unit);

          StarcraftAction action = getAction(act);
          try {
            action.execute(unit, act);
          } catch (ActException ex) {
            logger.log(Level.WARNING, "Could not execute " + act.toProlog(), ex);
          }
          it.remove();
        }
      }
      bwapi.drawCircle(new Position(37, 7, PosType.BUILD), 20, BWColor.Blue, false, false);
      bwapi.drawCircle(new Position(60, 119, PosType.BUILD), 20, BWColor.Blue, false, false);
      for (ChokePoint cp : bwapi.getMap().getChokePoints()) {
        bwapi.drawLine(cp.getFirstSide(), cp.getSecondSide(), BWColor.Yellow, false);
        bwapi.drawCircle(cp.getCenter(), (int) cp.getRadius(), BWColor.Red, false, false);
        bwapi.drawText(cp.getCenter(), "(" + cp.getCenter().getBX() 
            + "," + cp.getCenter().getBY() + ")", false);
      }

      for (BaseLocation loc : bwapi.getMap().getBaseLocations()) {
        bwapi.drawCircle(loc.getCenter(), 75, BWColor.Purple, false, false);
        bwapi.drawText(loc.getPosition(), "(" + loc.getCenter().getBX() 
            + "," + loc.getCenter().getBY() + ")", false);
      }
    }

    @Override
    public void keyPressed(int id) {
    }

    @Override
    public void playerLeft(int id) {
    }

    @Override
    public void nukeDetect() {
    }
    
    @Override
    public void nukeDetect(Position pstn) {
    }

    @Override
    public void unitDiscover(int id) {
    }

    @Override
    public void unitEvade(int id) {
    }

    @Override
    public void unitShow(int id) {
    }

    @Override
    public void unitHide(int id) {
    }

    @Override
    public void unitCreate(int id) {
    }

    @Override
    public void unitDestroy(int id) {
      if (unitNames.containsKey(id)) {
        String unitName = unitNames.get(id);
        units.remove(unitName);
        try {
          deleteEntity(unitName);
        } catch (EntityException | RelationException ex) {
          throw new RuntimeException(ex);
        }
      }
    }

    @Override
    public void unitMorph(int id) {
      if (unitNames.containsKey(id)) {
        String unitName = unitNames.get(id);
        Unit unit = units.get(unitName);
        if (bwapi.getMyUnits().contains(unit)) {
          register(unit);
        }
      }
    }

    @Override
    public void sendText(String text) {
    }

    @Override
    public void receiveText(String text) {
    }

    @Override
    public void unitRenegade(int unitId) {
    }

    @Override
    public void saveGame(String gameName) {
    }

    @Override
    public void unitComplete(int unitId) {
      Unit unit = bwapi.getUnit(unitId);
      if (bwapi.getMyUnits().contains(unit)) {
        register(unit);
      }
    }

    @Override
    public void playerDropped(int playerId) {
    }

    @Override
    public void matchEnd(boolean winner) {
    }
  };
}
