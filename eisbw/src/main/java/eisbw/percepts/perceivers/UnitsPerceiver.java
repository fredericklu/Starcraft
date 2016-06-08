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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
   * @param attackingpercepts
   *          - list with unitPercepts
   * @param unitpercepts
   *          - list with attackingPercepts
   * @param percepts
   *          The list of percepts
   * @param toReturn
   *          - the map that will be returned
   */
  private void setUnitPercepts(List<Unit> units, boolean isFriendly, Set<Percept> unitpercepts,
      Set<Percept> attackingpercepts) {

    // Fix for the phantom marines bug
    for (Unit u : units) {
      if (!isFriendly && u.isBeingConstructed()) {
        continue;
      }

      List<Parameter> conditions = new LinkedList<>();

      setUnitConditions(u, conditions);

      unitpercepts.add(new UnitPercept(isFriendly, u.getType().getName(), u.getID(),
          u.getHitPoints(), u.getShields(), conditions));
      if (u.getType().isAttackCapable()) {
        Unit targetUnit = u.getOrderTarget();
        if (targetUnit != null && targetUnit.getType().isAttackCapable()) {
          attackingpercepts.add(new Attacking(u.getID(), targetUnit.getID(),
              u.getPosition().getBX(), u.getPosition().getBY()));
        }
      }
    }
  }

  /**
   * Sets the conditions of the unit.
   * 
   * @param unit
   *          The evaluated unit
   * @param conditions
   *          The list of conditions of the unit
   */
  public void setUnitConditions(Unit unit, List<Parameter> conditions) {
    if (unit.getType().isFlyer()) {
      conditions.add(new Identifier("flying"));
    }
    if (unit.isMorphing()) {
      conditions.add(new Identifier("morphing"));
    }
    if (unit.isCloaked()) {
      conditions.add(new Identifier("cloaked"));
    }
    if (unit.isBeingConstructed()) {
      conditions.add(new Identifier("beingConstructed"));
    }
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {

    Set<Percept> unitpercepts = new HashSet<>();
    Set<Percept> attackingpercepts = new HashSet<>();

    // perceive friendly units
    setUnitPercepts(api.getMyUnits(), true, unitpercepts, attackingpercepts);

    // perceive enemy units
    setUnitPercepts(api.getEnemyUnits(), false, unitpercepts, attackingpercepts);

    toReturn.put(new PerceptFilter(Percepts.UNIT, Filter.Type.ALWAYS), unitpercepts);
    toReturn.put(new PerceptFilter(Percepts.ATTACKING, Filter.Type.ALWAYS), attackingpercepts);

    return toReturn;
  }

}
