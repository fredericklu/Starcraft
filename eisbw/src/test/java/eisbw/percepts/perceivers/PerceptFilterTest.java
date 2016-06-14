package eisbw.percepts.perceivers;

import eis.eis2java.translation.Filter.Type;

import org.junit.Test;

public class PerceptFilterTest {
  private PerceptFilter filter;

  @Test
  public void test() {
    filter = new PerceptFilter("self", Type.ONCE);
    filter.equals(null);
    filter.equals(new Integer(10));
  }

}
