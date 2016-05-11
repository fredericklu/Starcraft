package eisbw;

import eisbw.units.StarcraftUnit;
import eisbw.units.StarcraftUnitFactory;
import jnibwapi.Unit;

import java.util.HashMap;
import java.util.Map;

public class Units {

  private Map<String, Unit> unitMap;
  private Map<Integer, String> unitNames;
  private Map<String, StarcraftUnit> starcraftUnits;
  private StarcraftEnvironmentImpl environment;

  /**
   * Constructor.
   * 
   * @param environment
   *          - the SC environment
   */
  public Units(StarcraftEnvironmentImpl environment) {
    unitMap = new HashMap<>();
    unitNames = new HashMap<>();
    starcraftUnits = new HashMap<>();
    this.environment = environment;
  }

  /**
   * Adds a unit to the game data.
   * 
   * @param unit
   *          - the unit to add
   */
  public synchronized void addUnit(Unit unit, StarcraftUnitFactory factory) {
    String unitName = BwapiUtility.getUnitName(unit);
    unitMap.put(unitName, unit);
    unitNames.put(unit.getID(), unitName);
    starcraftUnits.put(unitName, factory.create(unit));
    environment.addToEnvironment(unitName, BwapiUtility.getEisUnitType(unit));
  }

  /**
   * Removes a unit from game data.
   * 
   * @param unitName
   *          - the unit name
   */
  public synchronized void deleteUnit(String unitName) {
    unitMap.remove(unitName);
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
    return new HashMap<>(starcraftUnits);
  }

  public void morphUnit(String unitName) {
    environment.deleteFromEnvironment(unitName);
  }

  /**
   * Clean units, let garbage collector remove the remains.
   */
  public void clean() {
    unitMap = new HashMap<>();
    unitNames = new HashMap<>();
    starcraftUnits = new HashMap<>();
  }
}
