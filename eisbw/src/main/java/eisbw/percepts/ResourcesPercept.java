package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class ResourcesPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public ResourcesPercept(int minerals, int gas, int csupply, int tsupply) {
    super(Percepts.Resources, new Numeral(minerals), new Numeral(gas), new Numeral(csupply),
        new Numeral(tsupply));
  }
}
