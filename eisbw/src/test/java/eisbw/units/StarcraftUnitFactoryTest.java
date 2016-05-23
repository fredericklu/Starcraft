package eisbw.units;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StarcraftUnitFactoryTest {

  private StarcraftUnitFactory factory;
  
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  
  @Test
  public void test() {
    MockitoAnnotations.initMocks(this);
    factory = new StarcraftUnitFactory(null);
    when(unit.getType()).thenReturn(unitType);
    assertEquals(2,factory.create(unit).perceivers.size());
    when(unitType.isBuilding()).thenReturn(true);
    when(unitType.isWorker()).thenReturn(true);
    assertEquals(4,factory.create(unit).perceivers.size());
  }

}
