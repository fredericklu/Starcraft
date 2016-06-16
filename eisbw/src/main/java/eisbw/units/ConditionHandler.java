package eisbw.units;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Danny & Harm - The condition perceiver.
 *
 */
public class ConditionHandler {

  protected final Unit unit;
  protected final JNIBWAPI api;

  /**
   * @param api
   *          The BWAPI.
   * @param unit
   *          The unit.
   */
  public ConditionHandler(JNIBWAPI api, Unit unit) {
    this.unit = unit;
    this.api = api;
  }

  /**
   * @param conditions
   *          The conditions of the unit
   * @return The conditions of the (moving) unit.
   */
  private void setTerranConditions(List<Parameter> conditions) {
    if (unit.isStimmed()) {
      conditions.add(new Identifier("stimmed"));
    }

    if (unit.isSieged()) {
      conditions.add(new Identifier("sieged"));
    }

    if (unit.getType().isBuilding()) {
      setBuildingConditions(conditions);
    }
  }

  /**
   * Sets all conditions caused by Zerg units.
   * 
   * @param conditions
   *          The conditions of the unit
   */
  private void zergAbilityConditions(List<Parameter> conditions) {
    // caused by a Queen
    if (unit.isEnsnared()) {
      conditions.add(new Identifier("ensnared"));
    }

    // caused by a Queen
    if (unit.isParasited()) {
      conditions.add(new Identifier("parasited"));
    }

    // caused by a Defiler
    if (unit.isPlagued()) {
      conditions.add(new Identifier("plagued"));
    }

    // caused by a Defiler
    if (unit.isUnderDarkSwarm()) {
      conditions.add(new Identifier("darkSwarmed"));
    }

  }

  /**
   * Sets all conditions caused by Terran units.
   * 
   * @param conditions
   *          The conditions of the unit
   */
  private void terranAbilityConditions(List<Parameter> conditions) {

    // caused by a Medic
    if (unit.isBlind()) {
      conditions.add(new Identifier("blinded"));
    }

    // caused by a Ghost
    if (unit.isLockedDown()) {
      conditions.add(new Identifier("lockDowned"));
    }

    // caused by a Science Vessel
    if (unit.isIrradiated()) {
      conditions.add(new Identifier("irradiated"));
    }

  }

  /**
   * Sets all conditions caused by Protoss units.
   * 
   * @param conditions
   *          The conditions of the unit
   */
  private void protossAbilityConditions(List<Parameter> conditions) {

    // caused by a High Templar
    if (unit.isUnderStorm()) {
      conditions.add(new Identifier("underStorm"));
    }

    // caused by an Arbiter
    if (unit.isStasised()) {
      conditions.add(new Identifier("stasised"));
    }

    // caused by a Dark Archon
    if (unit.isMaelstrommed()) {
      conditions.add(new Identifier("maelstrommed"));
    }

    // caused by a Corsair
    if (unit.isUnderDisruptionWeb()) {
      conditions.add(new Identifier("disruptionWebbed"));
    }

  }

  /**
   * Sets all the conditions caused by abilities.
   * 
   * @param conditions
   *          The conditions of the unit
   */
  private void setAbilityConditions(List<Parameter> conditions) {

    zergAbilityConditions(conditions);

    terranAbilityConditions(conditions);

    protossAbilityConditions(conditions);

  }

  /**
   * @param conditions
   *          The conditions of the unit
   * @return The conditions of the (moving) unit.
   */
  private void setMovingConditions(List<Parameter> conditions) {

    if (unit.isMoving()) {
      conditions.add(new Identifier("moving"));
    }

    if (unit.isFollowing()) {
      conditions.add(new Identifier("following"));
    }

    if (unit.isLoaded()) {
      conditions.add(new Identifier("loaded"));
    }

    setAbilityConditions(conditions);

  }

  /**
   * @param conditions
   *          The conditions of the unit
   * @return The conditions of generic unit.
   */
  private void setGenericConditions(List<Parameter> conditions) {
    if (unit.isIdle()) {
      conditions.add(new Identifier("idle"));
    }

    if (!unit.isCompleted()) {
      conditions.add(new Identifier("beingConstructed"));
    }

    if (unit.isCloaked()) {
      conditions.add(new Identifier("cloaked"));
    }
  }

  /**
   * @param conditions
   *          The conditions of the unit
   * @return The conditions of the worker units.
   */
  private void setWorkerConditions(List<Parameter> conditions) {
    if (unit.isCarryingGas() || unit.isCarryingMinerals()) {
      conditions.add(new Identifier("carrying"));
    }

    if (unit.isConstructing()) {
      conditions.add(new Identifier("constructing"));
    }
  }

  /**
   * @param conditions
   *          The conditions of the unit
   * @return The conditions of the building units.
   */
  private void setBuildingConditions(List<Parameter> conditions) {
    if (unit.isLifted()) {
      conditions.add(new Identifier("lifted"));
    }

    if (unit.getAddon() != null) {
      conditions.add(new Identifier(unit.getAddon().getType().getName()));
    }
  }

  /**
   * @return A list of conditions of the unit.
   */
  public List<Parameter> getConditions() {
    List<Parameter> conditions = new LinkedList<>();

    setGenericConditions(conditions);

    if (api.getSelf().getRace().getID() == RaceTypes.Terran.getID()) {
      setTerranConditions(conditions);
    }

    if (unit.getType().isWorker()) {
      setWorkerConditions(conditions);
    }

    if (unit.getType().isCanMove()) {
      setMovingConditions(conditions);
    }

    return conditions;
  }
}
