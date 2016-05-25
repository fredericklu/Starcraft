package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter.Type;

public class PerceptFilter {
  private String name;
  private Type type;

  public PerceptFilter(String name, Type type) {
    this.name = name;
    this.type = type;
  }
  
  public String getName() {
    return name;
  }
  
  public Type getType() {
    return type;
  }
}
