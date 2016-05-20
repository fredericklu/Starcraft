package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;


public class DefensiveMatrixPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public DefensiveMatrixPercept(int health) {
    super(Percepts.DEFENSIVEMATRIX, new Numeral(health));
  }
}
