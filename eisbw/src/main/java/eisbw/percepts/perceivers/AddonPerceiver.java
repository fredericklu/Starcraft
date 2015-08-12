package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;

import java.util.*;

import jnibwapi.*;

public class AddonPerceiver extends UnitPerceiver {
    public AddonPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        
        if(unit.getAddon()!=null){
        	percepts.add(new AddonPercept(unit.getAddon().getType().getName()));
        }
        
        return percepts;
    }
}
