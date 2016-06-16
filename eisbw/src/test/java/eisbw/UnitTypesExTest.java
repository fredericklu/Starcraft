package eisbw;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UnitTypesExTest {

  @Mock
  private UnitType unitType;
  
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void test() {
    when(unitType.getName()).thenReturn("none");
    assertFalse(UnitTypesEx.isMineralField(unitType));
    assertFalse(UnitTypesEx.isResourceType(unitType));
    assertFalse(UnitTypesEx.isVespeneGeyser(unitType));
  }
  
  @Test
  public void mineralfield_test() {
    when(unitType.getName()).thenReturn("Resource Mineral Field");
    assertTrue(UnitTypesEx.isMineralField(unitType));
    when(unitType.getName()).thenReturn("Resource Mineral Field Type 2");
    assertTrue(UnitTypesEx.isMineralField(unitType));
    when(unitType.getName()).thenReturn("Resource Mineral Field Type 3");
    assertTrue(UnitTypesEx.isMineralField(unitType));
  }
  
  @Test
  public void resourceType_test() {
    when(unitType.isRefinery()).thenReturn(true);
    assertTrue(UnitTypesEx.isResourceType(unitType));
    when(unitType.isRefinery()).thenReturn(false);
    when(unitType.getName()).thenReturn("Resource Mineral Field");
    assertTrue(UnitTypesEx.isResourceType(unitType));
    when(unitType.getName()).thenReturn("Resource Vespene Geyser");
    assertTrue(UnitTypesEx.isResourceType(unitType));
  }

}
