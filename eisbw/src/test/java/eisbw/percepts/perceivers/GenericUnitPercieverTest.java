package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

public class GenericUnitPercieverTest {

  private GenericUnitPerceiver perciever;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private Player self;
  @Mock
  private JNIBWAPI api;
  
  private Set<Player> toReturn;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    when(api.getEnemies()).thenReturn(toReturn);
    
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
  public void idle_test() {
    assertEquals("idle", perciever.perceive().get(0).getName());
  }

  @Test
  public void notidle_test() {
    when(unit.isIdle()).thenReturn(false);
    assertNotEquals("idle", perciever.perceive().get(0).getName());
  }
  
  @Test
  public void id_test() {
    assertEquals("id", perciever.perceive().get(1).getName());
    assertEquals("1", perciever.perceive().get(1).getParameters().get(0).toProlog());
  }
  
  @Test
  public void unittype_test() {
    assertEquals("unitType", perciever.perceive().get(2).getName());
    assertEquals("unitType", perciever.perceive().get(2).getParameters().get(0).toProlog());
  }
  
  @Test
  public void isConstructing_test() {
    assertEquals("isBeingConstructed", perciever.perceive().get(3).getName());
  }
  
  @Test
  public void isNotConstructing_test() {
    when(unit.isCompleted()).thenReturn(true);
    assertNotEquals("isBeingConstructed", perciever.perceive().get(3).getName());
  }
  
  @Test
  public void position_test() {
    assertEquals("position", perciever.perceive().get(4).getName());
    assertEquals("2", perciever.perceive().get(4).getParameters().get(0).toProlog());
    assertEquals("3", perciever.perceive().get(4).getParameters().get(1).toProlog());
  }
  
  @Test
  public void stuck_test() {
    assertEquals("isStuck", perciever.perceive().get(5).getName());
  }
  
  @Test
  public void notStuck_test() {
    when(unit.isStuck()).thenReturn(false);
    assertNotEquals("isStuck", perciever.perceive().get(5).getName());
  }
  
  @Test
  public void energy_test() {
    assertEquals("energy", perciever.perceive().get(6).getName());
    assertEquals("20", perciever.perceive().get(6).getParameters().get(0).toProlog());
    assertEquals("30", perciever.perceive().get(6).getParameters().get(1).toProlog());
  }

}
