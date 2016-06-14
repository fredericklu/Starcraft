package eisbw.actions;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.TechType;

import java.util.LinkedList;

/**
 * @author Danny & Harm - Researches a specified Tech Type.
 *
 */
public class Research extends StarcraftTechAction {

  /**
   * The Research constructor.
   * 
   * @param api
   *          The BWAPI
   */
  public Research(JNIBWAPI api) {
    super(api);
  }

  @Override
  public void execute(Unit unit, Action action) {
    LinkedList<Parameter> parameters = action.getParameters();
    TechType techType = getTechType(((Identifier) parameters.get(0)).getValue());
    unit.research(techType);
  }

  @Override
  public String toString() {
    return "research(Type)";
  }
}
