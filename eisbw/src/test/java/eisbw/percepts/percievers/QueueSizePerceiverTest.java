package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.QueueSizePerceiver;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class QueueSizePerceiverTest {

  private QueueSizePerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    perciever = new QueueSizePerceiver(null, unit);
    when(unit.getTrainingQueueSize()).thenReturn(1);
  }
  
  @Test
  public void test() {
    assertEquals("queueSize", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
  }

}
