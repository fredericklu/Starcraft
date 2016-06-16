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
import jnibwapi.Player;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class MorphTest {

  private Morph action;
  private LinkedList<Parameter> params;

  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Action act;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private Player player;
  @Mock
  private UnitType type;
  
  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    action = new Morph(bwapi);
    
    params = new LinkedList<>();
    params.add(new Identifier("Zerg Hydralisk"));
    params.add(new Numeral(2));
    
    when(act.getParameters()).thenReturn(params);
    when(unit.getType()).thenReturn(unitType);
    when(bwapi.getSelf()).thenReturn(player);
  }

  @Test
  public void isValid_test() {
    StarcraftAction spyAction = Mockito.spy(action);

    when(spyAction.getUnitType("Zerg Hydralisk")).thenReturn(unitType);
    
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
    when(player.getRace()).thenReturn(RaceTypes.Terran);
    assertFalse(action.canExecute(unit, act));
    when(player.getRace()).thenReturn(RaceTypes.Zerg);
    assertTrue(action.canExecute(unit, act));
  }
  
  @Test
  public void execute_test() {
    params.set(0, new Identifier("null"));
    action.execute(unit, act);
    verify(unit).morph(null);
  }
  
  @Test
  public void toString_test() {
    assertEquals("morph(Type)", action.toString());
  }

}
