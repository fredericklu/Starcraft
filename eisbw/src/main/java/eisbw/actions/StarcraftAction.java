package eisbw.actions;

import eis.exceptions.ActException;
import eis.iilang.Action;
import eisbw.BwapiUtility;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

public abstract class StarcraftAction {

  protected JNIBWAPI api;
  protected BwapiUtility utility;

  public StarcraftAction(JNIBWAPI api) {
    this.api = api;
    this.utility = new BwapiUtility(api);
  }

  public abstract boolean isValid(Action action);

  public abstract boolean canExecute(Unit unit, Action action);

  public abstract void execute(Unit unit, Action action) throws ActException;

  @Override
  public abstract String toString();
}
