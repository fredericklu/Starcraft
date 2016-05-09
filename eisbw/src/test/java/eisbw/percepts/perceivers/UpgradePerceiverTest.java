package eisbw.percepts.perceivers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eisbw.percepts.perceivers.UpgradePerceiver;
import jnibwapi.Unit;
import jnibwapi.types.UpgradeType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UpgradePerceiverTest {

  private UpgradePerceiver perciever;
  @Mock
  private Unit unit;
  @Mock
  private UpgradeType upgradeType;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    perciever = new UpgradePerceiver(null,unit);
    
    when(unit.getUpgrade()).thenReturn(upgradeType);
    when(upgradeType.getName()).thenReturn("upgradeType");
  }
  
  @Test
  public void test() {
    when(unit.isUpgrading()).thenReturn(true);
    assertEquals("upgrade", perciever.perceive().get(0).getName());
    assertEquals("upgradeType", perciever.perceive().get(0).getParameters().get(0).toProlog());
  }
  
  @Test
  public void notUpgrading_test() {
    when(unit.isUpgrading()).thenReturn(false);
    assertEquals(0, perciever.perceive().size());
  }

}
