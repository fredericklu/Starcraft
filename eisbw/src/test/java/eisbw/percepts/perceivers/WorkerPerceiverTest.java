package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

public class WorkerPerceiverTest {

  private WorkerPerceiver perciever;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private JNIBWAPI api;
  @Mock
  private Player self;
  private List<Unit> toreturn;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);

    when(unit.getType()).thenReturn(unitType);
    when(unitType.getID()).thenReturn(RaceTypes.Terran.getID());
    when(api.getNeutralUnits()).thenReturn(new LinkedList<Unit>());
    toreturn = new LinkedList<>();
    toreturn.add(unit);
    when(api.getMyUnits()).thenReturn(toreturn);

    perciever = new WorkerPerceiver(api, unit);
  }

  @Test
  public void gas_test() {
    when(unitType.isWorker()).thenReturn(true);
    when(unitType.isMechanical()).thenReturn(true);
    when(unit.isGatheringGas()).thenReturn(true);
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("gatheringGas", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void minerals_test() {
    when(unitType.isWorker()).thenReturn(true);
    when(unitType.isMechanical()).thenReturn(true);
    when(unit.isGatheringMinerals()).thenReturn(true);
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("gatheringMinerals",
        perciever.perceive().get(0).getParameters().get(1).toProlog());
    when(unit.getOrderTarget()).thenReturn(unit);
    assertEquals("0",
        perciever.perceive().get(1).getParameters().get(0).toProlog());
  }

  @Test
  public void constructing_test() {
    when(unitType.isWorker()).thenReturn(true);
    when(unitType.isMechanical()).thenReturn(true);
    when(unit.isConstructing()).thenReturn(true);
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("constructing", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void idle_test() {
    when(unitType.isWorker()).thenReturn(true);
    when(unitType.isMechanical()).thenReturn(true);
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("idling", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void noTerran_test() {
    when(unitType.getID()).thenReturn(RaceTypes.None.getID());
    when(unitType.isWorker()).thenReturn(true);
    assertEquals("workerActivity", perciever.perceive().get(0).getName());
    assertEquals("idling", perciever.perceive().get(0).getParameters().get(1).toProlog());
    when(unitType.isWorker()).thenReturn(false);
    assertTrue(perciever.perceive().isEmpty());
  }

  @Test
  public void repair_test() {
    when(unitType.isMechanical()).thenReturn(true);
    when(unit.getHitPoints()).thenReturn(0);
    when(unitType.getMaxHitPoints()).thenReturn(1);
    assertEquals("requiresRepair", perciever.perceive().get(0).getName());
    when(unit.getHitPoints()).thenReturn(1);
    assertTrue(perciever.perceive().isEmpty());    
  }


  @Test
  public void geyser_test() {
    when(api.getNeutralUnits()).thenReturn(toreturn);
    when(unit.getType()).thenReturn(UnitType.UnitTypes.Resource_Vespene_Geyser);
    when(unit.getPosition()).thenReturn(new Position(1, 2));
    assertEquals("vespeneGeyser", perciever.perceive().get(0).getName());
    when(unit.getType()).thenReturn(unitType);
    assertTrue(perciever.perceive().isEmpty());        
  }

  @Test
  public void conditions_gas_test() {
    when(unit.isCarryingGas()).thenReturn(true);
    assertEquals(1, perciever.getConditions().size());
  }

  @Test
  public void conditions_minerals_test() {
    when(unit.isCarryingMinerals()).thenReturn(true);
    assertEquals(1, perciever.getConditions().size());
  }

  @Test
  public void conditions_constructing_test() {
    when(unit.isConstructing()).thenReturn(true);
    assertEquals(1, perciever.getConditions().size());
  }

}
