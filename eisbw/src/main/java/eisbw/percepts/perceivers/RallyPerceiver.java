package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;

import java.util.*;

import jnibwapi.*;
import jnibwapi.Position.Positions;

public class RallyPerceiver extends UnitPerceiver {
    public RallyPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        
        if(!unit.getRallyPosition().equals(Positions.None.toString())){
        	percepts.add(new RallyPointPercept(unit.getRallyPosition().getBX(), unit.getRallyPosition().getBY()));
        }
        
        if(unit.getRallyUnit() != null){
        	percepts.add(new RallyUnitPercept(unit.getRallyUnit().getID()));
        }
        
        return percepts;
    }
}
