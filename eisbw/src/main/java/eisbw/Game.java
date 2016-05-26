package eisbw;

import eis.iilang.Percept;
import eisbw.percepts.perceivers.BufferPerceiver;
import eisbw.percepts.perceivers.ConstructionSitePerceiver;
import eisbw.percepts.perceivers.MapPerceiver;
import eisbw.percepts.perceivers.PerceptFilter;
import eisbw.units.StarcraftUnit;
import eisbw.units.Units;
import jnibwapi.JNIBWAPI;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class Game {

  protected volatile Map<String, List<Percept>> percepts;
  protected Units units;
  protected volatile Map<PerceptFilter, List<Percept>> constructionPercepts;
  protected StarcraftEnvironmentImpl env;

  private Map<PerceptFilter, List<Percept>> mapPercepts;
  private Map<String, Map<String, List<Percept>>> previous;
  private Map<String, Integer> hasPercepted;
  private Map<String, Map<String, List<Percept>>> previousHolder;

  /**
   * Constructor.
   * 
   * @param environment
   *          - the environment
   */
  public Game(StarcraftEnvironmentImpl environment) {
    units = new Units(environment);
    percepts = new HashMap<>();
    constructionPercepts = new HashMap<>();
    mapPercepts = new HashMap<>();
    previous = new HashMap<>();
    hasPercepted = new HashMap<>();
    previousHolder = new HashMap<>();
    env = environment;
  }

  /**
   * Update the map.
   * 
   * @param api
   *          - the API.
   */
  public void updateMap(JNIBWAPI api) {
    Map<PerceptFilter, List<Percept>> toReturn = new HashMap<>();
    new MapPerceiver(api).perceive(toReturn);
    mapPercepts = toReturn;
  }

  /**
   * Updates the percepts.
   * 
   * @param bwapi
   *          - the game bridge
   */
  public void update(JNIBWAPI bwapi) {
    Map<String, List<Percept>> unitPerceptHolder = new HashMap<>();
    Map<PerceptFilter, List<Percept>> perceptHolder = getPercepts(bwapi);

    Map<String, StarcraftUnit> unitList = units.getStarcraftUnits();
    for (Entry<String, StarcraftUnit> unit : unitList.entrySet()) {
      Map<PerceptFilter, List<Percept>> thisUnitPercepts = new HashMap<>(perceptHolder);
      thisUnitPercepts.putAll(constructionPercepts);
      thisUnitPercepts.putAll(mapPercepts);
      thisUnitPercepts.putAll(unit.getValue().perceive());

      unitPerceptHolder.put(unit.getKey(),
          (LinkedList<Percept>) translatePercepts(unit.getKey(), thisUnitPercepts));
    }

    percepts = unitPerceptHolder;
  }

  private List<Percept> translatePercepts(String unitName,
      Map<PerceptFilter, List<Percept>> thisUnitPercepts) {
    List<Percept> percept = new LinkedList<>();
    if (!previous.containsKey(unitName)) {
      previousHolder.put(unitName, new HashMap<>());
      previous.put(unitName, new HashMap<>());
      hasPercepted.put(unitName, 0);
    }
    for (Entry<PerceptFilter, List<Percept>> entry : thisUnitPercepts.entrySet()) {
      switch (entry.getKey().getType()) {
        case ALWAYS:
          percept.addAll(entry.getValue());
          break;
        case ONCE:
          if (!previous.get(unitName).containsKey(entry.getKey().getName())) {
            percept.addAll(entry.getValue());
            previousHolder.get(unitName).put(entry.getKey().getName(), entry.getValue());
          }
          break;
        case ON_CHANGE:
          handleOnChangePercept(entry, unitName, percept);
          break;
        case ON_CHANGE_NEG:
          Logger.getLogger("StarCraft logger").warning("Change with negation is not allowed.");
          break;
        default:
          break;
      }
    }
    return percept;
  }

  private void handleOnChangePercept(Entry<PerceptFilter, List<Percept>> entry, String unitName,
      List<Percept> percept) {
    if (previous.get(unitName).containsKey(entry.getKey().getName())) {
      if (!entry.getValue().equals(previous.get(unitName).get(entry.getKey().getName()))) {
        previousHolder.get(unitName).put(entry.getKey().getName(), entry.getValue());
        percept.addAll(entry.getValue());
      }
    } else {
      previousHolder.get(unitName).put(entry.getKey().getName(), entry.getValue());
      percept.addAll(entry.getValue());
    }
  }

  /**
   * updates the constructionsites in the game.
   * 
   * @param bwapi
   *          - the JNIBWAPI
   */
  public void updateConstructionSites(JNIBWAPI bwapi) {
    Map<PerceptFilter, List<Percept>> toReturn = new HashMap<>();
    new ConstructionSitePerceiver(bwapi).perceive(toReturn);
    constructionPercepts = toReturn;
  }

  private Map<PerceptFilter, List<Percept>> getPercepts(JNIBWAPI bwapi) {
    Map<PerceptFilter, List<Percept>> toReturn = new HashMap<>();
    new BufferPerceiver(bwapi).perceive(toReturn);
    return toReturn;
  }

  /**
   * Get the percepts of this unit.
   * 
   * @param entity
   *          - the name of the unit
   * @return the percepts
   */
  public List<Percept> getPercepts(String entity) {
    synchronized (percepts) {
      if (percepts.containsKey(entity)) {
        previous.put(entity, previousHolder.get(entity));
        return percepts.get(entity);
      }
    }
    return new LinkedList<>();
  }

  public Units getUnits() {
    return units;
  }

  /**
   * get the constructionSites.
   * 
   * @return the constructionsites.
   */
  public List<Percept> getConstructionSites() {
    List<Percept> perceptHolder = new LinkedList<>();
    for (List<Percept> percept : constructionPercepts.values()) {
      perceptHolder.addAll(percept);
    }
    return perceptHolder;
  }

  /**
   * Clean the game data.
   */
  public void clean() {
    units.clean();
    percepts = new HashMap<>();
    constructionPercepts = new HashMap<>();
    mapPercepts = new HashMap<>();
    previousHolder = new HashMap<>();
    previous = new HashMap<>();
  }

  public int getAgentCount() {
    return env.getAgents().size();
  }
}
