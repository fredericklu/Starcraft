package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Mineral Field percept.
 *
 */
public class MineralFieldPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * @param id
   *          The id of the mineralfield.
   * @param resources
   *          The amount of resources left.
   * @param resourceGroup
   *          The resource group.
   * @param xpos
   *          The x-coordinate of the position.
   * @param ypos
   *          The y-coordiante of the position.
   */
  public MineralFieldPercept(int id, int resources, int resourceGroup, int xpos, int ypos) {
    super(Percepts.MINERALFIELD, new Numeral(id), new Numeral(resources),
        new Numeral(resourceGroup), new Numeral(xpos), new Numeral(ypos));
  }
}
