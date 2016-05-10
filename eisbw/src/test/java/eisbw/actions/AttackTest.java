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
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class AttackTest {

  private Attack action;
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
    action = new Attack(bwapi);

    params = new LinkedList<>();
    params.add(new Numeral(1));

    when(act.getParameters()).thenReturn(params);
    when(unit.getType()).thenReturn(unitType);
  }

  @Test
  public void isValid_test() {
    assertTrue(action.isValid(act));
    params.set(0, new Identifier("Not Working"));
    assertFalse(action.isValid(act));
    params.set(0, new Numeral(1));
    params.add(new Numeral(10));
    assertFalse(action.isValid(act));
  }

  @Test
  public void canExecute_test() {
    when(unitType.isAttackCapable()).thenReturn(true);
    assertTrue(action.canExecute(unit, act));
    when(unitType.isAttackCapable()).thenReturn(false);
    assertFalse(action.canExecute(unit, act));
    
  }

  @Test
  public void execute_test() {
    when(bwapi.getUnit(1)).thenReturn(unit);
    when(unitType.isAttackCapable()).thenReturn(true);
    action.execute(unit, act);
    verify(unit).attack(unit, false);
  }

  @Test
  public void toString_test() {
    assertEquals("attack(targetId)", action.toString());
  }

}
