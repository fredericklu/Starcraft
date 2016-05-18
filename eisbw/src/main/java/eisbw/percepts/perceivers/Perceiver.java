package eisbw.percepts.perceivers;

import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;

import java.util.LinkedList;
import java.util.List;

public abstract class Perceiver implements IPerceiver {

  protected final JNIBWAPI api;

  public Perceiver(JNIBWAPI api) {
    this.api = api;
  }
  
  @Override
  public List<Parameter> getConditions() {
    return new LinkedList<>();
  }
}
