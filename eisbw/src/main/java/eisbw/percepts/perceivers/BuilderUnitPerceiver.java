package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.ConstructingPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class BuilderUnitPerceiver extends UnitPerceiver {

  public BuilderUnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    if (unit.isConstructing()) {
      percepts.add(new ConstructingPercept());
    }

    return percepts;
  }
}
