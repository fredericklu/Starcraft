package eisbw.percepts.perceivers;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eis.iilang.TruthValue;
import eisbw.percepts.Attacking;
import eisbw.percepts.UnitPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
   */
  private void setUnitPercepts(List<Unit> units, boolean isFriendly, List<Percept> percepts) {
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

      percepts.add(new UnitPercept(isFriendly, u.getType().getName(), u.getID(), u.getHitPoints(),
          u.getShields(), conditions));

      if (u.getType().isAttackCapable()) {
        Unit targetUnit = u.getOrderTarget();
        if (targetUnit != null && targetUnit.getType().isAttackCapable()) {
          percepts.add(new Attacking(u.getID(), targetUnit.getID()));
        }
      }
    }

  }

  @Override
  public List<Percept> perceive() {
    List<Percept> percepts = new LinkedList<>();

    // perceive friendly units
    setUnitPercepts(api.getMyUnits(), true, percepts);

    // perceive enemy units
    setUnitPercepts(api.getEnemyUnits(), false, percepts);

    return percepts;
  }

}
