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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
  private void perceiveWorkerActivity(Set<Percept> percepts, Unit unit) {
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
   * @param repairpercepts
   *          The list of percepts
   * @param unit
   *          the evaluated terran worker
   * @return the new list of percepts
   */
  private void perceiveRepair(Set<Percept> repairpercepts, Unit unit) {
    int hp = unit.getHitPoints();
    int maxHp = unit.getType().getMaxHitPoints();
    if (hp < maxHp) {
      repairpercepts.add(new RepairPercept(unit));
    }
  }

  /**
   * Perceives all the worker percepts minus the terran worker percepts.
   * 
   * @param toReturn
   *          The list of percepts
   */
  private void perceiveWorkers(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
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
  private void perceiveTerranWorkers(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> activitypercepts = new HashSet<>();
    Set<Percept> repairpercepts = new HashSet<>();
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
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {

    if (unit.getType().getID() == RaceTypes.Terran.getID()) {
      perceiveTerranWorkers(toReturn);
    } else {
      perceiveWorkers(toReturn);
    }


    Set<Percept> gatheringpercepts = new HashSet<>();
    if ((unit.isGatheringGas() || unit.isGatheringMinerals()) && unit.getOrderTarget() != null) {
      gatheringpercepts.add(new GatheringPercept(unit.getOrderTarget().getID()));
    }

    Set<Percept> geyserpercepts = new HashSet<>();
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
