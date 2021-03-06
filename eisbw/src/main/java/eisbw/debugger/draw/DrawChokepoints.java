package eisbw.debugger.draw;

import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.ChokePoint;
import jnibwapi.JNIBWAPI;
import jnibwapi.util.BWColor;

/**
 * @author Danny & Harm.
 */
public class DrawChokepoints extends IDraw {

  /**
   * Draw the chokepoints on the map.
   * @param game - the game object
   */
  public DrawChokepoints(Game game) {
    super(game);
  }

  @Override
  protected void drawOnMap(JNIBWAPI bwapi) throws TranslationException {
    for (ChokePoint cp : bwapi.getMap().getChokePoints()) {
      bwapi.drawLine(cp.getFirstSide(), cp.getSecondSide(), BWColor.Yellow, false);
      bwapi.drawCircle(cp.getCenter(), (int) cp.getRadius(), BWColor.Red, false, false);
      bwapi.drawText(cp.getCenter(),
          "(" + cp.getCenter().getBX() + "," + cp.getCenter().getBY() + ")", false);
    }
  }

}
