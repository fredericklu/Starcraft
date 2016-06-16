package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Chokepoint percept.
 *
 */
public class ChokepointPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * @param xpos
   *          The x coordinate of the chokepoint.
   * @param ypos
   *          The y coordinate of the chokepoint.
   */
  public ChokepointPercept(int xpos, int ypos) {
    super(Percepts.CHOKEPOINT, new Numeral(xpos), new Numeral(ypos));
  }
}
