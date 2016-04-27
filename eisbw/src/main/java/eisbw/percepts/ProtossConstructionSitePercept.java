package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class ProtossConstructionSitePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public ProtossConstructionSitePercept(int xpos, int ypos) {
    super(Percepts.ProtossConstructionSite, new Numeral(xpos), new Numeral(ypos));
  }
}
