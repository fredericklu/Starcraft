package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Rally Point percept.
 *
 */
public class RallyPointPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public RallyPointPercept(int xpos, int ypos) {
    super(Percepts.RALLYPOINT, new Numeral(xpos), new Numeral(ypos));
  }

}
