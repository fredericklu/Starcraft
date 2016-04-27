package eisbw;

import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;
import jnibwapi.types.UpgradeType;
import jnibwapi.types.UpgradeType.UpgradeTypes;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;

public class BwapiUtility {

  public JNIBWAPI bwapi;
  private final HashMap<String, UnitType> unitTypeMap = new HashMap<>();
  private final HashMap<String, TechType> techTypeMap = new HashMap<>();
  private final HashMap<String, UpgradeType> upgradeTypeMap = new HashMap<>();

  public BwapiUtility(JNIBWAPI api) {
    this.bwapi = api;
  }

  /**
   * Calculate the distance between two units.
   * @param unitId - the ID of unit 1.
   * @param otherUnitId - the ID of unit 2.
   * @return the distance between the two units.
   */
  public double distanceSq(int unitId, int otherUnitId) {
    Unit unit = this.bwapi.getUnit(unitId);
    Unit otherUnit = this.bwapi.getUnit(otherUnitId);
    Point2D p1 = new Point(unit.getPosition().getBX(), unit.getPosition().getBY());
    Point2D p2 = new Point(otherUnit.getPosition().getBX(), otherUnit.getPosition().getBY());

    return p1.distanceSq(p2);
  }

  /**
   * Get the name of a unit.
   * @param unit - the unit that has to be named.
   * @return the name of the unit.
   */
  public String getUnitName(Unit unit) {
    String name = (unit.getType().getName() + unit.getID()).replace("_", "").replace(" ", "");
    name = name.substring(0, 1).toLowerCase() + name.substring(1);
    return name;
  }

  /**
   * Get the EIS unittype of a unit.
   * @param unit - the unit that you want yhe Type from.
   * @return the type of a unit.
   */
  public String getEisUnitType(Unit unit) {
    String type = unit.getType().getName().replace(" ", "");
    type = type.substring(0, 1).toLowerCase() + type.substring(1);
    return type;
  }

  /**
   * Convert EIS type to unit.
   * @param type - the type to be converted.
   * @return the unit.
   */
  public UnitType getUnitType(String type) {
    if (this.unitTypeMap.isEmpty()) {
      for (UnitType ut : UnitTypes.getAllUnitTypes()) {
        unitTypeMap.put(ut.getName(), ut);
      }
    }

    return this.unitTypeMap.get(type);
  }

  /**
   * Convert type string to a techtype.
   * @param type - the string to be converted.
   * @return a techtype.
   */
  public TechType getTechType(String type) {
    if (this.techTypeMap.isEmpty()) {
      for (TechType tt : TechTypes.getAllTechTypes()) {
        techTypeMap.put(tt.getName(), tt);
      }
    }

    return this.techTypeMap.get(type);
  }

  /**
   * Convert a string to a upgradetype.
   * @param type - the string to be converted.
   * @return a upgradetype.
   */
  public UpgradeType getUpgradeType(String type) {
    if (this.upgradeTypeMap.isEmpty()) {
      for (UpgradeType tt : UpgradeTypes.getAllUpgradeTypes()) {
        upgradeTypeMap.put(tt.getName(), tt);
      }
    }

    return this.upgradeTypeMap.get(type);
  }

}
