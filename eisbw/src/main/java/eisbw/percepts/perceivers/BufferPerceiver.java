package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.UnitTypesEx;
import eisbw.percepts.EnemyRacePercept;
import eisbw.percepts.MineralFieldPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.UnitAmountPercept;
import eisbw.percepts.VespeneGeyserPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the buffered percepts.
 *
 */
public class BufferPerceiver extends Perceiver {

  /**
   * The BufferPerceiver constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public BufferPerceiver(JNIBWAPI api) {
    super(api);
  }

  private void unitAmount(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> countHolder = new HashSet<>();

    Map<UnitType, Integer> count = new HashMap<>();

    for (Unit myUnit : api.getMyUnits()) {
      UnitType unitType = myUnit.getType();
      if (!count.containsKey(unitType)) {
        count.put(unitType, 0);
      }
      count.put(unitType, count.get(unitType) + 1);
    }
    for (Entry<UnitType, Integer> entry : count.entrySet()) {
      countHolder.add(new UnitAmountPercept(entry.getKey().getName(), entry.getValue()));
    }

    toReturn.put(new PerceptFilter(Percepts.UNITAMOUNT, Filter.Type.ALWAYS), countHolder);
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

  private void enemyRacePercept(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    for (Player p : api.getEnemies()) {
      percepts.add(new EnemyRacePercept(p.getRace().getName().toLowerCase()));
    }
    toReturn.put(new PerceptFilter(Percepts.ENEMYRACE, Filter.Type.ONCE), percepts);
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
    unitAmount(toReturn);
    resourcesPercepts(toReturn);
    enemyRacePercept(toReturn);

    return toReturn;
  }

}
