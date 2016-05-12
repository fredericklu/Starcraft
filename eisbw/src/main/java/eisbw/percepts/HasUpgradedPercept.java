package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;

public class HasUpgradedPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public HasUpgradedPercept(String name) {
    super(Percepts.HasUpgraded, new Identifier(name));
  }
}
