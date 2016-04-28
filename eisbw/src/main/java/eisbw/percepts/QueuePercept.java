package eisbw.percepts;

import eis.iilang.Numeral;
import eis.iilang.Percept;

public class QueuePercept extends Percept {

  private static final long serialVersionUID = 1L;

  public QueuePercept(int queueSize) {
    super(Percepts.Queue, new Numeral(queueSize));
  }
}
