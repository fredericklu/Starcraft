package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Unit Amount percept.
 *
 */
public class UnitAmountPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * The UnitAmountPercept constructor.
   * 
   * @param name
   *          The name (type) of the unit.
   * @param count
   *          The amount of that specific unit.
   */
  public UnitAmountPercept(String name, int count) {
    super(Percepts.UNITAMOUNT, new Identifier(name), new Numeral(count));
  }
}
