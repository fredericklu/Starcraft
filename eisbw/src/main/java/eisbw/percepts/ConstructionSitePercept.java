package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

public class ConstructionSitePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public ConstructionSitePercept(int xpos, int ypos) {
    super(Percepts.ConstructionSite, new Numeral(xpos), new Numeral(ypos));
  }

  public ConstructionSitePercept(int xpos, int ypos, Boolean isInRange) {
    super(Percepts.ConstructionSite, new Numeral(xpos), new Numeral(ypos),
        new TruthValue(isInRange));
  }
}
