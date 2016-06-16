package eisbw;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import eis.exceptions.ManagementException;
import eisbw.units.Units;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UpdateThreadTest {

  private Queue<Unit> queue;

  private String name;

  private UpdateThread update;
  @Mock
  private Game game;
  @Mock
  private JNIBWAPI api;
  @Mock
  private Units units;
  @Mock
  private Unit unit;
  @Mock
  private UnitType type;
  @Mock
  private StarcraftEnvironmentImpl env;

  /**
   * init environment and mocks.
   * 
   * @throws ManagementException
   *           - from environment
   */
  @Before
  public void start() throws ManagementException {
    MockitoAnnotations.initMocks(this);

    name = "name";
    queue = new ConcurrentLinkedQueue<Unit>();
    queue.add(unit);

    doNothing().when(game).update(api);
    when(units.getUninitializedUnits()).thenReturn(queue);
    when(game.getUnits()).thenReturn(units);
    when(unit.getType()).thenReturn(type);
    when(type.getName()).thenReturn(name);
    when(unit.getID()).thenReturn(0);

    update = new UpdateThread(game, api);
  }

  @Test
  public void test_false_case() {
    update.update();
    update.terminate();
    assertEquals(units.getUninitializedUnits().size(), 1);
  }

  @Test
  public void test_true_case() {
    when(game.isInitialized(name + "0")).thenReturn(true);
    when(game.getEnvironment()).thenReturn(env);

    update.update();
    update.terminate();

    assertEquals(units.getUninitializedUnits().size(), 0);
  }

}
