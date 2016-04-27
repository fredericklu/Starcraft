package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class IsMorphing extends Percept {

  private static final long serialVersionUID = 1L;

  public IsMorphing(String typeName, int id) {
    super(Percepts.IsMorphing, new Identifier(typeName), new Numeral(id));
  }
}
