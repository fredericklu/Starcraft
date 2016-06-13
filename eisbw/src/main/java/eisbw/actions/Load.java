package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Numeral;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public class Load extends StarcraftLoadingAction {

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
