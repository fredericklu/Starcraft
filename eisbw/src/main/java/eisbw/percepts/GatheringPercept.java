package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class GatheringPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public GatheringPercept(int id) {
    super(Percepts.GATHERING, new Numeral(id));
  }

}
