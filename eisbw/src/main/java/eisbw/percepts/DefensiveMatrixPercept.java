package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - Defensive Matrix percept.
 *
 */
public class DefensiveMatrixPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * @param health
   *          The amount of health left of the Defensive Matrix.
   */
  public DefensiveMatrixPercept(int health) {
    super(Percepts.DEFENSIVEMATRIX, new Numeral(health));
  }
}
