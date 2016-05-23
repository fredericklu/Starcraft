package eisbw.debugger.draw;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import jnibwapi.ChokePoint;
import jnibwapi.JNIBWAPI;
import jnibwapi.Map;
import jnibwapi.Position;
import jnibwapi.Position.PosType;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

public class DrawChokepointsTest {

  private DrawChokepoints draw;
  private List<ChokePoint> locations;
  
  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Map map;
  @Mock
  private ChokePoint loc1;
  @Mock
  private ChokePoint loc2;
  
  @Test
  public void test() throws NoTranslatorException, TranslationException {
    MockitoAnnotations.initMocks(this);
    locations = new LinkedList<>();
    locations.add(loc1);
    locations.add(loc2);
    
    when(bwapi.getMap()).thenReturn(map);
    when(map.getChokePoints()).thenReturn(locations);
    when(loc1.getCenter()).thenReturn(new Position(1, 1, PosType.BUILD));
    when(loc2.getCenter()).thenReturn(new Position(1, 2, PosType.BUILD));
    
    draw = new DrawChokepoints(null);
    draw.toggle();
    draw.draw(bwapi);

    verify(bwapi,times(1)).drawText(eq(new Position(1,1,PosType.BUILD)),eq("(1,1)"),eq(false));
    verify(bwapi,times(1)).drawText(eq(new Position(1,2,PosType.BUILD)),eq("(1,2)"),eq(false));
  }

}
