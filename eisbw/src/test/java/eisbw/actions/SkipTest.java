package eisbw.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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

public class SkipTest {

  private Skip action;
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
    action = new Skip(bwapi);
    
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
    assertTrue(action.canExecute(unit, act));
  }
  
  @Test
  public void execute_test() {
    action.execute(unit, act);
    verifyNoMoreInteractions(unit);
  }
  
  @Test
  public void toString_test() {
    assertEquals("skip", action.toString());
  }

}
