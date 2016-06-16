package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Attacking Percept.
 *
 */
public class Attacking extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * @param unit
   *          The which is attacking.
   * @param targetUnit
   *          The unit which is being attacked.
   * @param xpos
   *          The x coordinate of the unit.
   * @param ypos
   *          The y coordinate of the unit.
   */
  public Attacking(int unit, int targetUnit, int xpos, int ypos) {
    super(Percepts.ATTACKING, new Numeral(unit), new Numeral(targetUnit), new Numeral(xpos),
        new Numeral(ypos));
  }
}