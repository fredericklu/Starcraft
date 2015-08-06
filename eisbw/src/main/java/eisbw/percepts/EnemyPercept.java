package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

public class EnemyPercept extends Percept {

    public EnemyPercept(String name, int id, int health, int shields, int wX, int wY, int bX, int bY) {
        super(Percepts.Enemy, new Identifier(name), new Numeral(id),new Numeral(health), new Numeral(shields), new Numeral(wX), new Numeral(wY), new Numeral(bX), new Numeral(bY));
    }

}
