package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.UnitTypesEx;
import eisbw.percepts.GatheringPercept;
import eisbw.percepts.MineralFieldPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.VespeneGeyserPercept;
import eisbw.percepts.WorkerActivityPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the worker percepts.
 */
public class WorkerPerceiver extends UnitPerceiver {

  /**
   * @param api
   *          The BWAPI.
   * @param unit
   *          The unit which is about the perceiving.
   */
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

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {

    perceiveWorkers(toReturn);
    resourcesPercepts(toReturn);

    Set<Percept> gatheringpercepts = new HashSet<>();
    if ((unit.isGatheringGas() || unit.isGatheringMinerals()) && unit.getOrderTarget() != null) {
      gatheringpercepts.add(new GatheringPercept(unit.getOrderTarget().getID()));
    }

    toReturn.put(new PerceptFilter(Percepts.GATHERING, Filter.Type.ALWAYS), gatheringpercepts);
    return toReturn;
  }

  private void resourcesPercepts(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> minerals = new HashSet<>();
    Set<Percept> geysers = new HashSet<>();

    for (Unit u : api.getNeutralUnits()) {
      UnitType unitType = u.getType();
      if (u.isVisible()) {
        if (UnitTypesEx.isMineralField(unitType)) {
          MineralFieldPercept mineralfield = new MineralFieldPercept(u.getID(), u.getResources(),
              u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
          minerals.add(mineralfield);
        } else if (UnitTypesEx.isVespeneGeyser(unitType)) {
          VespeneGeyserPercept mineralfield = new VespeneGeyserPercept(u.getID(), u.getResources(),
              u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
          geysers.add(mineralfield);

        }
      }
    }
    toReturn.put(new PerceptFilter(Percepts.MINERALFIELD, Filter.Type.ALWAYS), minerals);
    toReturn.put(new PerceptFilter(Percepts.VESPENEGEYSER, Filter.Type.ALWAYS), geysers);
  }

}
