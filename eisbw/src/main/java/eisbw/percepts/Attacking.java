package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class Attacking extends Percept {

  private static final long serialVersionUID = 1L;

  public Attacking(int unit, int targetUnit, int xpos, int ypos) {
    super(Percepts.ATTACKING, new Numeral(unit), new Numeral(targetUnit), new Numeral(xpos),
        new Numeral(ypos));
  }
}