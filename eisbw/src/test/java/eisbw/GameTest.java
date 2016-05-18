package eisbw;

import jnibwapi.JNIBWAPI;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameTest {

  private Game game;
  @Mock
  private StarcraftEnvironmentImpl env;
  @Mock
  private JNIBWAPI bwapi;
  
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    game = new Game(env);
  }
  
  @Test
  public void test() {
  }

}
