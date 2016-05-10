package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.WorkerActivityPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class WorkerActivityPerceiverTest {

  private WorkerActivityPerceiver perciever;
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
    perciever = new WorkerActivityPerceiver(bwapi);

    when(unit.getType()).thenReturn(unitType);
    when(unitType.getName()).thenReturn("unitType");
    when(unitType.isWorker()).thenReturn(true);

    when(unit.getID()).thenReturn(1);

    when(bwapi.getMyUnits()).thenReturn(toreturn);
  }

  @Test
  public void gas_test() {
    when(unit.isGatheringGas()).thenReturn(true);
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("gatheringGas", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void minerals_test() {
    when(unit.isGatheringMinerals()).thenReturn(true);
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("gatheringMinerals",
        perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void constructing_test() {
    when(unit.isConstructing()).thenReturn(true);
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("constructing", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void idling_test() {
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("idling", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void noPercept_test() {
    when(unitType.isWorker()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());
  }

}
