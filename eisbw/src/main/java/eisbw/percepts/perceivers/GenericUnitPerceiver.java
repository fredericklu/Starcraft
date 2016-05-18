package eisbw.percepts.perceivers;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.EnemyRacePercept;
import eisbw.percepts.EnergyPercept;
import eisbw.percepts.ResourcesPercept;
import eisbw.percepts.SelfPercept;
import eisbw.percepts.StatusPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;

import java.util.LinkedList;
import java.util.List;

public class GenericUnitPerceiver extends UnitPerceiver {

  public GenericUnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> percepts = new LinkedList<>();

    for (Player p : api.getEnemies()) {
      percepts.add(new EnemyRacePercept(p.getRace().getName()));
    }

    percepts.add(new ResourcesPercept(api.getSelf().getMinerals(), api.getSelf().getGas(),
        api.getSelf().getSupplyUsed(), api.getSelf().getSupplyTotal()));

    percepts.add(new SelfPercept(unit.getID(), unit.getType().getName()));

    percepts.add(new StatusPercept(unit.getHitPoints(), unit.getShields(),
        unit.getPosition().getBX(), unit.getPosition().getBY()));

    if (unit.getType().getMaxEnergy() > 0) {
      percepts.add(new EnergyPercept(unit.getEnergy(), unit.getType().getMaxEnergy()));
    }

    return percepts;
  }

  /**
   * @param conditions
   *          The conditions of the unit
   * @return The conditions of the (moving) unit.
   */
  private List<Parameter> setTerranTypes(List<Parameter> conditions) {
    if (unit.isStimmed()) {
      conditions.add(new Identifier("stimmed"));
    }

    if (unit.isSieged()) {
      conditions.add(new Identifier("sieged"));
    }

    return conditions;
  }

  /**
   * @param conditions
   *          The conditions of the unit
   * @return The conditions of the (moving) unit.
   */
  private List<Parameter> setMovingConditions(List<Parameter> conditions) {

    if (api.getSelf().getRace().getID() == RaceTypes.Terran.getID()) {
      setTerranTypes(conditions);
    }

    if (unit.isMoving()) {
      conditions.add(new Identifier("moving"));
    }

    if (unit.isFollowing()) {
      conditions.add(new Identifier("following"));
    }

    if (unit.isLoaded()) {
      conditions.add(new Identifier("isLoaded"));
    }

    return conditions;
  }

  @Override
  public List<Parameter> getConditions() {
    List<Parameter> conditions = new LinkedList<>();

    if (unit.isIdle()) {
      conditions.add(new Identifier("idle"));
    }

    if (!unit.isCompleted()) {
      conditions.add(new Identifier("isBeingConstructed"));
    }

    if (unit.isCloaked()) {
      conditions.add(new Identifier("isCloaked"));
    }

    if (unit.getType().isCanMove()) {
      setMovingConditions(conditions);
    }

    return conditions;
  }
}
