package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Unloads all units.
 *
 */
public class UnloadAll extends StarcraftAction {

  /**
   * The UnloadAll constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public UnloadAll(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.isEmpty();
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.getType().getSpaceProvided() > 0;
  }

  @Override
  public void execute(Unit unit, Action action) {
    unit.unloadAll(false);
  }

  @Override
  public String toString() {
    return "unloadAll";
  }
}
