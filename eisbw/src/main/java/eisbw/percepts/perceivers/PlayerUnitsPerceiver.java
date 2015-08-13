package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BWApiUtility;
import eisbw.UnitTypesEx;
import eisbw.percepts.FriendlyPercept;
import eisbw.percepts.LoadablePercept;

import java.util.*;

import jnibwapi.*;

public class PlayerUnitsPerceiver extends Perceiver {
    private final BWApiUtility util;
    
    public PlayerUnitsPerceiver(JNIBWAPI api, BWApiUtility util) {
        super(api);
        this.util = util;
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> percepts = new ArrayList<>();
        
        for (Unit unit : this.api.getMyUnits()) {
            percepts.add(new FriendlyPercept(util.getUnitName(unit), unit.getType().getName(), unit.getID(), unit.getHitPoints(), unit.getShields(), unit.getPosition().getWX(), unit.getPosition().getWY(), unit.getPosition().getBX(), unit.getPosition().getBY()));
            if(unit.getType().getSpaceProvided() > 0 && unit.isExists()){
            	percepts.add(new LoadablePercept(unit.getType().getName(), unit.getID(), unit.getLoadedUnits().size(), unit.getType().getSpaceProvided()));
            }
        }
        
        return percepts;
    }
}
