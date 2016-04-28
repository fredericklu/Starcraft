package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.QueuePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class QueuePerceiver extends UnitPerceiver {

  public QueuePerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> result = new ArrayList<>();
    result.add(new QueuePercept(unit.getTrainingQueueSize()));
    return result;
  }
}
