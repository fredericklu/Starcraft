package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Self percept.
 *
 */
public class SelfPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * @param id
   *          The (unique) ID of the unit.
   * @param type
   *          The type of the unit.
   * @param maxHealth
   *          The maximum amount of health of the unit.
   * @param maxShield
   *          The maximum amount of shield of the unit.
   * @param maxEnergy
   *          The maximum amount of energy of the unit.
   */
  public SelfPercept(int id, String type, int maxHealth, int maxShield, int maxEnergy) {
    super(Percepts.SELF, new Numeral(id), new Identifier(type), new Numeral(maxHealth),
        new Numeral(maxShield), new Numeral(maxEnergy));
  }
}