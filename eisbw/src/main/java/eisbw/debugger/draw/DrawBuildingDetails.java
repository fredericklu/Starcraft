package eisbw.debugger.draw;

import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.util.BWColor;

/**
 * @author Danny & Harm - The class which handles the drawing of the buildings
 *         of the dev. tool.
 *
 */
public class DrawBuildingDetails extends IDraw {

  /**
   * The DrawBuildingDetails constructor.
   * 
   * @param game
   *          The current game.
   */
  public DrawBuildingDetails(Game game) {
    super(game);
  }

  @Override
  protected void drawOnMap(JNIBWAPI api) throws TranslationException {
    // Draw "is working" box
    int barHeight = 18;

    for (Unit unit : api.getMyUnits()) {
      int total = 0;
      int done = 0;
      String txt = "";
      BWColor color = BWColor.Blue;
      if (unit.getRemainingResearchTime() > 0) {
        total = unit.getTech().getResearchTime();
        done = total - unit.getRemainingResearchTime();
        txt = unit.getTech().getName();
      }
      if (unit.isUpgrading()) {
        total = unit.getUpgrade().getUpgradeTimeBase();
        done = total - unit.getRemainingUpgradeTime();
        txt = unit.getUpgrade().getName();
      }

      if (total > 0) {
        int width = unit.getType().getTileWidth() * 32;

        Position start = new Position(unit.getPosition().getPX() - width / 2,
            unit.getPosition().getPY() - 30);
        Position end = new Position(start.getPX() + width, start.getPY() + barHeight);

        int progress = (int) ((double) done / (double) total * width);
        Position progressbar = new Position(start.getPX() + progress, start.getPY() + barHeight);
        api.drawBox(start, end, color, false, false);
        api.drawBox(start, progressbar, color, true, false);

        api.drawText(new Position(start.getPX() + 5, start.getPY() + 2), txt, false);
      }

    }
  }

  @Override
  public void draw(JNIBWAPI api) {
    api.drawHealth(toggle);
    api.drawTargets(toggle);
    api.drawIDs(toggle);
    super.draw(api);
  }
}
