package eisbw.units;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.perceivers.IPerceiver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

public class StarcraftUnitTest {

  private StarcraftUnit unit;
  @Mock
  private IPerceiver perceiver;
  private List<Percept> percepts;
  private List<Parameter> params;

  /**
   * Initialize variables and mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    percepts = new LinkedList<>();
    percepts.add(new Percept("percept"));
    when(perceiver.perceive()).thenReturn(percepts);
    params = new LinkedList<>();
    params.add(new Identifier("param"));
    when(perceiver.getConditions()).thenReturn(params);
    List<IPerceiver> list = new LinkedList<>();
    list.add(perceiver);
    unit = new StarcraftUnit(list);
  }

  @Test
  public void test() {
    assertEquals(2,unit.perceive().size());
  }

}
