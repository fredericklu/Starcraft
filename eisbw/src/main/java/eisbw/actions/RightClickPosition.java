package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Right clicks on a specified unit.
 *
 */
public class RightClickPosition extends StarcraftMovableAction {

  /**
   * The RightClickPosition constructor.
   * 
   * @param api The BWAPI
   */
  public RightClickPosition(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return true;
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    int xpos = ((Numeral) parameters.get(0)).getValue().intValue();
    int ypos = ((Numeral) parameters.get(1)).getValue().intValue();

    Position pos = new Position(xpos, ypos, Position.PosType.BUILD);
    unit.rightClick(pos, false);
  }

  @Override
  public String toString() {
    return "rightClick(x,y)";
  }
}
