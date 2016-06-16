package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Stops a unit from what it was doing.
 *
 */
public class Stop extends StarcraftAction {

  /**
   * The Stop constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Stop(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.isEmpty();
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    UnitType unitType = unit.getType();
    return !unitType.isBuilding();
  }

  @Override
  public void execute(Unit unit, Action action) {
    unit.stop(false);
  }

  @Override
  public String toString() {
    return "stop";
  }
}
