package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eis.iilang.Percept;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;
import jnibwapi.types.RaceType;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("deprecation")
public class GenericUnitPerceiverTest {

  private GenericUnitPerceiver perciever;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private Player self;
  @Mock
  private JNIBWAPI api;
  @Mock
  private RaceType race;
  
  private Set<Player> toReturn;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    toReturn = new HashSet<>();
    toReturn.add(self);
    when(api.getEnemies()).thenReturn(toReturn);

    when(self.getRace()).thenReturn(RaceTypes.None);
    
    when(api.getSelf()).thenReturn(self);
    when(self.getMinerals()).thenReturn(50);
    when(self.getGas()).thenReturn(90);
    when(self.getSupplyUsed()).thenReturn(10);
    when(self.getSupplyTotal()).thenReturn(20);
    
    when(unit.getID()).thenReturn(1);
    when(unit.getType()).thenReturn(unitType);
    when(unitType.getName()).thenReturn("type");

    when(unit.getHitPoints()).thenReturn(25);
    when(unit.getShields()).thenReturn(30);
    when(unit.getPosition()).thenReturn(new Position(2,1,PosType.BUILD));

    when(unit.getEnergy()).thenReturn(100);
    when(unitType.getMaxEnergy()).thenReturn(110);
    perciever = new GenericUnitPerceiver(api, unit);
  }

  @Test
  public void size_test() {
    Map<PerceptFilter, List<Percept>> ret = new HashMap<>();
    when(race.getName()).thenReturn("race");
    when(self.getRace()).thenReturn(race);
    assertEquals(5, perciever.perceive(ret).size());
    toReturn = new HashSet<>();
    when(api.getEnemies()).thenReturn(toReturn);
    ret = new HashMap<>();
    assertEquals(5, perciever.perceive(ret).size());
    when(unitType.getMaxEnergy()).thenReturn(0);
    when(unit.isDefenseMatrixed()).thenReturn(true);
    ret = new HashMap<>();
    assertEquals(4, perciever.perceive(ret).size());
    
  }
  
  @Test
  public void idle_test() {
    when(unit.isIdle()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    assertEquals("idle", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void completed_test() {
    when(unit.isCompleted()).thenReturn(false);
    assertEquals("beingConstructed", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void cloaked_test() {
    when(unit.isCloaked()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    assertEquals("cloaked", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void moving_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isMoving()).thenReturn(true);
    assertEquals("moving", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void following_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isFollowing()).thenReturn(true);
    assertEquals("following", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void loaded_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isLoaded()).thenReturn(true);
    assertEquals("loaded", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void stimmed_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(self.getRace()).thenReturn(RaceTypes.Terran);
    when(unit.isStimmed()).thenReturn(true);
    assertEquals("stimmed", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void sieged_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(self.getRace()).thenReturn(RaceTypes.Terran);
    when(unit.isSieged()).thenReturn(true);
    assertEquals("sieged", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void blinded_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isBlind()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    assertEquals("blinded", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void lockedDown_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isLockedDown()).thenReturn(true);
    assertEquals("lockDowned", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void irradiated_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isIrradiated()).thenReturn(true);
    assertEquals("irradiated", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void underStorm_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isUnderStorm()).thenReturn(true);
    assertEquals("underStorm", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void stasised_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isStasised()).thenReturn(true);
    assertEquals("stasised", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void maelstrommed_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isMaelstrommed()).thenReturn(true);
    assertEquals("maelstrommed", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void disruptionWebbed_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isUnderDisruptionWeb()).thenReturn(true);
    assertEquals("disruptionWebbed", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void ensnared_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isEnsnared()).thenReturn(true);
    assertEquals("ensnared", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void parasited_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isParasited()).thenReturn(true);
    assertEquals("parasited", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void plagued_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isPlagued()).thenReturn(true);
    assertEquals("plagued", perciever.getConditions().get(0).toProlog());
  }
  
  @Test
  public void darkSwarmed_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isUnderDarkSwarm()).thenReturn(true);
    assertEquals("darkSwarmed", perciever.getConditions().get(0).toProlog());
  }

}
