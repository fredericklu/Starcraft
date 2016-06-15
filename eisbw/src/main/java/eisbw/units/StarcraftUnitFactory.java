package eisbw.units;

import eisbw.percepts.perceivers.BuildingPerceiver;
import eisbw.percepts.perceivers.GenericUnitPerceiver;
import eisbw.percepts.perceivers.IPerceiver;
import eisbw.percepts.perceivers.WorkerPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Danny & Harm - The Starcraft Unit Factory which creates the units.
 *
 */
public class StarcraftUnitFactory {

  private final JNIBWAPI api;

  /**
   * The StarcraftUnitFactory constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public StarcraftUnitFactory(JNIBWAPI api) {
    this.api = api;
  }

  // These perceptgenerators are only added on init, so a building that can fly
  // can not move when built, so please take caution and think when adding
  // percepts
  /**
   * Creates a unit.
   * 
   * @param unit
   *          - the unit in the game.
   * @return - a StarCraft unit with perceivers.
   */
  public StarcraftUnit create(Unit unit) {
    List<IPerceiver> perceptGenerators = new LinkedList<>();
    perceptGenerators.add(new GenericUnitPerceiver(api, unit));

    if (unit.getType().isBuilding()) {
      perceptGenerators.add(new BuildingPerceiver(api, unit));
    }

    if (unit.getType().isWorker()) {
      perceptGenerators.add(new WorkerPerceiver(api, unit));
    }

    return new StarcraftUnit(perceptGenerators, unit.getType().isWorker());
  }
}
