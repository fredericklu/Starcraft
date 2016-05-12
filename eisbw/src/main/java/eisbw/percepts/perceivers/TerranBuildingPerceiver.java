package eisbw.percepts.perceivers;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.AddonPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class TerranBuildingPerceiver extends UnitPerceiver {
  public TerranBuildingPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();

    if (unit.getAddon() != null) {
      percepts.add(new AddonPercept(unit.getAddon().getType().getName()));
    }

    return percepts;
  }

  @Override
  public List<Parameter> getConditions() {
    List<Parameter> conditions = new ArrayList<>();
    
    if (unit.isLifted()) {
      conditions.add(new Identifier("isLifted"));
    }
    
    return conditions;
  }
}
