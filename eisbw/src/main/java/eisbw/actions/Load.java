package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Numeral;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

/**
 * @author Danny & Harm - Loads a unit into a specified other unit.
 *
 */
public class Load extends StarcraftLoadingAction {

  /**
   * The Load constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Load(JNIBWAPI api) {
    super(api);
  }

  @Override
  public void execute(Unit unit, Action action) {
    Unit target = api.getUnit(((Numeral) action.getParameters().get(0)).getValue().intValue());
    unit.load(target, false);
  }

  @Override
  public String toString() {
    return "load(targetID)";
  }
}
