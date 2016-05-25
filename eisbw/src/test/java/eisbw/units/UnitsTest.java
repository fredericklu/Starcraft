package eisbw.units;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eisbw.StarcraftEnvironmentImpl;
import eisbw.percepts.perceivers.IPerceiver;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class UnitsTest {

  private Units units;

  @Mock
  private StarcraftEnvironmentImpl env;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private StarcraftUnitFactory factory;

  /**
   * Init mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);

    when(unit.getType()).thenReturn(unitType);
    when(unitType.getName()).thenReturn("name");
    when(unit.getID()).thenReturn(0);

    when(factory.create(any(Unit.class)))
        .thenReturn(new StarcraftUnit(new LinkedList<IPerceiver>()));

    units = new Units(env);
  }

  @Test
  public void addDeleteUnit_test() {
    units.addUnit(unit, factory);
    assertEquals("name0", units.getUnitNames().get(0));
    assertNotNull(units.getUnits().get("name0"));
    verify(factory, times(1)).create(any(Unit.class));
    verify(env, times(1)).addToEnvironment("name0", "name");
    units.deleteUnit("name0",0);
    verify(env, times(1)).deleteFromEnvironment("name0");
    units.addUnit(unit, factory);
    units.clean();
    verify(env, times(2)).deleteFromEnvironment("name0");
  }

  @Test
  public void getStarcraftUnits_test() {
    assertTrue(units.getStarcraftUnits() == units.starcraftUnits);
  }

}
