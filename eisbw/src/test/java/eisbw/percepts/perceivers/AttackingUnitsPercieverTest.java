package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.AttackingUnitsPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class AttackingUnitsPercieverTest {

  private AttackingUnitsPerceiver perciever;
  @Mock
  private JNIBWAPI bwapi;
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
    ArrayList<Unit> returnlist = new ArrayList<Unit>();
    returnlist.add(unit);
    when(bwapi.getMyUnits()).thenReturn(returnlist);
    when(unit.getOrderTarget()).thenReturn(unit);
    when(unit.getType()).thenReturn(unitType);
    when(unit.getID()).thenReturn(1);
    when(unitType.isAttackCapable()).thenReturn(true);
    perciever = new AttackingUnitsPerceiver(bwapi);
  }

  @Test
  public void test() {
    assertEquals("attacking", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void targetNull_test() {
    when(unit.getOrderTarget()).thenReturn(null);
    assertEquals(0, perciever.perceive().size());
  }

  @Test
  public void notAttackCapable_test() {
    when(unitType.isAttackCapable()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());

    when(unitType.isBuilding()).thenReturn(true);
    assertEquals("attacking", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

}