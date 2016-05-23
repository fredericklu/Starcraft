package eisbw.debugger.draw;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eis.eis2java.exception.NoTranslatorException;
import eis.eis2java.exception.TranslationException;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.TechType;
import jnibwapi.types.UnitType;
import jnibwapi.types.UpgradeType;
import jnibwapi.util.BWColor;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

public class DrawBuildingDetailsTest {

  private DrawBuildingDetails draw;
  private List<Unit> units;

  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Unit unit1;
  @Mock
  private Unit unit2;
  @Mock
  private TechType techType;
  @Mock
  private UpgradeType upgradeType;
  @Mock
  private UnitType unitType;

  @Test
  public void test() throws NoTranslatorException, TranslationException {
    MockitoAnnotations.initMocks(this);
    units = new LinkedList<>();
    units.add(unit1);
    units.add(unit2);

    when(bwapi.getMyUnits()).thenReturn(units);
    when(unit2.isUpgrading()).thenReturn(true);
    when(unit2.getRemainingResearchTime()).thenReturn(1);
    when(unit2.getTech()).thenReturn(techType);
    when(unit2.getUpgrade()).thenReturn(upgradeType);
    when(techType.getResearchTime()).thenReturn(10);
    when(upgradeType.getUpgradeTimeBase()).thenReturn(10);
    when(unit2.getType()).thenReturn(unitType);
    when(unit2.getPosition()).thenReturn(new Position(1, 1));

    draw = new DrawBuildingDetails(null);
    draw.toggle();
    draw.draw(bwapi);

    verify(bwapi, times(1)).drawBox(any(Position.class), any(Position.class), any(BWColor.class),
        eq(false), eq(false));

  }

}
