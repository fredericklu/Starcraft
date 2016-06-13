package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Numeral;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public class UnloadUnit extends StarcraftLoadingAction {

  public UnloadUnit(JNIBWAPI api) {
    super(api);
  }

  @Override
  public void execute(Unit unit, Action action) {
    Unit target = api.getUnit(((Numeral) action.getParameters().get(0)).getValue().intValue());
    unit.unload(target);
  }

  @Override
  public String toString() {
    return "unload(targetId)";
  }
}
