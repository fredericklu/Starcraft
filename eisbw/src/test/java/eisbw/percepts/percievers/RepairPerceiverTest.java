package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.RepairPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class RepairPerceiverTest {

  private RepairPerceiver perciever;
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
    perciever = new RepairPerceiver(bwapi);

    when(unit.getType()).thenReturn(unitType);
    when(unitType.getName()).thenReturn("unitType");
    when(unitType.isMechanical()).thenReturn(true);
    when(unit.getHitPoints()).thenReturn(20);
    when(unitType.getMaxHitPoints()).thenReturn(30);
    when(unit.getID()).thenReturn(1);

    when(bwapi.getMyUnits()).thenReturn(toreturn);
  }

  @Test
  public void test() {
    assertEquals("requiresRepair", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("unitType", perciever.perceive().get(0).getParameters().get(1).toProlog());
    assertEquals("20", perciever.perceive().get(0).getParameters().get(2).toProlog());
    assertEquals("30", perciever.perceive().get(0).getParameters().get(3).toProlog());
  }

  @Test
  public void noPercept_test() {
    when(unit.getHitPoints()).thenReturn(999);
    assertEquals(0, perciever.perceive().size());
  }

  @Test
  public void notMechanical_test() {
    when(unitType.isMechanical()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());
  }

}
