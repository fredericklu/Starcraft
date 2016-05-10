package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.LiftUnitPerceiver;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LiftUnitPerceiverTest {

  private LiftUnitPerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    perciever = new LiftUnitPerceiver(null, unit);
  }
  
  @Test
  public void test() {
    when(unit.isLifted()).thenReturn(true);
    assertEquals("lifted", perciever.perceive().get(0).getName());
  }
  
  @Test
  public void notLifted_test() {
    assertEquals(0, perciever.perceive().size());
  }

}
