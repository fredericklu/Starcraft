package eisbw.percepts.perceivers;

import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.QueueSizePercept;
import eisbw.percepts.RallyPointPercept;
import eisbw.percepts.RallyUnitPercept;
import eisbw.percepts.UpgradePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position.Positions;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class BuildingPerceiver extends UnitPerceiver {

  public BuildingPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> percepts = new ArrayList<>();

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
    return percepts;
  }

  @Override
  public List<Parameter> getConditions() {
    return new ArrayList<>();
  }
}
