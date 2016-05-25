package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

public class UnitPercept extends Percept {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor UnitPercept.
   * 
   * @param isFriendly
   *          Indicates whether the unit is friendly or not
   * @param type
   *          The unit type
   * @param id
   *          The ID of the unit
   * @param health
   *          The amount of health of the unit
   * @param shields
   *          The amount of shields of the unit
   */
  public UnitPercept(boolean isFriendly, String type, int id, int health, int shields) {
    super(Percepts.UNIT, new TruthValue(isFriendly), new Identifier(type), new Numeral(id),
        new Numeral(health), new Numeral(shields));
  }

}
