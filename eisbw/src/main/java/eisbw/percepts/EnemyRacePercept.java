package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Percept;


public class EnemyRacePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public EnemyRacePercept(String name) {
    super(Percepts.ENEMYRACE, new Identifier(name));
  }
}
