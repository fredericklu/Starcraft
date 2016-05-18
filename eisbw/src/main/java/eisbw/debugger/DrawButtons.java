package eisbw.debugger;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import eisbw.Game;
import eisbw.debugger.draw.DrawBases;
import eisbw.debugger.draw.DrawChokepoints;
import eisbw.debugger.draw.DrawConstructionSite;
import eisbw.debugger.draw.IDraw;
import jnibwapi.JNIBWAPI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DrawButtons extends JPanel implements ActionListener {

  private static final long serialVersionUID = 1L;
  private Map<String, IDraw> draw;

  /**
   * Toggle switches to draw in the game.
   * 
   * @param game
   *          - the game data.
   */
  public DrawButtons(Game game) {
    draw = new HashMap<>();
    draw.put(Draw.CONSTRUCTION_SITES.getName(), new DrawConstructionSite(game));
    draw.put(Draw.CHOKEPOINTS.getName(), new DrawChokepoints(game));
    draw.put(Draw.BASE_LOCATIONS.getName(), new DrawBases(game));
    JButton buildButton = new JButton("Draw BuildLocations");
    buildButton.setActionCommand(Draw.CONSTRUCTION_SITES.getName());
    buildButton.addActionListener(this);
    JButton chokeButton = new JButton("Draw Chokepoints");
    chokeButton.setActionCommand(Draw.CHOKEPOINTS.getName());
    chokeButton.addActionListener(this);
    JButton baseButton = new JButton("Draw BaseLocations");
    baseButton.setActionCommand(Draw.BASE_LOCATIONS.getName());
    baseButton.addActionListener(this);

    add(baseButton);
    add(chokeButton);
    add(buildButton);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    draw.get(event.getActionCommand()).toggle();
  }

  /**
   * Draw on screen.
   * 
   * @param api
   *          - the API.
   * @throws NoTranslatorException
   *           iff there is no translator.
   * @throws TranslationException
   *           iff translation fails.
   */
  public void draw(JNIBWAPI api) {
    for (Entry<String, IDraw> drawable : draw.entrySet()) {
      drawable.getValue().draw(api);
    }
  }
}
