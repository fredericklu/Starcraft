package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Lands the flying unit on the specified location.
 *
 */
public class Land extends StarcraftMovableAction {

  /**
   * The Land constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Land(JNIBWAPI api) {
    super(api);
  }

  @Override
  public boolean canExecute(Unit unit, Action action) {
    return unit.isLifted();
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    int xpos = ((Numeral) parameters.get(0)).getValue().intValue();
    int ypos = ((Numeral) parameters.get(1)).getValue().intValue();
    unit.land(new Position(xpos, ypos, PosType.BUILD));
  }

  @Override
  public String toString() {
    return "land(x,y)";
  }
}
