package eisbw.debugger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CheatButtons extends JPanel implements ActionListener {

  private static final long serialVersionUID = 1L;

  private Color buttonBackground;
  private List<String> actions;
  
  /**
   * Constructor for buttons that inject cheats into the game.
   */
  public CheatButtons() {
    setLayout(new BorderLayout());
    
    JLabel label = new JLabel("Cheat actions", SwingConstants.CENTER);
    add(label,BorderLayout.NORTH);
    
    actions = new LinkedList<>();
    JButton resources = new JButton("Give resources");
    resources.setActionCommand("SHOW ME THE MONEY");
    resources.addActionListener(this);
    

    JButton mapview = new JButton("Show map");
    mapview.setActionCommand("black sheep wall");
    mapview.addActionListener(this);
    
    JButton godmode = new JButton("Enemy attacks deal 0 damage");
    godmode.setActionCommand("power overwhelming");
    godmode.addActionListener(this);
    
    buttonBackground = resources.getBackground();

    JPanel cheatbuttons = new JPanel();
    
    cheatbuttons.add(resources);
    cheatbuttons.add(godmode);
    cheatbuttons.add(mapview);
    
    add(cheatbuttons);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    actions.add(event.getActionCommand());
    JButton buttonPressed = (JButton) event.getSource();
    if (buttonPressed.getBackground().equals(Color.GRAY)) {
      buttonPressed.setBackground(buttonBackground);
    } else {
      buttonPressed.setBackground(Color.GRAY);
    }
  }

  public List<String> getActions() {
    return actions;
  }
  
  public void clean() {
    actions = new LinkedList<>();
  }
}
