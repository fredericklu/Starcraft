package eisbw.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class LiftTest {

  private Lift action;
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
    action = new Lift(bwapi);
    
    params = new LinkedList<>();
    params.add(new Identifier("Working"));
    
    when(act.getParameters()).thenReturn(params);
    when(unit.getType()).thenReturn(unitType);
  }

  @Test
  public void isValid_test() {
    assertFalse(action.isValid(act));
    params.remove(0);
    assertTrue(action.isValid(act));
  }
  
  @Test
  public void canExecute_test() {
    when(unitType.isBuilding()).thenReturn(false);
    when(unitType.getRaceID()).thenReturn(2);
    assertFalse(action.canExecute(unit, act));
    when(unitType.getRaceID()).thenReturn(1);
    assertFalse(action.canExecute(unit, act));
    when(unitType.isBuilding()).thenReturn(true);
    assertTrue(action.canExecute(unit, act));
    when(unitType.getRaceID()).thenReturn(2);
    assertFalse(action.canExecute(unit, act));
  }
  
  @Test
  public void execute_test() {
    action.execute(unit, act);
    verify(unit).lift();
  }
  
  @Test
  public void toString_test() {
    assertEquals("lift()", action.toString());
  }

}
