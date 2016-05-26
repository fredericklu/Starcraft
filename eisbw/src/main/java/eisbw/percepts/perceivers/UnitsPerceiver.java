package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.Attacking;
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
    List<Percept> attackingpercepts = new LinkedList<>();

    for (Unit u : units) {
      List<Parameter> conditions = new LinkedList<>();

      if (u.getType().isFlyer()) {
        conditions.add(new Identifier("flying"));
      }
      if (u.isMorphing()) {
        conditions.add(new Identifier("morphing"));
      }
      if (u.isCloaked()) {
        conditions.add(new Identifier("cloaked"));
      }

      unitpercepts.add(new UnitPercept(isFriendly, u.getType().getName(), u.getID(),
          u.getHitPoints(), u.getShields(), conditions));

      if (u.getType().isAttackCapable()) {
        Unit targetUnit = u.getOrderTarget();
        if (targetUnit != null && targetUnit.getType().isAttackCapable()) {
          attackingpercepts.add(new Attacking(u.getID(), targetUnit.getID()));
        }
      }
    }

    toReturn.put(new PerceptFilter(Percepts.UNIT, Filter.Type.ALWAYS), unitpercepts);
    toReturn.put(new PerceptFilter(Percepts.ATTACKING, Filter.Type.ALWAYS), attackingpercepts);
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
