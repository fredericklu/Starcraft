package eisbw.debugger.draw;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Translator;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.Game;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.util.BWColor;

import java.util.LinkedList;
import java.util.List;

public class DrawConstructionSite extends IDraw {

  public DrawConstructionSite(Game game) {
    super(game);
  }

  @Override
  protected void drawOnMap(JNIBWAPI api) throws NoTranslatorException, TranslationException {
    Translator translator = Translator.getInstance();
    List<Percept> percepts = game.getConstructionSites();
    for (Percept percept : percepts) {
      LinkedList<Parameter> params = percept.getParameters();
      int xpos = translator.translate2Java(params.get(0), Integer.class);
      int ypos = translator.translate2Java(params.get(1), Integer.class);
      api.drawBox(new Position(xpos, ypos, PosType.BUILD),
          new Position(xpos + 3, ypos + 3, PosType.BUILD), BWColor.Blue, false, false);
    }
  }

}
