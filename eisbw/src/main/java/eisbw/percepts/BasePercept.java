package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

/**
 * @author Danny & Harm - The Base percept.
 *
 */
public class BasePercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * @param xpos
   *          The x coordinate of the base location.
   * @param ypos
   *          The y coordinate of the base location.
   * @param isStart
   *          Indicates whether the base location is a starting location or not.
   * @param regionId
   *          The region ID of the base location.
   */
  public BasePercept(int xpos, int ypos, boolean isStart, int regionId) {
    super(Percepts.BASE, new Numeral(xpos), new Numeral(ypos), new TruthValue(isStart),
        new Numeral(regionId));
  }
}
