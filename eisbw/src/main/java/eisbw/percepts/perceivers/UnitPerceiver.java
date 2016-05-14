package eisbw.percepts.perceivers;

import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public abstract class UnitPerceiver extends Perceiver {

  protected final Unit unit;

  public UnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api);
    this.unit = unit;
  }
  
  @Override
  public List<Parameter> getConditions() {
    return new ArrayList<>();
  }
}
