package eisbw.debugger;

import eisbw.Game;
import jnibwapi.JNIBWAPI;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DebugWindow extends JFrame {
  private static final long serialVersionUID = 1L;

  SpeedSlider speedSlider;
  CheatButtons cheats;
  DrawButtons draw;

  /**
   * Constructs a debug window for the game.
   * 
   * @param game
   *          - the game data.
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

  private List<String> getActions() {
    List<String> result = new LinkedList<>();
    result.addAll(cheats.getActions());
    cheats.clean();
    return result;
  }

  /**
   * Iterates over the debug options and executes.
   * 
   * @param bwapi
   *          - the API.
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
    draw.draw(bwapi);
  }
}