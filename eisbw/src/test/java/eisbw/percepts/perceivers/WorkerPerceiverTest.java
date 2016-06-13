package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import eis.iilang.Percept;
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    Map<PerceptFilter, Set<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
  }

  @Test
  public void minerals_test() {
    when(unitType.isWorker()).thenReturn(true);
    when(unitType.isMechanical()).thenReturn(true);
    when(unit.isGatheringMinerals()).thenReturn(true);


    Map<PerceptFilter, Set<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
  }

  @Test
  public void constructing_test() {
    when(unitType.isWorker()).thenReturn(true);
    when(unitType.isMechanical()).thenReturn(true);
    when(unit.isConstructing()).thenReturn(true);
    Map<PerceptFilter, Set<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
  }

  @Test
  public void idle_test() {
    when(unitType.isWorker()).thenReturn(true);
    when(unitType.isMechanical()).thenReturn(true);
    Map<PerceptFilter, Set<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
  }

  @Test
  public void noTerran_test() {
    when(unitType.getID()).thenReturn(RaceTypes.None.getID());
    when(unitType.isWorker()).thenReturn(true);
    Map<PerceptFilter, Set<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
    when(unitType.isWorker()).thenReturn(false);
    assertFalse(perciever.perceive(ret).isEmpty());
  }

  @Test
  public void repair_test() {
    when(unitType.isMechanical()).thenReturn(true);
    when(unit.getHitPoints()).thenReturn(0);
    when(unitType.getMaxHitPoints()).thenReturn(1);
    Map<PerceptFilter, Set<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
    when(unit.getHitPoints()).thenReturn(1);
    assertFalse(perciever.perceive(ret).isEmpty());    
  }


  @Test
  public void geyser_test() {
    when(api.getNeutralUnits()).thenReturn(toreturn);
    when(unit.getType()).thenReturn(UnitType.UnitTypes.Resource_Vespene_Geyser);
    when(unit.getPosition()).thenReturn(new Position(1, 2));
    Map<PerceptFilter, Set<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
    when(unit.getType()).thenReturn(unitType);
    assertFalse(perciever.perceive(ret).isEmpty());        
  }



}
