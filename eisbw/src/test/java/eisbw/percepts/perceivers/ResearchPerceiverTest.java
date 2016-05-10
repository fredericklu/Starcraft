package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.ResearchPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Unit;
import jnibwapi.types.TechType;
import jnibwapi.types.TechType.TechTypes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ResearchPerceiverTest {

  private ResearchPerceiver perciever;
  @Mock
  private Unit unit;
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Player player;
  @Mock
  private TechType techType;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    perciever = new ResearchPerceiver(bwapi, unit);
    when(unit.getTech()).thenReturn(techType);
    when(techType.getID()).thenReturn(20);
    when(techType.getName()).thenReturn("techType");
    when(bwapi.getSelf()).thenReturn(player);
  }
  
  @Test
  public void test() {
    assertEquals("research", perciever.perceive().get(0).getName());
    assertEquals("techType", perciever.perceive().get(0).getParameters().get(0).toProlog());
  }
  
  @Test
  public void noPercept_test() {
    when(unit.getTech()).thenReturn(TechTypes.None);
    assertEquals(0, perciever.perceive().size());
  }
  
  @Test
  public void isResearched_test() {
    when(unit.getTech()).thenReturn(TechTypes.None);
    when(player.isResearched(TechTypes.Dark_Archon_Meld)).thenReturn(true);
    assertEquals("hasResearched", perciever.perceive().get(0).getName());
  }
}
