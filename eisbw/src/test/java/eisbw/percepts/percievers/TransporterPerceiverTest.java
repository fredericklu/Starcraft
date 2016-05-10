package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.TransporterPerceiver;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class TransporterPerceiverTest {

  private TransporterPerceiver perciever;
  private LinkedList<Unit> toreturn;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    toreturn = new LinkedList<>();
    toreturn.add(unit);
    perciever = new TransporterPerceiver(null,unit);
    
    when(unit.getType()).thenReturn(unitType);
    when(unit.getID()).thenReturn(5);
    when(unitType.getSpaceProvided()).thenReturn(10);
    when(unitType.getName()).thenReturn("unitType");
    when(unit.getLoadedUnits()).thenReturn(toreturn);
  }
  
  @Test
  public void test() {
    assertEquals("spaceProvided", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("10", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }
  
  @Test
  public void isloaded_test() {
    assertEquals("unitLoaded", perciever.perceive().get(1).getName());
    assertEquals("5", perciever.perceive().get(1).getParameters().get(0).toProlog());
    assertEquals("unitType", perciever.perceive().get(1).getParameters().get(1).toProlog());
  }

}
