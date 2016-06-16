package eisbw.debugger;

/**
 * @author Danny & Harm.
 */
public enum Draw {
  CHOKEPOINTS("choke"), CONSTRUCTION_SITES("construct"), BASE_LOCATIONS("base"), BUILDING_DETAILS(
      "buildings");

  private String parameter;

  private Draw(String name) {
    parameter = name;
  }

  public String getName() {
    return parameter;
  }
}
