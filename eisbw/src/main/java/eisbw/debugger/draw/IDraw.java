package eisbw.debugger.draw;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.JNIBWAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Danny & Harm - The abstract class for the drawing classes.
 *
 */
public abstract class IDraw {

  protected Logger logger = Logger.getLogger("StarCraft Logger");

  protected boolean toggle = false;
  protected Game game;

  /**
   * The IDraw constructor.
   * 
   * @param game
   *          the current game.
   */
  public IDraw(Game game) {
    this.game = game;
  }

  protected abstract void drawOnMap(JNIBWAPI api) throws TranslationException;

  /**
   * Draw on the map.
   * 
   * @param api
   *          - the StarCraft API.
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

  /**
   * Handles the toggling of the drawing.
   */
  public void toggle() {
    toggle = !toggle;
  }
}
