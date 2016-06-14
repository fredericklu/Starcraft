package eisbw.actions;

import eis.iilang.Action;
import eisbw.BwapiUtility;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.TechType;

/**
 * @author Danny & Harm - Abstract class for all the actions.
 *
 */
public abstract class StarcraftAction {

  protected JNIBWAPI api;

  /**
   * The StarcraftAction constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public StarcraftAction(JNIBWAPI api) {
    this.api = api;
  }

  protected TechType getTechType(String type) {
    return BwapiUtility.getTechType(type);
  }

  /**
   * @param action
   *          The evaluated action.
   * @return A boolean which indicates whether the parameters of the action are
   *         valid.
   */
  public abstract boolean isValid(Action action);

  /**
   * @param unit
   *          The unit performing the action.
   * @param action
   *          The evaluated action.
   * @return A boolean which indicated wheter the specified unit can execute the
   *         action.
   */
  public abstract boolean canExecute(Unit unit, Action action);

  /**
   * @param unit
   *          The unit performing the action.
   * @param action
   *          The evaluated action.
   */
  public abstract void execute(Unit unit, Action action);

  @Override
  public abstract String toString();
}
