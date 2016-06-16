package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Sets a rally point on a specified unit.
 *
 */
public class SetRallyUnit extends StarcraftAction {

  /**
   * The SetRallyUnit constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public SetRallyUnit(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    if (parameters.size() == 1) { // type
      return parameters.get(0) instanceof Numeral;
    }

    return false;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.getType().isBuilding();
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    int unitId = ((Numeral) parameters.get(0)).getValue().intValue();
    unit.setRallyPoint(api.getUnit(unitId));
  }

  @Override
  public String toString() {
    return "setRallyUnit(targetId)";
  }
}
