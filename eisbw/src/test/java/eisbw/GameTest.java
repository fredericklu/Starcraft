package eisbw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import eis.eis2java.translation.Filter;
import eis.iilang.Percept;
import eisbw.percepts.ConstructionSitePercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.perceivers.PerceptFilter;
import eisbw.units.StarcraftUnit;
import eisbw.units.Units;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class GameTest {

  private Game game;
  private Map<PerceptFilter, Set<Percept>> percepts;
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
  private Unit unit;
  @Mock
  private UnitType type;
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

    percepts = new HashMap<>();
    Set<Percept> perc = new HashSet<>();
    perc.add(new ConstructionSitePercept(1, 2));
    percepts.put(new PerceptFilter(Percepts.CONSTRUCTIONSITE, Filter.Type.ALWAYS),perc);

    when(scUnit.perceive()).thenReturn(percepts);

    when(units.getStarcraftUnits()).thenReturn(unitList);
    
    Map<String,Unit> unitMap = new HashMap<>();
    unitMap.put("unit", unit);
    when(unit.getType()).thenReturn(type);
    when(type.isWorker()).thenReturn(true);
    when(units.getUnits()).thenReturn(unitMap);

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
