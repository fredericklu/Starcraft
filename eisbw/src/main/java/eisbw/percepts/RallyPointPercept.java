package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Rally Point percept.
 *
 */
public class RallyPointPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * The RallyPointPercept constructor.
   * 
   * @param xpos
   *          The x-coordinate of the rallypoint.
   * @param ypos
   *          The y-coordinate of the rallypoint.
   */
  public RallyPointPercept(int xpos, int ypos) {
    super(Percepts.RALLYPOINT, new Numeral(xpos), new Numeral(ypos));
  }

}
