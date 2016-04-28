package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class PylonConstructionSitePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public PylonConstructionSitePercept(int xpos, int ypos) {
    super(Percepts.PylonConstructionSite, new Numeral(xpos), new Numeral(ypos));
  }
}
