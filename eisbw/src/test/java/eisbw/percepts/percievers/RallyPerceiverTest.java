package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.RallyPerceiver;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Position.Positions;
import jnibwapi.Unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RallyPerceiverTest {

  private RallyPerceiver perciever;
  @Mock
  private Unit unit;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    perciever = new RallyPerceiver(null, unit);
  }
  
  @Test
  public void rallyPoint_test() {
    when(unit.getRallyPosition()).thenReturn(new Position(1, 2, PosType.BUILD));
    assertEquals("rallyPoint", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("2", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  
  @Test
  public void rallyUnit_test() {
    when(unit.getRallyUnit()).thenReturn(unit);
    when(unit.getID()).thenReturn(3);
    when(unit.getRallyPosition()).thenReturn(Positions.None);
    assertEquals("rallyUnit", perciever.perceive().get(0).getName());
    assertEquals("3", perciever.perceive().get(0).getParameters().get(0).toProlog());
  }

}
