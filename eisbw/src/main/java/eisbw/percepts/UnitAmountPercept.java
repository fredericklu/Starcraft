package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class UnitAmountPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public UnitAmountPercept(String name, int count) {
    super(Percepts.UnitAmount, new Identifier(name), new Numeral(count));
  }
}
