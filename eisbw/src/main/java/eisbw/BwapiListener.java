package eisbw;

import eis.exceptions.ActException;
import eis.iilang.Action;
import eisbw.actions.ActionProvider;
import eisbw.actions.StarcraftAction;
import eisbw.debugger.DebugWindow;
import eisbw.units.StarcraftUnitFactory;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class BwapiListener extends BwapiEvents {

  private JNIBWAPI bwapi;
  private Game game;
  private ActionProvider actionProvider;
  private Map<Unit, Action> pendingActions;
  private StarcraftUnitFactory factory;
  private UpdateThread updateThread;
  private DebugWindow debug;
  private boolean debugmode;
  int count = 0;

  /**
   * Event listener for BWAPI.
   * 
   * @param game
   *          - the game data class
   * @param debugmode
   *          - true iff debugger should be attached
   */
  public BwapiListener(Game game, boolean debugmode) {
    bwapi = new JNIBWAPI(this, true);
    this.game = game;
    actionProvider = new ActionProvider();
    actionProvider.loadActions(bwapi);
    pendingActions = new ConcurrentHashMap<>();
    factory = new StarcraftUnitFactory(bwapi);
    this.debugmode = debugmode;

    new Thread() {
      @Override
      public void run() {
        bwapi.start();
      }
    }.start();

  }

  @Override
  public void matchStart() {
    // set game speed to 30 (0 is the fastest. Tournament speed is 20)
    // You can also change the game speed from within the game by
    // "/speed X" command.
    updateThread = new UpdateThread(game, bwapi);
    updateThread.start();
    game.updateConstructionSites(bwapi);
    bwapi.setGameSpeed(5);

    // START THE DEBUGGER.
    if (debugmode) {
      debug = new DebugWindow(game);
      bwapi.drawTargets(true);
      bwapi.enableUserInput();
      bwapi.setGameSpeed(30);
    }
  }

  @Override
  public void matchFrame() {
    Iterator<Entry<Unit,Action>> it = pendingActions.entrySet().iterator();
    while (it.hasNext()) {
      Entry<Unit,Action> entry = it.next();
      Unit unit = entry.getKey();
      Action act = entry.getValue();

      StarcraftAction action = getAction(act);
      action.execute(unit, act);

      it.remove();
    }

    if (debug != null) {
      debug.debug(bwapi);
    }

    if (count == 200) {
      game.updateConstructionSites(bwapi);
      count = 0;
    }
    count++;
  }

  @Override
  public void unitDestroy(int id) {
    if (game.getUnits().getUnitNames().containsKey(id)) {
      String unitName = game.getUnits().getUnitNames().get(id);
      game.getUnits().deleteUnit(unitName);
    }
  }

  @Override
  public void unitMorph(int id) {
    if (game.getUnits().getUnitNames().containsKey(id)) {
      String unitName = game.getUnits().getUnitNames().get(id);
      Unit unit = game.getUnits().getUnits().get(unitName);
      if (bwapi.getMyUnits().contains(unit)) {
        game.getUnits().morphUnit(unitName);
        game.getUnits().addUnit(unit, factory);
      }
    }
  }

  @Override
  public void matchEnd(boolean winner) {
    updateThread.terminate();
    try {
      updateThread.join();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
    pendingActions = new ConcurrentHashMap<>();
    debug.dispose();
    game.clean();
  }

  @Override
  public void unitComplete(int unitId) {
    Unit unit = bwapi.getUnit(unitId);
    if (bwapi.getMyUnits().contains(unit) && !game.getUnits().getUnitNames().containsKey(unitId)) {
      game.getUnits().addUnit(unit, factory);
    }
  }

  protected boolean isSupportedByEntity(Action act, String name) {
    Unit unit = game.getUnits().getUnits().get(name);

    StarcraftAction action = getAction(act);
    return action.isValid(act) && action.canExecute(unit, act);
  }

  public StarcraftAction getAction(Action action) {
    return actionProvider.getAction(action.getName() + "/" + action.getParameters().size());
  }

  /**
   * Adds an action to the action queue, the action is then executed on the next
   * frame.
   * 
   * @param name
   *          - the name of the unit.
   * @param act
   *          - the action.
   * @throws ActException
   *           - mandatory from EIS
   */
  public void performEntityAction(String name, Action act) throws ActException {
    Unit unit = game.getUnits().getUnits().get(name);

    // cant act during construction
    if (!unit.isBeingConstructed()) {
      StarcraftAction action = getAction(act);
      // Action might be invalid
      if (action.isValid(act)) {
        pendingActions.put(unit, act);
      }
    }

  }

}
