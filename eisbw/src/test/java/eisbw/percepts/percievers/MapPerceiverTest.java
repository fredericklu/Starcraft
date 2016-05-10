package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.MapPerceiver;
import jnibwapi.BaseLocation;
import jnibwapi.ChokePoint;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Region;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class MapPerceiverTest {

  private MapPerceiver perciever;
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private jnibwapi.Map map;
  @Mock
  private Position mapsize;
  @Mock
  private BaseLocation baselocation;
  @Mock
  private Region region;
  @Mock
  private ChokePoint chokepoint;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);

    when(bwapi.getMap()).thenReturn(map);
    when(map.getSize()).thenReturn(mapsize);
    when(mapsize.getBX()).thenReturn(10);
    when(mapsize.getBY()).thenReturn(11);

    perciever = new MapPerceiver(bwapi);
  }

  @Test
  public void mapsize_test() {
    assertEquals("map", perciever.perceive().get(0).getName());
    assertEquals("10", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("11", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void baseLocation_test() {
    LinkedList<BaseLocation> locs = new LinkedList<BaseLocation>();
    locs.add(baselocation);
    when(map.getBaseLocations()).thenReturn(locs);
    when(baselocation.getPosition()).thenReturn(new Position(3, 4, PosType.BUILD));
    when(baselocation.isStartLocation()).thenReturn(true);
    when(baselocation.getRegion()).thenReturn(region);
    when(region.getID()).thenReturn(5);

    assertEquals("base", perciever.perceive().get(1).getName());
    assertEquals("3", perciever.perceive().get(1).getParameters().get(0).toProlog());
    assertEquals("4", perciever.perceive().get(1).getParameters().get(1).toProlog());
    assertEquals("true", perciever.perceive().get(1).getParameters().get(2).toProlog());
    assertEquals("5", perciever.perceive().get(1).getParameters().get(3).toProlog());
  }

  @Test
  public void chokepoint_test() {
    LinkedList<ChokePoint> locs = new LinkedList<ChokePoint>();
    locs.add(chokepoint);
    when(map.getChokePoints()).thenReturn(locs);
    when(chokepoint.getCenter()).thenReturn(new Position(5, 6, PosType.BUILD));

    assertEquals("chokepoint", perciever.perceive().get(1).getName());
    assertEquals("5", perciever.perceive().get(1).getParameters().get(0).toProlog());
    assertEquals("6", perciever.perceive().get(1).getParameters().get(1).toProlog());
  }

}
