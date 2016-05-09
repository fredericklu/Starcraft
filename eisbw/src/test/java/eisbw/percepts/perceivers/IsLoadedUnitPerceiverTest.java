package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.IsLoadedUnitPerceiver;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IsLoadedUnitPerceiverTest {

  private IsLoadedUnitPerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    when(unit.isLoaded()).thenReturn(true);
    perciever = new IsLoadedUnitPerceiver(null, unit);
  }
  
  @Test
  public void test() {
    assertEquals("loaded", perciever.perceive().get(0).getName());
  }
  
  @Test
  public void notLoaded_test() {
    when(unit.isLoaded()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());
  }

}
