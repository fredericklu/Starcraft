package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.TechType;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Ability which can be used on a specified unit.
 *
 */
public class UseOnTarget extends StarcraftAction {

  /**
   * The UseOnTarget constructor.
   * 
   * @param api
   *          The BWAPI.
   */
  public UseOnTarget(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean isValid(Action action) {
    LinkedList<Parameter> parameters = action.getParameters();

    if (parameters.size() == 2) { // type, targetId
      boolean isTechType = parameters.get(0) instanceof Identifier
          && getTechType(((Identifier) parameters.get(0)).getValue()) != null;
      return isTechType && parameters.get(1) instanceof Numeral;
    }
    return false;
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());
    return techType.isTargetsUnits(); // check if unit has Tech?
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());
    unit.useTech(techType, api.getUnit(((Numeral) parameters.get(1)).getValue().intValue()));
  }

  @Override
  public String toString() {
    return "abilityOnTarget(TechType, TargetId)";
  }
}
