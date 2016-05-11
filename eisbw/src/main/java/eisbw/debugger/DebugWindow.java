package eisbw.debugger;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import jnibwapi.JNIBWAPI;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DebugWindow extends JFrame {

  private Logger logger = Logger.getLogger("StarCraft Logger");
  private static final long serialVersionUID = 1L;

  SpeedSlider speedSlider;
  CheatButtons cheats;
  DrawButtons draw;

  /**
   * Constructs a debug window for the game.
   * @param game - the game data.
   */
  public DebugWindow(Game game) {
    setTitle("StarCraft GOAL debugger");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 800);

    JPanel contentPane = new JPanel();
    setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout());

    speedSlider = new SpeedSlider();
    contentPane.add(speedSlider, BorderLayout.NORTH);

    cheats = new CheatButtons();
    contentPane.add(cheats, BorderLayout.WEST);
    
    draw = new DrawButtons(game);
    contentPane.add(draw, BorderLayout.SOUTH);

    setVisible(true);

  }

  private ArrayList<String> getActions() {
    ArrayList<String> result = new ArrayList<>();
    result.addAll(cheats.getActions());
    cheats.clean();
    return result;
  }

  /**
   * Iterates over the debug options and executes.
   * @param bwapi - the API.
   */
  public void debug(JNIBWAPI bwapi) {
    Iterator<String> iter = getActions().iterator();
    while (iter.hasNext()) {
      bwapi.sendText(iter.next());
      iter.remove();
    }
    if (speedSlider.speedChanged()) {
      bwapi.setGameSpeed(speedSlider.getSpeed());
    }
    try {
      draw.draw(bwapi);
    } catch (NoTranslatorException exception) {
      logger.log(Level.WARNING, "No translator in draw function", exception);
    } catch (TranslationException exception) {
      logger.log(Level.WARNING, "Cannot translate in draw function", exception);
    }
  }
}
