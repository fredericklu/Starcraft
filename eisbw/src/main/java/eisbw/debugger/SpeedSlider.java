package eisbw.debugger;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Danny & Harm - Class which handles the Speedslider of the dev. tool.
 *
 */
public class SpeedSlider extends JPanel {

  private static final long serialVersionUID = 1L;

  private int initialSpeed = 20;
  private int slowest = 50;
  private int fastest = 0;

  private boolean changed = false;

  private int speed = initialSpeed;

  /**
   * Slider to change gamespeed.
   */
  public SpeedSlider() {
    setPreferredSize(new Dimension(500, 100));

    JLabel showSpeed = new JLabel("Current speed: 20");

    final JSlider slider = new JSlider(JSlider.HORIZONTAL, fastest, slowest, initialSpeed);
    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent event) {
        changed = true;
        speed = slowest - slider.getValue();
        showSpeed.setText("Current speed: " + slider.getValue());
      }
    });

    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    JButton defaultSpeed = new JButton("Default speed");
    defaultSpeed.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        slider.setValue(20);
      }
    });

    JButton tournamentSpeed = new JButton("Tournament speed");
    tournamentSpeed.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        slider.setValue(30);
      }
    });

    JLabel label = new JLabel("Game Speed");
    add(label);
    add(slider);

    add(showSpeed);
    add(defaultSpeed);
    add(tournamentSpeed);
  }

  /**
   * The on change function.
   * 
   * @return true iff changed.
   */
  public boolean speedChanged() {
    if (changed) {
      changed = false;
      return true;
    }
    return false;
  }

  public int getSpeed() {
    return speed;
  }
}
