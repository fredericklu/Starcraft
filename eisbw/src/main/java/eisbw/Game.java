package eisbw;

import eis.iilang.Percept;
import eisbw.percepts.MineralFieldPercept;
import eisbw.percepts.UnitAmountPercept;
import eisbw.percepts.VespeneGeyserPercept;
import eisbw.percepts.perceivers.ConstructionSitePerceiver;
import eisbw.units.StarcraftUnit;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Game {

  private volatile Map<String, LinkedList<Percept>> percepts;
  private Units units;
  private volatile LinkedList<Percept> constructionPercepts;

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
    Map<UnitType, Integer> count = new HashMap<>();

    for (Unit myUnit : bwapi.getMyUnits()) {
      UnitType unitType = myUnit.getType();
      if (!count.containsKey(unitType)) {
        count.put(unitType, 0);
      }
      count.put(unitType, count.get(unitType) + 1);
    }
    LinkedList<Percept> perceptHolder = new LinkedList<>();

    for (UnitType unitType : count.keySet()) {
      perceptHolder.add(new UnitAmountPercept(unitType.getName(), count.get(unitType)));
    }

    for (Unit u : bwapi.getNeutralUnits()) {
      UnitType unitType = u.getType();
      if (u.isVisible()) {
        if (UnitTypesEx.isMineralField(unitType)) {
          MineralFieldPercept mineralfield = new MineralFieldPercept(u.getID(), u.getResources(),
              u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
          perceptHolder.add(mineralfield);
        } else if (UnitTypesEx.isVespeneGeyser(unitType)) {
          VespeneGeyserPercept mineralfield = new VespeneGeyserPercept(u.getID(), u.getResources(),
              u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
          perceptHolder.add(mineralfield);

        }
      }
    }
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
}
