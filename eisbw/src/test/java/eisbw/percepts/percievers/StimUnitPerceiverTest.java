package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.StimUnitPerceiver;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StimUnitPerceiverTest {

  private StimUnitPerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    when(unit.isStimmed()).thenReturn(true);
    perciever = new StimUnitPerceiver(null, unit);
  }
  
  @Test
  public void test() {
    when(unit.isStimmed()).thenReturn(true);
    assertEquals("stimmed", perciever.perceive().get(0).getName());
  }
  
  @Test
  public void notStimmedtest() {
    when(unit.isStimmed()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());
  }

}
