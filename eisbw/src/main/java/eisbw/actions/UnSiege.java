package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Unsieges a Siege Tank.
 *
 */
public class UnSiege extends StarcraftAction {

  /**
   * The Unsiege constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public UnSiege(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    return parameters.isEmpty();
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.isSieged();
  }

  @Override
  public void execute(Unit unit, Action action) {
    unit.unsiege();
  }

  @Override
  public String toString() {
    return "unsiege()";
  }
}
