package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

public class ResearchPercept extends Percept {

    public ResearchPercept(String tech) {
        super(Percepts.Research, new Identifier(tech));
    }

}
