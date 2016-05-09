package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.IsMovingPerceiver;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IsMovingPerceiverTest {

  private IsMovingPerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    perciever = new IsMovingPerceiver(null, unit);
  }
  
  @Test
  public void moving_test() {
    when(unit.isMoving()).thenReturn(true);
    assertEquals("moving", perciever.perceive().get(0).getName());
  }
  
  @Test
  public void following_test() {
    when(unit.isFollowing()).thenReturn(true);
    assertEquals("following", perciever.perceive().get(0).getName());
  }
  
  @Test
  public void noPercept_test() {
    
    assertEquals(0, perciever.perceive().size());
  }

}
