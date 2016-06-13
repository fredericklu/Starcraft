package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.BasePercept;
import eisbw.percepts.ChokepointPercept;
import eisbw.percepts.MapPercept;
import eisbw.percepts.Percepts;
import jnibwapi.BaseLocation;
import jnibwapi.ChokePoint;
import jnibwapi.JNIBWAPI;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The perceiver which handles all the map percepts.
 *
 */
public class MapPerceiver extends Perceiver {

  /**
   * The MapPerceiver constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public MapPerceiver(JNIBWAPI api) {
    super(api);
  }

  @Override
  public Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn) {
    Set<Percept> percepts = new HashSet<>();
    jnibwapi.Map map = api.getMap();

    Percept mapPercept = new MapPercept(map.getSize().getBX(), map.getSize().getBY());
    percepts.add(mapPercept);

    for (BaseLocation location : map.getBaseLocations()) {
      Percept basePercept = new BasePercept(location.getPosition().getBX(),
          location.getPosition().getBY(), location.isStartLocation(), location.getRegion().getID());
      percepts.add(basePercept);
    }

    for (ChokePoint cp : map.getChokePoints()) {
      Percept chokePercept = new ChokepointPercept(cp.getCenter().getBX(), cp.getCenter().getBY());
      percepts.add(chokePercept);
    }
    toReturn.put(new PerceptFilter(Percepts.MAP, Filter.Type.ONCE), percepts);
    return toReturn;
  }
}
