package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Worker Activity percept.
 *
 */
public class WorkerActivityPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public WorkerActivityPercept(int id, String activity) {
    super(Percepts.WORKERACTIVITY, new Numeral(id), new Identifier(activity));
  }
}
