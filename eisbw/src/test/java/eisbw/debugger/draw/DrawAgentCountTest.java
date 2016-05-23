package eisbw.debugger.draw;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import eisbw.Game;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DrawAgentCountTest {

  private AgentCounter draw;

  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Game game;

  /**
   * Init mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);

    draw = new AgentCounter(game);

  }

  @Test
  public void test() {
    draw.draw(bwapi);
    verify(bwapi, times(1)).drawText(eq(new Position(10, 10, PosType.PIXEL)), eq("Agentcount: 0"),
        eq(true));
  }

}
