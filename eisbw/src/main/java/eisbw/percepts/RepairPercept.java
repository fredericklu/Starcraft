package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;
import jnibwapi.Unit;

public class RepairPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * Repair percept.
   */
  public RepairPercept(Unit unit) {
    super(Percepts.REQUIRESREPAIR, new Numeral(unit.getID()), new Identifier(
        unit.getType().getName()), new Numeral(unit.getHitPoints()), 
        new Numeral(unit.getType().getMaxHitPoints()));
  }
}
