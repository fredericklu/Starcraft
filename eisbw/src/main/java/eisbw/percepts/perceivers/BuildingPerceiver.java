package eisbw.percepts.perceivers;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.HasResearchedPercept;
import eisbw.percepts.QueueSizePercept;
import eisbw.percepts.RallyPointPercept;
import eisbw.percepts.RallyUnitPercept;
import eisbw.percepts.SpaceProvidedPercept;
import eisbw.percepts.UnitLoadedPercept;
import eisbw.percepts.UpgradePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position.Positions;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;

import java.util.LinkedList;
import java.util.List;

public class BuildingPerceiver extends UnitPerceiver {

  public BuildingPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> percepts = new LinkedList<>();

    if (!unit.getRallyPosition().equals(Positions.None)) {
      percepts.add(
          new RallyPointPercept(unit.getRallyPosition().getBX(), unit.getRallyPosition().getBY()));
    }

    if (unit.getRallyUnit() != null) {
      percepts.add(new RallyUnitPercept(unit.getRallyUnit().getID()));
    }

    percepts.add(new QueueSizePercept(unit.getTrainingQueueSize()));

    if (unit.isUpgrading()) {
      percepts.add(new UpgradePercept(unit.getUpgrade().getName()));
    }

    for (TechType tech : TechTypes.getAllTechTypes()) {
      if (api.getSelf().isResearched(tech)) {
        percepts.add(new HasResearchedPercept(tech.getName()));
      }
    }

    if (unit.getType().getSpaceProvided() > 0) {
      List<Unit> loadedUnits = unit.getLoadedUnits();

      percepts.add(new SpaceProvidedPercept(loadedUnits.size(), unit.getType().getSpaceProvided()));
      for (Unit u : loadedUnits) {
        percepts.add(new UnitLoadedPercept(u.getID(), unit.getType().getName()));
      }
    }

    return percepts;
  }

  @Override
  public List<Parameter> getConditions() {
    List<Parameter> conditions = new LinkedList<>();

    if (unit.getType().getRaceID() == RaceTypes.Terran.getID()) {

      if (unit.isLifted()) {
        conditions.add(new Identifier("isLifted"));
      }

      if (unit.getAddon() != null) {
        conditions.add(new Identifier(unit.getAddon().getType().getName()));
      }
    }

    return conditions;
  }
}
