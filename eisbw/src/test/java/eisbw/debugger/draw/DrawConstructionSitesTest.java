package eisbw.debugger.draw;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.Game;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.util.BWColor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DrawConstructionSitesTest {

  private DrawConstructionSite draw;
  private List<Percept> locations;
  private LinkedList<Parameter> params;

  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Game game;
  @Mock
  private Percept loc1;
  @Mock
  private Percept loc2;
  @Mock
  private Logger logger;

  /**
   * Init mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    locations = new LinkedList<>();
    locations.add(loc1);
    locations.add(loc2);

    params = new LinkedList<>();
    params.add(new Numeral(1));
    params.add(new Numeral(2));

    when(loc2.getParameters()).thenReturn(params);
    when(loc1.getParameters()).thenReturn(params);

    draw = new DrawConstructionSite(game);

  }

  @Test
  public void test() {
    draw.toggle();
    draw.draw(bwapi);

    when(game.getConstructionSites()).thenReturn(locations);

    draw.draw(bwapi);
    verify(bwapi, times(2)).drawBox(any(Position.class), any(Position.class), any(BWColor.class),
        eq(false), eq(false));
  }

  @Test
  public void exception_test() {
    draw.toggle = true;

    params = new LinkedList<>();
    params.add(new Identifier("s"));
    params.add(new Numeral(2));

    when(loc1.getParameters()).thenReturn(params);
    when(game.getConstructionSites()).thenReturn(locations);

    draw.logger = logger;

    draw.draw(bwapi);
    verify(logger, times(1)).log(eq(Level.WARNING), eq("Cannot translate in draw function"),
        any(Exception.class));
  }

}
