package eisbw.units;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import jnibwapi.JNIBWAPI;
import jnibwapi.Player;
import jnibwapi.Unit;
import jnibwapi.types.RaceType.RaceTypes;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ConditionHandlerTest {

  private ConditionHandler handler;

  @Mock
  private Unit unit;
  @Mock
  private JNIBWAPI api;
  @Mock
  private UnitType unitType;
  @Mock
  private Player self;

  /**
   * Init mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);

    when(self.getRace()).thenReturn(RaceTypes.None);

    when(api.getSelf()).thenReturn(self);
    when(unit.getType()).thenReturn(unitType);
    when(unitType.getName()).thenReturn("name");
    when(unit.getID()).thenReturn(0);

    handler = new ConditionHandler(api, unit);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void idle_test() {
    when(unit.isIdle()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    assertEquals("idle", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void completed_test() {
    when(unit.isCompleted()).thenReturn(false);
    assertEquals("beingConstructed", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void cloaked_test() {
    when(unit.isCloaked()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    assertEquals("cloaked", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void moving_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isMoving()).thenReturn(true);
    assertEquals("moving", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void following_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isFollowing()).thenReturn(true);
    assertEquals("following", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void loaded_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isLoaded()).thenReturn(true);
    assertEquals("loaded", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void stimmed_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(self.getRace()).thenReturn(RaceTypes.Terran);
    when(unit.isStimmed()).thenReturn(true);
    assertEquals("stimmed", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void sieged_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(self.getRace()).thenReturn(RaceTypes.Terran);
    when(unit.isSieged()).thenReturn(true);
    assertEquals("sieged", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void blinded_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isBlind()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    assertEquals("blinded", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void lockedDown_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isLockedDown()).thenReturn(true);
    assertEquals("lockDowned", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void irradiated_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isIrradiated()).thenReturn(true);
    assertEquals("irradiated", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void underStorm_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isUnderStorm()).thenReturn(true);
    assertEquals("underStorm", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void stasised_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isStasised()).thenReturn(true);
    assertEquals("stasised", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void maelstrommed_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isMaelstrommed()).thenReturn(true);
    assertEquals("maelstrommed", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void disruptionWebbed_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isUnderDisruptionWeb()).thenReturn(true);
    assertEquals("disruptionWebbed", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void ensnared_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isEnsnared()).thenReturn(true);
    assertEquals("ensnared", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void parasited_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isParasited()).thenReturn(true);
    assertEquals("parasited", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void plagued_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isPlagued()).thenReturn(true);
    assertEquals("plagued", handler.getConditions().get(0).toProlog());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void darkSwarmed_test() {
    when(unitType.isCanMove()).thenReturn(true);
    when(unit.isCompleted()).thenReturn(true);
    when(unit.isUnderDarkSwarm()).thenReturn(true);
    assertEquals("darkSwarmed", handler.getConditions().get(0).toProlog());
  }

  @Test
  public void conditions_gas_test() {
    when(unit.isCarryingGas()).thenReturn(true);
    assertEquals(1, handler.getConditions().size());
  }

  @Test
  public void conditions_minerals_test() {
    when(unit.isCarryingMinerals()).thenReturn(true);
    assertEquals(1, handler.getConditions().size());
  }

  @Test
  public void conditions_constructing_test() {
    when(unit.isConstructing()).thenReturn(true);
    assertEquals(1, handler.getConditions().size());
  }

  @Test
  public void conditions_building_test() {
    when(unit.isCompleted()).thenReturn(true);
    when(unitType.isBuilding()).thenReturn(true);

    when(unit.isLifted()).thenReturn(true);
    when(unit.getAddon()).thenReturn(unit);

    assertEquals(0, handler.getConditions().size());

    when(self.getRace()).thenReturn(RaceTypes.Terran);

    assertEquals(2, handler.getConditions().size());

    when(unit.isLifted()).thenReturn(false);
    when(unit.getAddon()).thenReturn(null);

    assertEquals(0, handler.getConditions().size());
  }

}
