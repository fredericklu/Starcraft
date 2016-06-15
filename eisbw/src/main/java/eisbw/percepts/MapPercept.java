package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Map percept.
 *
 */
public class MapPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * The MapPercept constructor.
   * 
   * @param width
   *          The width of the map
   * @param height
   *          The height of the map
   */
  public MapPercept(int width, int height) {
    super(Percepts.MAP, new Numeral(width), new Numeral(height));
  }
}
