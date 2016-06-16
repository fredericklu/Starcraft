package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.HasResearchedPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.QueueSizePercept;
import eisbw.percepts.RallyPointPercept;
import eisbw.percepts.RallyUnitPercept;
import eisbw.percepts.SpaceProvidedPercept;
import eisbw.percepts.UnitLoadedPercept;
import eisbw.percepts.UpgradePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position.Positions;
import jnibwapi.Unit;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the building percepts.
 *
 */
public class BuildingPerceiver extends UnitPerceiver {

  /**
   * @param api
   *          The BWAPI
   * @param unit
   *          The perceiving unit.
   */
  public BuildingPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {

    rallyPointPercept(toReturn);
    rallyUnitPercept(toReturn);
    queueSizePercept(toReturn);
    upgradingPercept(toReturn);
    researchedPercept(toReturn);
    if (unit.getType().getSpaceProvided() > 0) {
      List<Unit> loadedUnits = unit.getLoadedUnits();
      spaceProvidedPercept(toReturn, loadedUnits);
      unitLoadedPercept(toReturn, loadedUnits);
    }

    return toReturn;
  }

  private void unitLoadedPercept(Map<PerceptFilter, Set<Percept>> toReturn,
      List<Unit> loadedUnits) {
    Set<Percept> percepts = new HashSet<>();
    for (Unit u : loadedUnits) {
      if (u != null) {
        percepts.add(new UnitLoadedPercept(u.getID(), u.getType().getName()));
      }
    }
    toReturn.put(new PerceptFilter(Percepts.UNITLOADED, Filter.Type.ALWAYS), percepts);
  }

  private void spaceProvidedPercept(Map<PerceptFilter, Set<Percept>> toReturn,
      List<Unit> loadedUnits) {
    Set<Percept> percepts = new HashSet<>();
    percepts.add(new SpaceProvidedPercept(loadedUnits.size(), unit.getType().getSpaceProvided()));
    toReturn.put(new PerceptFilter(Percepts.SPACEPROVIDED, Filter.Type.ON_CHANGE), percepts);
  }

  private void researchedPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    for (TechType tech : TechTypes.getAllTechTypes()) {
      if (api.getSelf().isResearched(tech)) {
        percepts.add(new HasResearchedPercept(tech.getName()));
      }
    }
    toReturn.put(new PerceptFilter(Percepts.HASRESEARCHED, Filter.Type.ONCE), percepts);
  }

  private void rallyPointPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    if (!unit.getRallyPosition().equals(Positions.None)) {
      percepts.add(
          new RallyPointPercept(unit.getRallyPosition().getBX(), unit.getRallyPosition().getBY()));
      toReturn.put(new PerceptFilter(Percepts.RALLYPOINT, Filter.Type.ON_CHANGE), percepts);
    }
  }

  private void upgradingPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    if (unit.isUpgrading()) {
      percepts.add(new UpgradePercept(unit.getUpgrade().getName()));
      toReturn.put(new PerceptFilter(Percepts.UPGRADING, Filter.Type.ALWAYS), percepts);
    }
  }

  private void queueSizePercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    percepts.add(new QueueSizePercept(unit.getTrainingQueueSize()));
    toReturn.put(new PerceptFilter(Percepts.QUEUESIZE, Filter.Type.ON_CHANGE), percepts);
  }

  private void rallyUnitPercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    if (unit.getRallyUnit() != null) {
      percepts.add(new RallyUnitPercept(unit.getRallyUnit().getID()));
      toReturn.put(new PerceptFilter(Percepts.RALLYUNIT, Filter.Type.ON_CHANGE), percepts);
    }
  }
}
