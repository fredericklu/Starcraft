package eisbw.debugger.draw;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.JNIBWAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class IDraw {

  private Logger logger = Logger.getLogger("StarCraft Logger");

  protected boolean toggle = false;
  protected Game game;

  public IDraw(Game game) {
    this.game = game;
  }

  protected abstract void drawOnMap(JNIBWAPI api)
      throws NoTranslatorException, TranslationException;

  /**
   * Draw on the map.
   * @param api - the StarCraft API.
   */
  public void draw(JNIBWAPI api) {
    if (toggle) {
      try {
        drawOnMap(api);
      } catch (NoTranslatorException exception) {
        logger.log(Level.WARNING, "No translator in draw function", exception);
      } catch (TranslationException exception) {
        logger.log(Level.WARNING, "Cannot translate in draw function", exception);
      }
    }
  }

  public void toggle() {
    toggle = !toggle;
  }
}
