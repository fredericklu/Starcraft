package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.GatheringPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.RepairPercept;
import eisbw.percepts.VespeneGeyserPercept;
import eisbw.percepts.WorkerActivityPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkerPerceiver extends UnitPerceiver {

  public WorkerPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  /**
   * Perceives the workerActivity percept.
   * 
   * @param activitypercepts
   *          The list of percepts
   * @param unit
   *          the evaluated terran worker
   * @return the new list of percepts
   */
  private void perceiveWorkerActivity(List<Percept> activitypercepts, Unit unit) {
    if (unit.isGatheringGas()) {
      activitypercepts.add(new WorkerActivityPercept(unit.getID(), "gatheringGas"));
    } else if (unit.isGatheringMinerals()) {
      activitypercepts.add(new WorkerActivityPercept(unit.getID(), "gatheringMinerals"));
    } else if (unit.isConstructing()) {
      activitypercepts.add(new WorkerActivityPercept(unit.getID(), "constructing"));
    } else {
      activitypercepts.add(new WorkerActivityPercept(unit.getID(), "idling"));
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
   * @param toReturn
   *          The list of percepts
   */
  private void perceiveWorkers(Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> percepts = new LinkedList<>();
    for (Unit unit : this.api.getMyUnits()) {
      if (unit.getType().isWorker()) {
        perceiveWorkerActivity(percepts, unit);
      }
    }
    toReturn.put(new PerceptFilter(Percepts.WORKERACTIVITY, Filter.Type.ALWAYS), percepts);
  }

  /**
   * Perceives all the terran worker percepts.
   * 
   * @param toReturn
   *          The list of percepts
   */
  private void perceiveTerranWorkers(Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> activitypercepts = new LinkedList<>();
    List<Percept> repairpercepts = new LinkedList<>();
    for (Unit unit : this.api.getMyUnits()) {
      if (unit.getType().isWorker()) {
        perceiveWorkerActivity(activitypercepts, unit);
      }
      if (unit.getType().isMechanical()) {
        perceiveRepair(repairpercepts, unit);
      }
    }
    toReturn.put(new PerceptFilter(Percepts.WORKERACTIVITY, Filter.Type.ALWAYS), activitypercepts);
    toReturn.put(new PerceptFilter(Percepts.REQUIRESREPAIR, Filter.Type.ALWAYS), repairpercepts);
  }

  @Override
  public Map<PerceptFilter, List<Percept>> perceive(Map<PerceptFilter, List<Percept>> toReturn) {

    if (unit.getType().getID() == RaceTypes.Terran.getID()) {
      perceiveTerranWorkers(toReturn);
    } else {
      perceiveWorkers(toReturn);
    }


    List<Percept> gatheringpercepts = new LinkedList<>();
    if ((unit.isGatheringGas() || unit.isGatheringMinerals()) && unit.getOrderTarget() != null) {
      gatheringpercepts.add(new GatheringPercept(unit.getOrderTarget().getID()));
    }

    List<Percept> geyserpercepts = new LinkedList<>();
    for (Unit u : api.getNeutralUnits()) {
      if (u.getType() == UnitType.UnitTypes.Resource_Vespene_Geyser) {
        Percept percept = new VespeneGeyserPercept(u.getID(), u.getResources(),
            u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
        geyserpercepts.add(percept);
      }
    }

    toReturn.put(new PerceptFilter(Percepts.GATHERING, Filter.Type.ALWAYS), gatheringpercepts);
    toReturn.put(new PerceptFilter(Percepts.VESPENEGEYSER, Filter.Type.ALWAYS), geyserpercepts);
    return toReturn;
  }

  @Override
  public List<Parameter> getConditions() {
    List<Parameter> conditions = new LinkedList<>();

    if (unit.isCarryingGas() || unit.isCarryingMinerals()) {
      conditions.add(new Identifier("carrying"));
    }

    if (unit.isConstructing()) {
      conditions.add(new Identifier("constructing"));
    }

    return conditions;
  }
}
