package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import eis.iilang.Percept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.Position.PosType;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    when(unit.getPosition()).thenReturn(new Position(10,12,PosType.BUILD));
  }

  @Test
  public void test() {
    Map<PerceptFilter, List<Percept>> ret = new HashMap<>();
    assertFalse(perceiver.perceive(ret).isEmpty());
  }

  @Test
  public void attackcapable_test() {
    when(unitType.isAttackCapable()).thenReturn(true);
    when(unit.getOrderTarget()).thenReturn(unit);
    Map<PerceptFilter, List<Percept>> ret = new HashMap<>();
    assertEquals(2, perceiver.perceive(ret).size());
    when(unit.getOrderTarget()).thenReturn(null);
    ret = new HashMap<>();
    assertEquals(2, perceiver.perceive(ret).size());
  }

  @Test
  public void conditions_test() {
    assertTrue(perceiver.getConditions().isEmpty());
  }

}