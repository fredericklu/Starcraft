package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;

public class LoadablePercept extends Percept {
    public LoadablePercept(String agentName, String typeName, int id) {
        super(Percepts.Loadable, new Identifier(agentName), new Identifier(typeName), new Numeral(id));
    }
}
