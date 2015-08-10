package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.IsLoadedPercept;
import eisbw.percepts.StimPercept;

import java.util.*;

import jnibwapi.*;

public class IsLoadedUnitPerceiver extends UnitPerceiver {

    public IsLoadedUnitPerceiver(JNIBWAPI api, Unit unit) {
        super(api, unit);
    }

    @Override
    public List<Percept> perceive() {
        List<Percept> result = new ArrayList<>();
        if (unit.isLoaded()) {
            result.add(new IsLoadedPercept());
        }
        return result;
    }
}
