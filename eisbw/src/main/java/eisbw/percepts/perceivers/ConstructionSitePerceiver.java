package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.UnitTypesEx;
import eisbw.percepts.ConstructionSitePercept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ConstructionSitePerceiver extends Perceiver {

  public ConstructionSitePerceiver(JNIBWAPI api) {
    super(api);
  }

  /**
   * @param x
   *          The x-coordinate.
   * @param y
   *          The y-coordinate.
   * @param illegals
   *          A list of illegal build places.
   * @return Check whether the given ConstructionSite is legal or not.
   */
  public Boolean checkConstructionSite(int x, int y, ArrayList<Point> illegals) {
    Point possible = new Point(x, y);
    boolean add = true;
    for (Point illegal : illegals) {
      if (illegal.distance(possible) < 3) {
        add = false;
        break;
      }
    }

    if (add) {
      return true;
    } else {
      return false;
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

    for (int x = 0; x < mapWidth; x++) {
      for (int y = 0; y < mapHeight; y++) {
        Position pos = new Position(x, y, Position.PosType.BUILD);

        // Terran ConstructionSite
        if (api.getSelf().getRace().getID() == RaceTypes.Terran.getID()) {

          // check if you can actually build here as terran
          if (api.canBuildHere(pos, UnitType.UnitTypes.Terran_Command_Center, true)) {

            // checks whether the ConstructionSite is not near an illegal build
            // place
            if (checkConstructionSite(x, y, illegals)) {
              percepts.add(new ConstructionSitePercept(x, y));
            }
          }

          // Zerg ConstructionSite
        } else if (api.getSelf().getRace().getID() == RaceTypes.Zerg.getID()) {

          // check if you can actually build here as zerg
          if (api.canBuildHere(pos, UnitType.UnitTypes.Zerg_Spawning_Pool, true)) {

            // checks whether the ConstructionSite is not near an illegal build
            // place
            if (checkConstructionSite(x, y, illegals)) {
              percepts.add(new ConstructionSitePercept(x, y));
            }
          }

          // Protoss ConstructionSite
        } else if (api.getSelf().getRace().getID() == RaceTypes.Protoss.getID()) {

          if (api.canBuildHere(pos, UnitType.UnitTypes.Protoss_Nexus, true)) {

            // checks whether the ConstructionSite is not near an illegal build
            // place
            boolean legal = checkConstructionSite(x, y, illegals);
            boolean nearPylon = api.canBuildHere(pos, UnitType.UnitTypes.Protoss_Gateway, true);
            if (legal && nearPylon) {
              percepts.add(new ConstructionSitePercept(x, y, true));
            } else if (legal && !nearPylon) {
              percepts.add(new ConstructionSitePercept(x, y, false));
            }
          }
        }
      }
    }
    // TODO: Locations near minerals and geysers are not buildable
    return percepts;
  }
}
