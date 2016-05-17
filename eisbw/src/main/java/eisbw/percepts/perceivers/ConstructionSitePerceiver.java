package eisbw.percepts.perceivers;

import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.UnitTypesEx;
import eisbw.percepts.ConstructionSitePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ConstructionSitePerceiver extends Perceiver {

  public ConstructionSitePerceiver(JNIBWAPI api) {
    super(api);
  }

  /**
   * @param xpos
   *          The x-coordinate.
   * @param ypos
   *          The y-coordinate.
   * @param illegals
   *          A list of illegal build places.
   * @return Check whether the given ConstructionSite is legal or not.
   */
  private Boolean checkConstructionSite(int xpos, int ypos, ArrayList<Point> illegals) {
    Point possible = new Point(xpos, ypos);
    boolean add = true;
    for (Point illegal : illegals) {
      if (illegal.distance(possible) < 3) {
        add = false;
        break;
      }
    }

    return add;
  }

  /**
   * @param pos
   *          The current evaluated position
   * @param xpos
   *          The x-coordinate
   * @param ypos
   *          The y-coordinate
   * @param illegals
   *          A list of illegal build places.
   * @param percepts
   *          The list of perceived constructionsites
   */
  private void perceiveTerran(Position pos, int xpos, int ypos, ArrayList<Point> illegals,
      ArrayList<Percept> percepts) {

    // check if you can actually build here as terran
    if (api.canBuildHere(pos, UnitType.UnitTypes.Terran_Command_Center, true)
        && checkConstructionSite(xpos, ypos, illegals)) {
      percepts.add(new ConstructionSitePercept(xpos, ypos));
    }
  }

  /**
   * @param pos
   *          The current evaluated position
   * @param xpos
   *          The x-coordinate
   * @param ypos
   *          The y-coordinate
   * @param illegals
   *          A list of illegal build places.
   * @param percepts
   *          The list of perceived constructionsites
   */
  private void perceiveZerg(Position pos, int xpos, int ypos, ArrayList<Point> illegals,
      ArrayList<Percept> percepts) {

    // check if you can actually build here as zerg
    if (api.canBuildHere(pos, UnitType.UnitTypes.Zerg_Hatchery, true) && api.hasCreep(pos)
        && checkConstructionSite(xpos, ypos, illegals)) {
      percepts.add(new ConstructionSitePercept(xpos, ypos));
    }
  }

  /**
   * @param pos
   *          The current evaluated position
   * @param xpos
   *          The x-coordinate
   * @param ypos
   *          The y-coordinate
   * @param illegals
   *          A list of illegal build places.
   * @param percepts
   *          The list of perceived constructionsites
   */
  private void perceiveProtosss(Position pos, int xpos, int ypos, ArrayList<Point> illegals,
      ArrayList<Percept> percepts) {
    if (api.canBuildHere(pos, UnitType.UnitTypes.Protoss_Nexus, true)) {

      // checks whether the ConstructionSite is not near an illegal build
      // place
      boolean legal = checkConstructionSite(xpos, ypos, illegals);
      boolean nearPylon = api.canBuildHere(pos, UnitType.UnitTypes.Protoss_Gateway, true);
      if (legal && nearPylon) {
        percepts.add(new ConstructionSitePercept(xpos, ypos, true));
      } else if (legal) {
        percepts.add(new ConstructionSitePercept(xpos, ypos, false));
      }
    }
  }

  @Override
  public List<Percept> perceive() {
    ArrayList<Percept> percepts = new ArrayList<>();
    jnibwapi.Map map = api.getMap();

    int mapWidth = map.getSize().getBX();
    int mapHeight = map.getSize().getBY();

    ArrayList<Point> illegals = new ArrayList<>();
    for (Unit u : api.getNeutralUnits()) {
      if (UnitTypesEx.isResourceType(u.getType()) && u.isExists()) {
        illegals.add(new Point(u.getTilePosition().getBX(), u.getTilePosition().getBY()));
      }
    }

    for (int x = 0; x < mapWidth; x = x + 3) {
      for (int y = 0; y < mapHeight; y = y + 3) {
        Position pos = new Position(x, y, Position.PosType.BUILD);

        if (api.getSelf().getRace().getID() == RaceTypes.Terran.getID()) {

          perceiveTerran(pos, x, y, illegals, percepts);

        } else if (api.getSelf().getRace().getID() == RaceTypes.Zerg.getID()) {

          perceiveZerg(pos, x, y, illegals, percepts);

        } else if (api.getSelf().getRace().getID() == RaceTypes.Protoss.getID()) {

          perceiveProtosss(pos, x, y, illegals, percepts);

        }
      }
    }
    return percepts;
  }

}
