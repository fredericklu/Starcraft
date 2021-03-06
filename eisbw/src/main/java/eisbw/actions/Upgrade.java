package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UpgradeType;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Upgrades a specified upgrade.
 *
 */
public class Upgrade extends StarcraftAction {

  /**
   * The Upgrade constructor.
   * 
   * @param api
   *          The BWAPI.
   */
  public Upgrade(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    if (parameters.size() == 1) {
      return parameters.get(0) instanceof Identifier
          && getUpgradeType(((Identifier) parameters.get(0)).getValue()) != null;
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
    UpgradeType upgradeType = getUpgradeType(((Identifier) parameters.get(0)).getValue());

    unit.upgrade(upgradeType);

  }

  @Override
  public String toString() {
    return "upgrade(Type)";
  }
}
