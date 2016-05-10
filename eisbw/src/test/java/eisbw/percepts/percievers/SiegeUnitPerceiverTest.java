package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.SiegeUnitPerceiver;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SiegeUnitPerceiverTest {

  private SiegeUnitPerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    perciever = new SiegeUnitPerceiver(null, unit);
  }
  
  @Test
  public void test() {
    when(unit.isSieged()).thenReturn(true);
    assertEquals("sieged", perciever.perceive().get(0).getName());
  }
  
  @Test
  public void notSieged_test() {
    when(unit.isSieged()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());
  }

}
