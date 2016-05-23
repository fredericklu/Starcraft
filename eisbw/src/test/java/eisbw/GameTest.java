package eisbw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import eis.iilang.Percept;
import eisbw.percepts.ConstructionSitePercept;
import eisbw.units.StarcraftUnit;
import eisbw.units.Units;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameTest {

  private Game game;
  private List<Percept> percepts;
  private Map<String, StarcraftUnit> unitList;

  @Mock
  private StarcraftEnvironmentImpl env;
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private StarcraftUnit scUnit;
  @Mock
  private Units units;
  @Mock
  private jnibwapi.Map map;

  /**
   * Init mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);

    unitList = new HashMap<>();
    unitList.put("unit", scUnit);

    percepts = new LinkedList<>();
    percepts.add(new ConstructionSitePercept(1, 2));

    when(scUnit.perceive()).thenReturn(percepts);

    when(units.getStarcraftUnits()).thenReturn(unitList);

    when(env.getAgents()).thenReturn(new LinkedList<String>());
    
    game = new Game(env);
    game.units = units;
  }

  @Test
  public void update_test() {
    game.update(bwapi);
    assertTrue(game.getUnits() == units);
    assertTrue(game.getPercepts("null").isEmpty());
    assertEquals(1, game.getPercepts("unit").size());
  }

  @Test
  public void constructionsites_test() {
    when(bwapi.getMap()).thenReturn(map);
    when(map.getSize()).thenReturn(new Position(0, 0));
    game.updateConstructionSites(bwapi);
    assertTrue(game.getConstructionSites().isEmpty());
    game.clean();
    assertEquals(0, game.getAgentCount());
  }

}
