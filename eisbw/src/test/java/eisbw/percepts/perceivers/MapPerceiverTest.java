package eisbw.percepts.perceivers;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import eis.iilang.Percept;
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    Map<PerceptFilter, List<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
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

    Map<PerceptFilter, List<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
  }

  @Test
  public void chokepoint_test() {
    LinkedList<ChokePoint> locs = new LinkedList<ChokePoint>();
    locs.add(chokepoint);
    when(map.getChokePoints()).thenReturn(locs);
    when(chokepoint.getCenter()).thenReturn(new Position(5, 6, PosType.BUILD));

    Map<PerceptFilter, List<Percept>> ret = new HashMap<>();
    assertFalse(perciever.perceive(ret).isEmpty());
  }

}
