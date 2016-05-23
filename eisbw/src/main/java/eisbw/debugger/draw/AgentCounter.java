package eisbw.debugger.draw;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;

public class AgentCounter extends IDraw {

  public AgentCounter(Game game) {
    super(game);
    toggle();
  }

  @Override
  protected void drawOnMap(JNIBWAPI api) throws NoTranslatorException, TranslationException {
    api.drawText(new Position(10, 10, PosType.PIXEL), "Agentcount: " + game.getAgentCount(), true);
  }

  @Override
  public void toggle() {
    toggle = true;
  }
}
