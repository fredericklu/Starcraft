package eisbw.units;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.perceivers.IPerceiver;
import eisbw.percepts.perceivers.PerceptFilter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StarcraftUnitTest {

  private StarcraftUnit unit;
  @Mock
  private IPerceiver perceiver;
  private java.util.Map<PerceptFilter, Set<Percept>> percepts;
  private List<Parameter> params;

  /**
   * Initialize variables and mocks.
   */
  @SuppressWarnings("unchecked")
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    percepts = new HashMap<>();
    percepts.put(null,null);
    when(perceiver.perceive(any(java.util.Map.class))).thenReturn(percepts);
    params = new LinkedList<>();
    params.add(new Identifier("param"));
    when(perceiver.getConditions()).thenReturn(params);
    List<IPerceiver> list = new LinkedList<>();
    list.add(perceiver);
    unit = new StarcraftUnit(list,false);
  }

  @Test
  public void test() {
    assertEquals(1,unit.perceive().size());
    assertFalse(unit.isWorker());
  }

}
