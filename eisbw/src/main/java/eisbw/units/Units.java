package eisbw.units;

import eisbw.BwapiUtility;
import eisbw.StarcraftEnvironmentImpl;
import jnibwapi.Unit;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Units {

  protected Map<String, Unit> unitMap;
  protected Map<Integer, String> unitNames;
  protected Map<String, StarcraftUnit> starcraftUnits;
  protected StarcraftEnvironmentImpl environment;
  private Queue<Unit> uninitializedUnits;

  /**
   * Constructor.
   * 
   * @param environment
   *          - the SC environment
   */
  public Units(StarcraftEnvironmentImpl environment) {
    unitMap = new ConcurrentHashMap<>();
    unitNames = new ConcurrentHashMap<>();
    starcraftUnits = new ConcurrentHashMap<>();
    uninitializedUnits = new ConcurrentLinkedQueue<>();
    this.environment = environment;
  }

  /**
   * Adds a unit to the game data.
   * 
   * @param unit
   *          - the unit to add
   */
  public void addUnit(Unit unit, StarcraftUnitFactory factory) {
    String unitName = BwapiUtility.getUnitName(unit);
    unitMap.put(unitName, unit);
    unitNames.put(unit.getID(), unitName);
    starcraftUnits.put(unitName, factory.create(unit));
    uninitializedUnits.add(unit);
  }

  /**
   * Removes a unit from game data.
   * 
   * @param unitName
   *          - the unit name
   */
  public void deleteUnit(String unitName, int id) {
    unitMap.remove(unitName);
    unitNames.remove(id);
    starcraftUnits.remove(unitName);
    environment.deleteFromEnvironment(unitName);
  }

  public Map<String, Unit> getUnits() {
    return unitMap;
  }

  public Map<Integer, String> getUnitNames() {
    return unitNames;
  }

  public Map<String, StarcraftUnit> getStarcraftUnits() {
    return starcraftUnits;
  }

  /**
   * Clean units, let garbage collector remove the remains.
   */
  public void clean() {
    for (Entry<Integer, String> entry : unitNames.entrySet()) {
      deleteUnit(entry.getValue(), entry.getKey());
    }
  }

  public Queue<Unit> getUninitializedUnits() {
    return uninitializedUnits;
  }
}
