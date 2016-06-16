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
import jnibwapi.types.UpgradeType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class UpgradeTest {

  private Upgrade action;
  private LinkedList<Parameter> params;
  private String upgradeType;

  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Action act;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private UpgradeType upgrade;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    action = new Upgrade(bwapi);

    upgradeType = "Terran Infantry Armor";

    params = new LinkedList<>();
    params.add(new Identifier("Terran Infantry Armor"));
    params.add(new Numeral(2));

    when(act.getParameters()).thenReturn(params);
    when(unit.getType()).thenReturn(unitType);
  }

  @Test
  public void isValid_test() {
    StarcraftAction spyAction = Mockito.spy(action);

    when(spyAction.getUpgradeType(upgradeType)).thenReturn(upgrade);
    
    params.removeLast();
    assertTrue(spyAction.isValid(act));
    params.add(new Numeral(2));
    assertFalse(action.isValid(act));
    params.remove(1);
    assertFalse(action.isValid(act));
    params.set(0, new Numeral(1));
    assertFalse(action.isValid(act));
    params.set(0, new Identifier("Hero Mojo"));
    assertFalse(action.isValid(act));
  }

  @Test
  public void canExecute_test() {
    when(unitType.isBuilding()).thenReturn(true);
    assertTrue(action.canExecute(unit, act));
  }

  @Test
  public void execute_test() {
    action.execute(unit, act);
    verify(unit).upgrade(null);
  }

  @Test
  public void toString_test() {
    assertEquals("upgrade(Type)", action.toString());
  }

}
