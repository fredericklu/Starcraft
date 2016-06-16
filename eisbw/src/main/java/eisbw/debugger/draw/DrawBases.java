package eisbw.debugger.draw;

import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.BaseLocation;
import jnibwapi.JNIBWAPI;
import jnibwapi.util.BWColor;

/**
 * @author Harm & Danny.
 *
 */
public class DrawBases extends IDraw {

  /**
   * Draw the baselocations on the map.
   * @param game - the game object
   */
  public DrawBases(Game game) {
    super(game);
  }

  @Override
  protected void drawOnMap(JNIBWAPI api) throws TranslationException {
    for (BaseLocation base : api.getMap().getBaseLocations()) {
      api.drawCircle(base.getCenter(), 75, BWColor.Purple, false, false);
      if (base.isStartLocation()) {
        api.drawText(base.getCenter(), "Starting Location", false);
      }
    }
  }

}
