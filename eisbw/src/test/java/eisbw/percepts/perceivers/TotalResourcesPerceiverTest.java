package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.TotalResourcesPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TotalResourcesPerceiverTest {

  private TotalResourcesPerceiver perciever;
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Player player;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    perciever = new TotalResourcesPerceiver(bwapi);
    when(bwapi.getSelf()).thenReturn(player);
    when(player.getMinerals()).thenReturn(10);
    when(player.getCumulativeMinerals()).thenReturn(20);
    when(player.getGas()).thenReturn(30);
    when(player.getCumulativeGas()).thenReturn(40);
    when(player.getSupplyUsed()).thenReturn(50);
    when(player.getSupplyTotal()).thenReturn(60);
  }
  
  @Test
  public void test() {
    assertEquals("totalRes", perciever.perceive().get(0).getName());
    assertEquals("10", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("20", perciever.perceive().get(0).getParameters().get(1).toProlog());
    assertEquals("30", perciever.perceive().get(0).getParameters().get(2).toProlog());
    assertEquals("40", perciever.perceive().get(0).getParameters().get(3).toProlog());
    assertEquals("50", perciever.perceive().get(0).getParameters().get(4).toProlog());
    assertEquals("60", perciever.perceive().get(0).getParameters().get(5).toProlog());
  }

}
