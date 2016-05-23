package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.UnitTypesEx;
import eisbw.percepts.MineralFieldPercept;
import eisbw.percepts.UnitAmountPercept;
import eisbw.percepts.VespeneGeyserPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BufferPerceiver extends Perceiver {

  public BufferPerceiver(JNIBWAPI api) {
    super(api);
  }

  @Override
  public List<Percept> perceive() {
    LinkedList<Percept> perceptHolder = new LinkedList<>();
    
    Map<UnitType, Integer> count = new HashMap<>();

    for (Unit myUnit : api.getMyUnits()) {
      UnitType unitType = myUnit.getType();
      if (!count.containsKey(unitType)) {
        count.put(unitType, 0);
      }
      count.put(unitType, count.get(unitType) + 1);
    }
    for (Entry<UnitType, Integer> entry : count.entrySet()) {
      perceptHolder.add(new UnitAmountPercept(entry.getKey().getName(), entry.getValue()));
    }

    for (Unit u : api.getNeutralUnits()) {
      UnitType unitType = u.getType();
      if (u.isVisible()) {
        if (UnitTypesEx.isMineralField(unitType)) {
          MineralFieldPercept mineralfield = new MineralFieldPercept(u.getID(), u.getResources(),
              u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
          perceptHolder.add(mineralfield);
        } else if (UnitTypesEx.isVespeneGeyser(unitType)) {
          VespeneGeyserPercept mineralfield = new VespeneGeyserPercept(u.getID(), u.getResources(),
              u.getResourceGroup(), u.getPosition().getBX(), u.getPosition().getBY());
          perceptHolder.add(mineralfield);

        }
      }
    }

    return perceptHolder;
  }

}
