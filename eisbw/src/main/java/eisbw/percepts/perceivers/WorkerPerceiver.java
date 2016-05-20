package eisbw.percepts.perceivers;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.RepairPercept;
import eisbw.percepts.VespeneGeyserPercept;
import eisbw.percepts.WorkerActivityPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import java.util.LinkedList;
import java.util.List;

public class WorkerPerceiver extends UnitPerceiver {

  public WorkerPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  /**
   * Perceives the workerActivity percept.
   * 
   * @param percepts
   *          The list of percepts
   * @param unit
   *          the evaluated terran worker
   * @return the new list of percepts
   */
  private void perceiveWorkerActivity(List<Percept> percepts, Unit unit) {
    if (unit.isGatheringGas()) {
      percepts.add(new WorkerActivityPercept(unit.getID(), "gatheringGas"));
    } else if (unit.isGatheringMinerals()) {
      percepts.add(new WorkerActivityPercept(unit.getID(), "gatheringMinerals"));
    } else if (unit.isConstructing()) {
      percepts.add(new WorkerActivityPercept(unit.getID(), "constructing"));
    } else {
      percepts.add(new WorkerActivityPercept(unit.getID(), "idling"));
    }
  }

  /**
   * Perceives the repair percept.
   * 
   * @param percepts
   *          The list of percepts
   * @param unit
   *          the evaluated terran worker
   * @return the new list of percepts
   */
  private void perceiveRepair(List<Percept> percepts, Unit unit) {
    int hp = unit.getHitPoints();
    int maxHp = unit.getType().getMaxHitPoints();
    if (hp < maxHp) {
      percepts.add(new RepairPercept(unit));
    }
  }

  /**
   * Perceives all the worker percepts minus the terran worker percepts.
   * 
   * @param percepts
   *          The list of percepts
   */
  private void perceiveWorkers(List<Percept> percepts) {
    for (Unit unit : this.api.getMyUnits()) {
      if (unit.getType().isWorker()) {
        perceiveWorkerActivity(percepts, unit);
      }
    }
  }

  /**
   * Perceives all the terran worker percepts.
   * 
   * @param percepts
   *          The list of percepts
   */
  private void perceiveTerranWorkers(List<Percept> percepts) {
    for (Unit unit : this.api.getMyUnits()) {
      if (unit.getType().isWorker()) {
        perceiveWorkerActivity(percepts, unit);
      }
      if (unit.getType().isMechanical()) {
        perceiveRepair(percepts, unit);
      }
    }
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> percepts = new LinkedList<>();

    if (unit.getType().getID() == RaceTypes.Terran.getID()) {
      perceiveTerranWorkers(percepts);
    } else {
      perceiveWorkers(percepts);
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
    List<Parameter> conditions = new LinkedList<>();

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
