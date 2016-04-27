package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BwapiUtility;
import eisbw.percepts.IdleWorkerPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class IdleWorkersPerceiver extends Perceiver {
  private final BwapiUtility util;

  public IdleWorkersPerceiver(JNIBWAPI api, BwapiUtility util) {
    super(api);
    this.util = util;
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    List<Unit> units = api.getMyUnits();
    for (Unit unit : units) {
      if (unit.isIdle() && unit.getType().isWorker()) {
        percepts.add(new IdleWorkerPercept(util.getUnitName(unit)));
      }
    }

    return percepts;
  }
}
