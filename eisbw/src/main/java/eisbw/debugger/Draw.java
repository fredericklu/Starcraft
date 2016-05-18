package eisbw.debugger;

public enum Draw {
  CHOKEPOINTS("choke"), CONSTRUCTION_SITES("construct"), BASE_LOCATIONS("base");

  private String parameter;

  private Draw(String name) {
    parameter = name;
  }

  public String getName() {
    return parameter;
  }
}
