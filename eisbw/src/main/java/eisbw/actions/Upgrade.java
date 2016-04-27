package eisbw.actions;

import eis.exceptions.ActException;
import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eisbw.BwapiBridge;
import eisbw.UnitTypesEx;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UpgradeType;

import java.util.LinkedList;
import java.util.logging.Logger;

public class Upgrade extends StarcraftAction {

  public Upgrade(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    if (parameters.size() == 1) {
      return parameters.get(0) instanceof Identifier
          && utility.getUpgradeType(((Identifier) parameters.get(0)).getValue()) != null;
    }
    return false;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    // TODO check unit type (can be buildings that can train units)
    return UnitTypesEx.isUpgradeCapable(unit.getType());
    // return type == UnitTypes.Terran_Command_Center || type ==
    // UnitTypes.Terran_Barracks;
  }

  @Override
  public void execute(Unit unit, Action action) throws ActException {
    Logger.getLogger(BwapiBridge.class.getName()).info("Upgrade execute");
    LinkedList<Parameter> parameters = action.getParameters();
    UpgradeType upgradeType = utility.getUpgradeType(((Identifier) parameters.get(0)).getValue());

    boolean result = unit.upgrade(upgradeType);
    if (!result) {
      throw new ActException(ActException.FAILURE);
    }
  }

  @Override
  public String toString() {
    return "upgrade(Type)";
  }
}
