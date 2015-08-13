package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;

import java.util.*;

import jnibwapi.*;

public class IsMovingPerceiver extends UnitPerceiver {
    public IsMovingPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
      ArrayList<Percept> percepts = new ArrayList<Percept>();
      
      if(unit.isMoving())
        percepts.add(new MovingPercept());
      
      if(unit.isFollowing())
        percepts.add(new FollowingPercept());
      
      return percepts;
    }
    
}