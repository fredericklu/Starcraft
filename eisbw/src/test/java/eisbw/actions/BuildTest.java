package eisbw.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class BuildTest {

  private Build action;
  private LinkedList<Parameter> params;

  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Action act;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    action = new Build(bwapi);

    params = new LinkedList<>();
    params.add(new Identifier("Working"));
    params.add(new Numeral(2));

    when(act.getParameters()).thenReturn(params);
    when(unit.getType()).thenReturn(unitType);
  }

  @Test
  public void isValid_test() {
    StarcraftAction spyAction = Mockito.spy(action);

    when(spyAction.getUnitType("Working")).thenReturn(unitType);
    when(unitType.isBuilding()).thenReturn(true);

    params.add(new Numeral(3));

    assertTrue(spyAction.isValid(act));
    params.set(0, new Numeral(9));
    assertFalse(spyAction.isValid(act));
    params.remove(0);
    assertFalse(spyAction.isValid(act));
    params.add(0, new Identifier("Working"));
    params.set(2, new Identifier("falser"));
    assertFalse(spyAction.isValid(act));
    params.set(1, new Identifier("false"));
    assertFalse(spyAction.isValid(act));
    params.set(1, new Numeral(2));
    assertFalse(spyAction.isValid(act));
    when(unitType.isBuilding()).thenReturn(false);
    assertFalse(spyAction.isValid(act));
    params.add(0, new Identifier("Working"));
    when(spyAction.getUnitType("Working")).thenReturn(null);
    unitType = null;
    assertFalse(spyAction.isValid(act));
  }

  @Test
  public void canExecute_test() {
    when(unitType.isWorker()).thenReturn(false);
    assertFalse(action.canExecute(unit, act));
    when(unitType.isWorker()).thenReturn(true);
    assertTrue(action.canExecute(unit, act));
  }

  @Test
  public void execute_test() {
    params.set(0, new Identifier("null"));
    params.set(1, new Numeral(1));
    params.add(new Numeral(2));
    action.execute(unit, act);
    verify(unit).build(new Position(1, 2, PosType.BUILD), null);
  }

  @Test
  public void toString_test() {
    assertEquals("build(Type, X, Y)", action.toString());
  }

}
