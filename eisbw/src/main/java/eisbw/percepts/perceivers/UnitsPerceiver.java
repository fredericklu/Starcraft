package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.Attacking;
import eisbw.percepts.IsCloakedPercept;
import eisbw.percepts.IsMorphingPercept;
import eisbw.percepts.UnitPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

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
      percepts.add(new UnitPercept(isFriendly, u.getType().getName(), u.getID(), u.getHitPoints(),
          u.getShields(), u.getType().isFlyer(), u.getPosition().getBX(), u.getPosition().getBY()));
      if (u.isMorphing()) {
        percepts.add(new IsMorphingPercept(u.getBuildType().getName(), u.getID()));
      }
      if (u.getType().isAttackCapable()) {
        Unit targetUnit = u.getOrderTarget();
        if (targetUnit != null && targetUnit.getType().isAttackCapable()) {
          percepts.add(new Attacking(u.getID(), targetUnit.getID()));
        }
      }
      if (u.isCloaked()) {
        percepts.add(new IsCloakedPercept(u.getType().getName(), u.getID()));
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
