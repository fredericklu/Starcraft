package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class StatusPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public StatusPercept(int chealth, int cshields, int xpos, int ypos) {
    super(Percepts.STATUS, new Numeral(chealth), new Numeral(cshields), new Numeral(xpos),
        new Numeral(ypos));
  }

}
