package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.percepts.EnemyPercept;
import java.util.*;
import jnibwapi.*;

public class EnemyPerceiver extends Perceiver {

    public EnemyPerceiver(JNIBWAPI api) {
        super(api);
    }

    @Override
    public List<Percept> perceive() {
        ArrayList<Percept> percepts = new ArrayList<>();
        List<Unit> enemies = api.getEnemyUnits();

        for (Unit unit : enemies) {
            percepts.add(new EnemyPercept(api.getUnitType(unit.getTypeID()).getName(), unit.getID(), unit.getX(), unit.getY()));
        }

        return percepts;
    }
}