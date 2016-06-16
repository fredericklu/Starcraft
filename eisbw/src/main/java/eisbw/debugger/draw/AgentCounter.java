package eisbw.debugger.draw;

import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;

/**
 * @author Danny & Harm.
 */
public class AgentCounter extends IDraw {

  /**
   * initializes the agentcounter.
   * @param game - the game object
   */
  public AgentCounter(Game game) {
    super(game);
    toggle();
  }

  @Override
  protected void drawOnMap(JNIBWAPI api) throws TranslationException {
    api.drawText(new Position(10, 10, PosType.PIXEL), "Agentcount: " + game.getAgentCount(), true);
  }

  @Override
  public void toggle() {
    toggle = true;
  }
}
