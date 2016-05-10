package eisbw.percepts.percievers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.GathererUnitPerceiver;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Position.PosType;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class GathererUnitPercieverTest {

  private GathererUnitPerceiver perciever;
  private LinkedList<Unit> toreturn;
  @Mock
  private Unit unit;
  @Mock
  private Unit otherunit;
  @Mock
  private UnitType unitType;
  @Mock
  private JNIBWAPI bwapi;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    toreturn = new LinkedList<>();
    toreturn.add(unit);

    when(bwapi.getMyUnits()).thenReturn(new LinkedList<Unit>());
    when(bwapi.getNeutralUnits()).thenReturn(new LinkedList<Unit>());
    when(unit.getType()).thenReturn(UnitType.UnitTypes.Resource_Vespene_Geyser);

    when(unit.getID()).thenReturn(1);
    when(unit.getResources()).thenReturn(2);
    when(unit.getResourceGroup()).thenReturn(3);
    when(unit.getPosition()).thenReturn(new Position(4, 5, PosType.BUILD));

    when(unitType.getName()).thenReturn("unitType");

    perciever = new GathererUnitPerceiver(bwapi, unit);
  }

  @Test
  public void gatheringGas_test() {
    when(unit.isGatheringGas()).thenReturn(true);
    assertEquals("gathering", perciever.perceive().get(0).getName());
    assertEquals("vespene", perciever.perceive().get(0).getParameters().get(0).toProlog());
  }

  @Test
  public void gatheringMinerals_test() {
    when(unit.isGatheringMinerals()).thenReturn(true);
    assertEquals("gathering", perciever.perceive().get(0).getName());
    assertEquals("mineral", perciever.perceive().get(0).getParameters().get(0).toProlog());
  }

  @Test
  public void carryingGas_test() {
    when(unit.isCarryingGas()).thenReturn(true);
    assertEquals("carrying", perciever.perceive().get(0).getName());
  }

  @Test
  public void carryingMinerals_test() {
    when(unit.isCarryingMinerals()).thenReturn(true);
    assertEquals("carrying", perciever.perceive().get(0).getName());
  }

  @Test
  public void geyserPosition_test() {
    when(bwapi.getNeutralUnits()).thenReturn(toreturn);
    assertEquals("vespeneGeyser", perciever.perceive().get(0).getName());
    assertEquals("1", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("2", perciever.perceive().get(0).getParameters().get(1).toProlog());
    assertEquals("3", perciever.perceive().get(0).getParameters().get(2).toProlog());
    assertEquals("2", perciever.perceive().get(0).getParameters().get(3).toProlog());
    assertEquals("4", perciever.perceive().get(0).getParameters().get(4).toProlog());
  }

  @Test
  public void geyserPositionEmpty_test() {
    when(unit.getType()).thenReturn(unitType);
    when(bwapi.getNeutralUnits()).thenReturn(toreturn);
    assertEquals(0, perciever.perceive().size());
  }

  @Test
  public void sameUnit_test() {
    when(bwapi.getMyUnits()).thenReturn(toreturn);
    assertEquals(0, perciever.perceive().size());
  }

  @Test
  public void otherUnitGas_test() {
    toreturn.add(otherunit);
    when(otherunit.getType()).thenReturn(unitType);
    when(otherunit.getID()).thenReturn(999);
    when(otherunit.isGatheringGas()).thenReturn(true);
    when(bwapi.getMyUnits()).thenReturn(toreturn);
    assertEquals("gathering", perciever.perceive().get(0).getName());
    assertEquals("unitType999", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("vespene", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void otherUnitMinerals_test() {
    toreturn.add(otherunit);
    when(otherunit.getType()).thenReturn(unitType);
    when(otherunit.getID()).thenReturn(999);
    when(otherunit.isGatheringMinerals()).thenReturn(true);
    when(bwapi.getMyUnits()).thenReturn(toreturn);
    assertEquals("gathering", perciever.perceive().get(0).getName());
    assertEquals("unitType999", perciever.perceive().get(0).getParameters().get(0).toProlog());
    assertEquals("mineral", perciever.perceive().get(0).getParameters().get(1).toProlog());
  }

  @Test
  public void otherUnitNone_test() {
    toreturn.add(otherunit);
    when(bwapi.getMyUnits()).thenReturn(toreturn);
    assertEquals(0, perciever.perceive().size());
  }

}
