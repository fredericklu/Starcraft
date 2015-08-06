package eisbw.actions;

import eis.exceptions.ActException;
import eis.iilang.*;

import java.util.LinkedList;

import jnibwapi.*;
import jnibwapi.Position.PosType;
import jnibwapi.types.TechType;

public class SetRallyPoint extends StarcraftAction {

    public SetRallyPoint(JNIBWAPI api) {
        super(api);
    }

    @Override
    public boolean isValid(Action action) {
        LinkedList<Parameter> parameters = action.getParameters();
        if (parameters.size() == 2) { // type
            return parameters.get(0) instanceof Numeral
                    && parameters.get(1) instanceof Numeral;
        }

        return false;
    }

    @Override
    public boolean canExecute(Unit unit, Action action) {
        return unit.getType().isBuilding();
    }

    @Override
    public void execute(Unit unit, Action action) throws ActException {
        LinkedList<Parameter> parameters = action.getParameters();
        int x = ((Numeral) parameters.get(0)).getValue().intValue();
        int y = ((Numeral) parameters.get(1)).getValue().intValue();
        unit.setRallyPoint(new Position(x, y, PosType.BUILD));
    }

    @Override
    public String toString() {
        return "setRallyPoint(x,y)";
    }
}
