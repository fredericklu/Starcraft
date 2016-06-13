package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Rally Unit percept.
 *
 */
public class RallyUnitPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public RallyUnitPercept(int id) {
    super(Percepts.RALLYUNIT, new Numeral(id));
  }

}
