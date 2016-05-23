package eisbw.debugger.draw;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import jnibwapi.BaseLocation;
import jnibwapi.JNIBWAPI;
import jnibwapi.Map;
import jnibwapi.util.BWColor;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

public class DrawBasesTest {

  private DrawBases draw;
  private List<BaseLocation> locations;
  
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Map map;
  @Mock
  private BaseLocation loc1;
  @Mock
  private BaseLocation loc2;
  
  @Test
  public void test() throws NoTranslatorException, TranslationException {
    MockitoAnnotations.initMocks(this);
    locations = new LinkedList<>();
    locations.add(loc1);
    locations.add(loc2);
    
    when(bwapi.getMap()).thenReturn(map);
    when(map.getBaseLocations()).thenReturn(locations);
    when(loc2.isStartLocation()).thenReturn(true);
    
    draw = new DrawBases(null);
    draw.toggle();
    draw.draw(bwapi);

    verify(bwapi,times(2)).drawCircle(null, 75, BWColor.Purple, false, false);
    verify(bwapi,times(1)).drawText(null, "Starting Location", false);

    draw.toggle();
    draw.draw(bwapi);

    verify(bwapi,times(2)).drawCircle(null, 75, BWColor.Purple, false, false);
    verify(bwapi,times(1)).drawText(null, "Starting Location", false);
  }

}
