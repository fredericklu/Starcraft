package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

/**
 * @author Danny & Harm - The ConstructionSite percept.
 *
 */
public class ConstructionSitePercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * @param xpos
   *          The x coordinate of the construction site.
   * @param ypos
   *          The y coordinate of the construction site.
   */
  public ConstructionSitePercept(int xpos, int ypos) {
    super(Percepts.CONSTRUCTIONSITE, new Numeral(xpos), new Numeral(ypos));
  }

  /**
   * @param xpos
   *          The x coordinate of the construction site.
   * @param ypos
   *          The y coordinate of the construction site.
   * @param isInRange
   *          Indicates whether the construction site is in range of a pylon.
   */
  public ConstructionSitePercept(int xpos, int ypos, Boolean isInRange) {
    super(Percepts.CONSTRUCTIONSITE, new Numeral(xpos), new Numeral(ypos),
        new TruthValue(isInRange));
  }
}
