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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapPerceiver extends Perceiver {

  public MapPerceiver(JNIBWAPI api) {
    super(api);
  }

  @Override
  public Map<PerceptFilter, List<Percept>> perceive(Map<PerceptFilter, List<Percept>> toReturn) {
    List<Percept> percepts = new LinkedList<>();
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
