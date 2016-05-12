package eisbw.percepts.perceivers;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.VespeneGeyserPercept;
import eisbw.percepts.WorkerActivityPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.ArrayList;
import java.util.List;

public class WorkerPerceiver extends UnitPerceiver {

  public WorkerPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    for (Unit unit : this.api.getMyUnits()) {
      if (unit.getType().isWorker()) {
        if (unit.isGatheringGas()) {
          percepts.add(new WorkerActivityPercept(unit.getID(), "gatheringGas"));
        } else if (unit.isGatheringMinerals()) {
          percepts.add(new WorkerActivityPercept(unit.getID(), "gatheringMinerals"));
        } else if (unit.isConstructing()) {
          percepts.add(new WorkerActivityPercept(unit.getID(), "constructing"));
        } else if (unit.isIdle()) {
          percepts.add(new WorkerActivityPercept(unit.getID(), "idling"));
        }
      }
    }

    for (Unit u : api.getNeutralUnits()) {
      if (u.getType() == UnitType.UnitTypes.Resource_Vespene_Geyser) {
        Percept percept = new VespeneGeyserPercept(u.getID(), u.getResources(),
            u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
        percepts.add(percept);
      }
    }

    return percepts;
  }

  @Override
  public List<Parameter> getConditions() {
    List<Parameter> conditions = new ArrayList<>();

    if (unit.isCarryingGas() || unit.isCarryingMinerals()) {
      conditions.add(new Identifier("carrying"));
    }

    if (unit.isGatheringGas() || unit.isGatheringMinerals()) {
      conditions.add(new Identifier("gathering"));
    }

    if (unit.isConstructing()) {
      conditions.add(new Identifier("constructing"));
    }

    return conditions;
  }
}
