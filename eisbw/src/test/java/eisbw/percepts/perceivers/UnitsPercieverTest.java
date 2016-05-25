package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class UnitsPercieverTest {

  private UnitsPerceiver perciever;
  private LinkedList<Unit> toreturn;
  @Mock
  private Unit unit;
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private UnitType unitType;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    toreturn = new LinkedList<>();
    toreturn.add(unit);
    perciever = new UnitsPerceiver(bwapi);

    when(unit.getType()).thenReturn(unitType);
    when(unitType.getName()).thenReturn("unitType");
    when(unitType.isFlyer()).thenReturn(true);
    when(unit.isMorphing()).thenReturn(true);
    when(unit.isCloaked()).thenReturn(false);
    when(unit.getID()).thenReturn(1);
    when(unit.getHitPoints()).thenReturn(20);
    when(unit.getShields()).thenReturn(10);
    when(unit.getPosition()).thenReturn(new Position(34, 45, PosType.BUILD));

    when(bwapi.getEnemyUnits()).thenReturn(toreturn);
  }

  @Test
  public void test() {
    assertEquals("unit", perciever.perceive().get(0).getName());
    assertEquals("false", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("unitType", perciever.perceive().get(0).getParameters().get(1).toProlog());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(2).toProlog());
    assertEquals("20", perciever.perceive().get(0).getParameters().get(3).toProlog());
    assertEquals("10", perciever.perceive().get(0).getParameters().get(4).toProlog());
    assertEquals("true", perciever.perceive().get(0).getParameters().get(5).toProlog());
    assertEquals("true", perciever.perceive().get(0).getParameters().get(6).toProlog());
    assertEquals("false", perciever.perceive().get(0).getParameters().get(7).toProlog());
    assertEquals("34", perciever.perceive().get(0).getParameters().get(8).toProlog());
    assertEquals("45", perciever.perceive().get(0).getParameters().get(9).toProlog());
  }

  @Test
  public void attackcapable_test() {
    when(unitType.isAttackCapable()).thenReturn(true);
    when(unit.getOrderTarget()).thenReturn(unit);
    assertEquals(2, perciever.perceive().size());
    when(unit.getOrderTarget()).thenReturn(null);
    assertEquals(1, perciever.perceive().size());
  }

  @Test
  public void conditions_test() {
    assertTrue(perciever.getConditions().isEmpty());
  }

}
