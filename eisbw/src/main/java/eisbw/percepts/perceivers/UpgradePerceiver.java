package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.*;

import java.util.*;

import jnibwapi.*;

public class UpgradePerceiver extends UnitPerceiver {
    public UpgradePerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        
        if(unit.isUpgrading())
        	percepts.add(new UpgradePercept(unit.getUpgrade().getName()));
        
        return percepts;
    }
}
