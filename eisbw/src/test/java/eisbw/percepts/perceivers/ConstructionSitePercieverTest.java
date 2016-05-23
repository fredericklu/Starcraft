package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ConstructionSitePercieverTest {

  private ConstructionSitePerceiver perciever;
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private jnibwapi.Map map;
  @Mock
  private Position mapsize;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private Player player;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    ArrayList<Unit> neutrals = new ArrayList<>();
    neutrals.add(unit);
    when(unit.getType()).thenReturn(unitType);
    when(unit.isExists()).thenReturn(true);
    when(unit.getTilePosition()).thenReturn(mapsize);
    when(unitType.getName()).thenReturn("Resource Mineral Field");

    when(bwapi.canBuildHere(any(Position.class), any(UnitType.class), any(Boolean.class)))
        .thenReturn(true);
    when(bwapi.getSelf()).thenReturn(player);
    when(player.getRace()).thenReturn(RaceTypes.Terran);

    when(bwapi.getMap()).thenReturn(map);
    when(map.getSize()).thenReturn(mapsize);
    when(mapsize.getBX()).thenReturn(10);
    when(mapsize.getBY()).thenReturn(10);
    when(bwapi.getNeutralUnits()).thenReturn(neutrals);
    perciever = new ConstructionSitePerceiver(bwapi);
  }

  @Test
  public void terran_test() {
    assertEquals("constructionSite", perciever.perceive().get(0).getName());
    when(bwapi.canBuildHere(any(Position.class), any(UnitType.class), any(Boolean.class)))
        .thenReturn(false);
    assertTrue(perciever.perceive().isEmpty());
  }

  @Test
  public void zerg_test() {
    when(player.getRace()).thenReturn(RaceTypes.Zerg);
    assertTrue(perciever.perceive().isEmpty());
    when(bwapi.hasCreep(any(Position.class))).thenReturn(true);
    assertEquals("constructionSite", perciever.perceive().get(0).getName());
    when(bwapi.canBuildHere(any(Position.class), any(UnitType.class), any(Boolean.class)))
        .thenReturn(false);
    assertTrue(perciever.perceive().isEmpty());
  }

  @Test
  public void protoss_test() {
    when(player.getRace()).thenReturn(RaceTypes.Protoss);
    assertEquals("constructionSite", perciever.perceive().get(0).getName());
    when(bwapi.canBuildHere(any(Position.class), eq(UnitType.UnitTypes.Protoss_Gateway),
        any(Boolean.class))).thenReturn(false);
    assertEquals("constructionSite", perciever.perceive().get(0).getName());
    when(bwapi.canBuildHere(any(Position.class), any(UnitType.class), any(Boolean.class)))
        .thenReturn(false);
    assertTrue(perciever.perceive().isEmpty());
  }

  @Test
  public void noRace_test() {
    when(player.getRace()).thenReturn(RaceTypes.None);
    when(unit.isExists()).thenReturn(false);
    when(unitType.getName()).thenReturn("not illegal");
    assertTrue(perciever.perceive().isEmpty());
  }

  @Test
  public void conditions_test() {
    assertTrue(perciever.getConditions().isEmpty());
  }

}
