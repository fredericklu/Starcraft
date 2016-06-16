package eisbw.percepts;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;
import eis.iilang.TruthValue;

import java.util.List;

/**
 * @author Danny & Harm - The Unit percept which gives information about the
 *         other units.
 *
 */
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
   * @param conditions
   *          The current conditions of the unit
   */
  public UnitPercept(boolean isFriendly, String type, int id, int health, int shields,
      List<Parameter> conditions) {
    super(Percepts.UNIT, new TruthValue(isFriendly), new Identifier(type), new Numeral(id),
        new Numeral(health), new Numeral(shields), new ParameterList(conditions));
  }

}
