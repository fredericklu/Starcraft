package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

public class UnitPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public UnitPercept(boolean isFriendly, String type, int id, int health, int shields,
      boolean isFlying, int bx, int by) {
    super(Percepts.Unit, new TruthValue(isFriendly), new Identifier(type), new Numeral(id),
        new Numeral(health), new Numeral(shields), new TruthValue(isFlying), new Numeral(bx),
        new Numeral(by));
  }

}
