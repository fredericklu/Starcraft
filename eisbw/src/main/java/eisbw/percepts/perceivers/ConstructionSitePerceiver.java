package eisbw.percepts.perceivers;

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
        boolean buildable = api.canBuildHere(pos, 
            UnitType.UnitTypes.Terran_Command_Center, true);

        // boolean zergBuildable = api.canBuildHere(unit, p,
        // UnitType.UnitTypes.Zerg_Hatchery, true);
        boolean explored = api.isExplored(pos);
        // boolean hasCreep = api.hasCreep(p);
        if (buildable && explored && api.getSelf().getRace().equals(RaceTypes.Terran)) {
          Point possible = new Point(x, y);
          boolean add = true;
          for (Point illegal : illegals) {
            if (illegal.distance(possible) < 10) {
              add = false;
              break;
            }
          }

          if (add) {
            percepts.add(new ConstructionSitePercept(possible.x, possible.y));
          }
        } /*
           * else if (zergBuildable && hasCreep) {
           * 
           * Point possible = new Point(x, y); boolean add = true; for (Point
           * illegal : illegals) { if (illegal.distance(possible) < 10) { add =
           * false; break; } }
           * 
           * if (add) percepts.add(new ZergConstructionSitePercept(possible.x,
           * possible.y)); } else { if(api.canBuildHere(unit, p,
           * UnitType.UnitTypes.Protoss_Stargate, true)) { Point possible = new
           * Point(x, y); boolean add = true; for (Point illegal : illegals) {
           * if (illegal.distance(possible) < 10) { add = false; break; } }
           * 
           * if (add) percepts.add(new
           * ProtossConstructionSitePercept(possible.x, possible.y)); }
           * 
           * if(api.canBuildHere(unit, p, UnitType.UnitTypes.Protoss_Pylon,
           * true)) { Point possible = new Point(x, y); boolean add = true; for
           * (Point illegal : illegals) { if (illegal.distance(possible) < 10) {
           * add = false; break; } }
           * 
           * if (add) percepts.add(new PylonConstructionSitePercept(possible.x,
           * possible.y)); } }
           */
      }
    }

    // TODO: Locations near minerals and geysers are not buildable
    return percepts;
  }
}
