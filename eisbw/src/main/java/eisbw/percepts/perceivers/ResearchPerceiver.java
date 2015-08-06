package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;

import java.util.*;

import jnibwapi.*;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;

public class ResearchPerceiver extends UnitPerceiver {
    public ResearchPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        
        if(unit.getTech().getID() != TechTypes.None.getID())
        	percepts.add(new ResearchPercept(unit.getTech().getName()));
        
        return percepts;
    }
}
