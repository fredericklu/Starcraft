package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.DefensiveMatrixPercept;
import eisbw.percepts.EnergyPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.ResourcesPercept;
import eisbw.percepts.SelfPercept;
import eisbw.percepts.StatusPercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GenericUnitPerceiver extends UnitPerceiver {

  public GenericUnitPerceiver(JNIBWAPI api, Unit unit) {
    super(api, unit);
  }

  @Override
  public Map<PerceptFilter, List<Percept>> perceive() {
    Map<PerceptFilter, List<Percept>> toReturn = new HashMap<>();

    defenceMatrixPercept(toReturn);
    resourcesPercept(toReturn);
    selfPercept(toReturn);
    statusPercept(toReturn);
    if (unit.getType().getMaxEnergy() > 0) {
      energyPercept(toReturn);
    }

    return toReturn;
  }

  private void energyPercept(Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> percepts = new LinkedList<>();
    percepts.add(new EnergyPercept(unit.getEnergy(), unit.getType().getMaxEnergy()));
    toReturn.put(new PerceptFilter(Percepts.ENERGY, Filter.Type.ON_CHANGE), percepts);
  }

  private void statusPercept(Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> percepts = new LinkedList<>();
    percepts.add(new StatusPercept(unit.getHitPoints(), unit.getShields(),
        unit.getPosition().getBX(), unit.getPosition().getBY()));
    toReturn.put(new PerceptFilter(Percepts.STATUS, Filter.Type.ON_CHANGE), percepts);
  }

  private void selfPercept(Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> percepts = new LinkedList<>();
    percepts.add(new SelfPercept(unit.getID(), unit.getType().getName()));
    toReturn.put(new PerceptFilter(Percepts.SELF, Filter.Type.ONCE), percepts);
  }

  private void resourcesPercept(Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> percepts = new LinkedList<>();
    percepts.add(new ResourcesPercept(api.getSelf().getMinerals(), api.getSelf().getGas(),
        api.getSelf().getSupplyUsed(), api.getSelf().getSupplyTotal()));
    toReturn.put(new PerceptFilter(Percepts.RESOURCES, Filter.Type.ON_CHANGE), percepts);
  }

  private void defenceMatrixPercept(Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> percepts = new LinkedList<>();
    if (unit.isDefenseMatrixed()) {
      percepts.add(new DefensiveMatrixPercept(unit.getDefenseMatrixPoints()));
    }
    toReturn.put(new PerceptFilter(Percepts.DEFENSIVEMATRIX, Filter.Type.ALWAYS), percepts);
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

    if (api.getSelf().getRace().getID() == RaceTypes.Terran.getID()) {
      setTerranConditions(conditions);
    }

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

  @Override
  public List<Parameter> getConditions() {
    List<Parameter> conditions = new LinkedList<>();

    if (unit.isIdle()) {
      conditions.add(new Identifier("idle"));
    }

    if (!unit.isCompleted()) {
      conditions.add(new Identifier("beingConstructed"));
    }

    if (unit.isCloaked()) {
      conditions.add(new Identifier("cloaked"));
    }

    if (unit.getType().isCanMove()) {
      setMovingConditions(conditions);
    }

    return conditions;
  }
}
