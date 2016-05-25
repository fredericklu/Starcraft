package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.Attacking;
import eisbw.percepts.IsCloakedPercept;
import eisbw.percepts.IsMorphingPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.UnitPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UnitsPerceiver extends Perceiver {

  public UnitsPerceiver(JNIBWAPI api) {
    super(api);
  }

  /**
   * Sets some of the generic Unit percepts.
   * 
   * @param units
   *          The perceived units
   * @param isFriendly
   *          indicates whether these units are friendly or not
   * @param percepts
   *          The list of percepts
   * @param toReturn
   *          - the map that will be returned
   */
  private void setUnitPercepts(List<Unit> units, boolean isFriendly,
      Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> unitpercepts = new LinkedList<>();
    List<Percept> morphingpercepts = new LinkedList<>();
    List<Percept> attackingpercepts = new LinkedList<>();
    List<Percept> cloakedpercepts = new LinkedList<>();

    for (Unit u : units) {
      unitpercepts.add(new UnitPercept(isFriendly, u.getType().getName(), u.getID(),
          u.getHitPoints(), u.getShields(), u.getType().isFlyer(), u.getPosition().getBX(),
          u.getPosition().getBY()));
      if (u.isMorphing()) {
        morphingpercepts.add(new IsMorphingPercept(u.getBuildType().getName(), u.getID()));
      }
      if (u.getType().isAttackCapable()) {
        Unit targetUnit = u.getOrderTarget();
        if (targetUnit != null && targetUnit.getType().isAttackCapable()) {
          attackingpercepts.add(new Attacking(u.getID(), targetUnit.getID()));
        }
      }
      if (u.isCloaked()) {
        cloakedpercepts.add(new IsCloakedPercept(u.getType().getName(), u.getID()));
      }
    }

    toReturn.put(new PerceptFilter(Percepts.UNIT, Filter.Type.ALWAYS), unitpercepts);
    toReturn.put(new PerceptFilter(Percepts.ATTACKING, Filter.Type.ALWAYS), attackingpercepts);
    toReturn.put(new PerceptFilter(Percepts.ISMORPHING, Filter.Type.ALWAYS), morphingpercepts);
    toReturn.put(new PerceptFilter(Percepts.ISCLOACKED, Filter.Type.ALWAYS), cloakedpercepts);
  }

  @Override
  public Map<PerceptFilter, List<Percept>> perceive() {
    Map<PerceptFilter, List<Percept>> toReturn = new HashMap<>();

    // perceive friendly units
    setUnitPercepts(api.getMyUnits(), true, toReturn);

    // perceive enemy units
    setUnitPercepts(api.getEnemyUnits(), false, toReturn);

    return toReturn;
  }

}
