package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

/**
 * @author Danny & Harm - The Upgrade percept.
 *
 */
public class UpgradePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public UpgradePercept(String upgrade) {
    super(Percepts.UPGRADING, new Identifier(upgrade));
  }

}
