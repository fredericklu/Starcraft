package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Gathering Percept.
 *
 */
public class GatheringPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * @param id
   *          The ID of the mineral which is being gathered.
   */
  public GatheringPercept(int id) {
    super(Percepts.GATHERING, new Numeral(id));
  }

}
