package eisbw;

import eis.iilang.Percept;
import eisbw.percepts.perceivers.BufferPerceiver;
import eisbw.percepts.perceivers.ConstructionSitePerceiver;
import eisbw.units.StarcraftUnit;
import eisbw.units.Units;
import jnibwapi.JNIBWAPI;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Game {

  protected volatile Map<String, LinkedList<Percept>> percepts;
  protected Units units;
  protected volatile LinkedList<Percept> constructionPercepts;
  protected StarcraftEnvironmentImpl env;

  /**
   * Constructor.
   * 
   * @param environment
   *          - the environment
   */
  public Game(StarcraftEnvironmentImpl environment) {
    units = new Units(environment);
    percepts = new HashMap<>();
    constructionPercepts = new LinkedList<>();
    env = environment;
  }

  /**
   * Updates the percepts.
   * 
   * @param bwapi
   *          - the game bridge
   */
  public void update(JNIBWAPI bwapi) {
    Map<String, LinkedList<Percept>> unitPerceptHolder = new HashMap<>();
    LinkedList<Percept> perceptHolder = getPercepts(bwapi);

    Map<String, StarcraftUnit> unitList = units.getStarcraftUnits();
    for (Entry<String, StarcraftUnit> unit : unitList.entrySet()) {
      LinkedList<Percept> thisUnitPercepts = new LinkedList<>(perceptHolder);
      thisUnitPercepts.addAll(constructionPercepts);
      thisUnitPercepts.addAll(unit.getValue().perceive());

      unitPerceptHolder.put(unit.getKey(), thisUnitPercepts);
    }

    percepts = unitPerceptHolder;
  }

  /**
   * updates the constructionsites in the game.
   * 
   * @param bwapi
   *          - the JNIBWAPI
   */
  public void updateConstructionSites(JNIBWAPI bwapi) {
    LinkedList<Percept> perceptHolder = new LinkedList<>();
    perceptHolder.addAll(new ConstructionSitePerceiver(bwapi).perceive());
    constructionPercepts = perceptHolder;
  }

  private LinkedList<Percept> getPercepts(JNIBWAPI bwapi) {
    LinkedList<Percept> perceptHolder = new LinkedList<>();
    perceptHolder.addAll(new BufferPerceiver(bwapi).perceive());
    return perceptHolder;
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
        return percepts.get(entity);
      }
    }
    return new LinkedList<>();
  }

  public Units getUnits() {
    return units;
  }

  public List<Percept> getConstructionSites() {
    return constructionPercepts;
  }

  /**
   * Clean the game data.
   */
  public void clean() {
    units.clean();
    percepts = new HashMap<>();
    constructionPercepts = new LinkedList<>();
  }

  public int getAgentCount() {
    return env.getAgents().size();
  }
}
