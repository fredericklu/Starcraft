package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.List;

public class ProducerUnitPerceiver extends UnitPerceiver {

  public ProducerUnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
