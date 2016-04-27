package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class MapPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public MapPercept(int width, int height) {
    super(Percepts.Map, new Numeral(width), new Numeral(height));
  }
}
