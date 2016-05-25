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

public class UnitsPerceiverTest {

  private UnitsPerceiver perceiver;
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
    perceiver = new UnitsPerceiver(bwapi);

    when(unit.getType()).thenReturn(unitType);
    when(unitType.getName()).thenReturn("unitType");
    when(unitType.isFlyer()).thenReturn(true);
    when(unit.isMorphing()).thenReturn(true);
    when(unit.isCloaked()).thenReturn(false);
    when(unit.getID()).thenReturn(1);
    when(unit.getHitPoints()).thenReturn(20);
    when(unit.getShields()).thenReturn(10);

    when(bwapi.getEnemyUnits()).thenReturn(toreturn);
  }

  @Test
  public void test() {
    assertEquals("unit", perceiver.perceive().get(0).getName());
    assertEquals("false", perceiver.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("unitType", perceiver.perceive().get(0).getParameters().get(1).toProlog());
    assertEquals("1", perceiver.perceive().get(0).getParameters().get(2).toProlog());
    assertEquals("20", perceiver.perceive().get(0).getParameters().get(3).toProlog());
    assertEquals("10", perceiver.perceive().get(0).getParameters().get(4).toProlog());
    assertEquals("[flying,morphing]",
        perceiver.perceive().get(0).getParameters().get(5).toProlog());
  }

  @Test
  public void attackcapable_test() {
    when(unitType.isAttackCapable()).thenReturn(true);
    when(unit.getOrderTarget()).thenReturn(unit);
    assertEquals(2, perceiver.perceive().size());
    when(unit.getOrderTarget()).thenReturn(null);
    assertEquals(1, perceiver.perceive().size());
  }

  @Test
  public void conditions_test() {
    assertTrue(perceiver.getConditions().isEmpty());
  }

}
