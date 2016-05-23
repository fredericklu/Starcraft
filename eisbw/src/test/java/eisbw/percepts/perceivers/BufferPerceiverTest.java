package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

public class BufferPerceiverTest {

  private BufferPerceiver perciever;
  @Mock
  private Unit unit;
  @Mock
  private Unit unit2;
  @Mock
  private Unit unit3;
  @Mock
  private Unit unit4;
  @Mock
  private UnitType unitType;
  @Mock
  private UnitType unitType2;
  @Mock
  private UnitType unitType3;
  @Mock
  private JNIBWAPI api;
  @Mock
  private Player self;
  private List<Unit> units;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);

    when(unit.getType()).thenReturn(unitType);
    when(unit.getResourceGroup()).thenReturn(1);
    when(unit.getResources()).thenReturn(2);
    when(unit.getPosition()).thenReturn(new Position(1,2,PosType.BUILD));
    

    when(unit3.getResourceGroup()).thenReturn(1);
    when(unit3.getResources()).thenReturn(2);
    when(unit3.getPosition()).thenReturn(new Position(1,2,PosType.BUILD));
    
    units = new LinkedList<>();
    units.add(unit);
    units.add(unit4);
    units.add(unit3);
    units.add(unit2);

    when(api.getMyUnits()).thenReturn(units);
    when(api.getNeutralUnits()).thenReturn(units);
    
    when(unit2.isVisible()).thenReturn(false);
    when(unit3.isVisible()).thenReturn(true);
    when(unit4.isVisible()).thenReturn(true);
    when(unit3.getType()).thenReturn(unitType2);
    when(unit2.getType()).thenReturn(unitType2);
    when(unit4.getType()).thenReturn(unitType3);
    when(unit.isVisible()).thenReturn(true);
    when(unitType.getName()).thenReturn("Resource Mineral Field");
    when(unitType2.getName()).thenReturn("Resource Vespene Geyser");
    when(unitType3.getName()).thenReturn("No Resource");
    
    perciever = new BufferPerceiver(api);
  }

  @Test
  public void size_test() {
    assertEquals(5, perciever.perceive().size());
  }
  
  @Test
  public void conditions_test() {
    assertEquals(0, perciever.getConditions().size());
  }

}
