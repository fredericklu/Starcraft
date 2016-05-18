package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class SelfPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public SelfPercept(int id, String type) {
    super(Percepts.Self, new Numeral(id), new Identifier(type));
  }
}