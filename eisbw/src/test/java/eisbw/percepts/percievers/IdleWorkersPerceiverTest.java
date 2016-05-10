package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.EnemyPerceiver;
import eisbw.percepts.perceivers.IdleWorkersPerceiver;
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

public class IdleWorkersPerceiverTest {

  private IdleWorkersPerceiver perciever;
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
    perciever = new IdleWorkersPerceiver(bwapi);
    
    when(unit.getType()).thenReturn(unitType);
    when(unitType.getName()).thenReturn("unitType");
    when(unitType.isWorker()).thenReturn(true);
    when(unit.isIdle()).thenReturn(true);
    when(unit.getID()).thenReturn(0);
    
    when(bwapi.getMyUnits()).thenReturn(toreturn);
  }
  
  @Test
  public void test() {
    assertEquals("idleWorker", perciever.perceive().get(0).getName());
    assertEquals("unitType0", perciever.perceive().get(0).getParameters().get(0).toProlog());
  }
  
  @Test
  public void notIdle_test() {
    when(unit.isIdle()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());
  }
  
  @Test
  public void notWorker_test() {
    when(unitType.isWorker()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());
  }
}
