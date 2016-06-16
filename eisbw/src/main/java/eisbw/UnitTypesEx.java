package eisbw;

import jnibwapi.types.UnitType;

/**
 * @author Danny & Harm - Utility class for type checks.
 *
 */
public class UnitTypesEx {

  private UnitTypesEx() {
    // Private constructor as this class does not need to be instantiated.
  }

  /**
   * Check if a unit is a resource.
   * 
   * @param unitType
   *          - the type of the unit.
   * @return true when the unit is a resource, else false.
   */
  public static boolean isResourceType(UnitType unitType) {
    return isMineralField(unitType) || unitType.isRefinery()
        || "Resource Vespene Geyser".equals(unitType.getName());
  }

  /**
   * Check if a unit is a vespene geyser.
   * 
   * @param unitType
   *          - the type of the unit.
   * @return true iff the unit is a vespene geyser.
   */
  public static boolean isVespeneGeyser(UnitType unitType) {
    return "Resource Vespene Geyser".equals(unitType.getName());
  }

  /**
   * Check if a unit is a mineral field.
   * 
   * @param unitType
   *          - the type of the unit.
   * @return true iff the unit is a vespene geyser.
   */
  public static boolean isMineralField(UnitType unitType) {
    return "Resource Mineral Field".equals(unitType.getName())
        || "Resource Mineral Field Type 2".equals(unitType.getName())
        || "Resource Mineral Field Type 3".equals(unitType.getName());
  }
}
