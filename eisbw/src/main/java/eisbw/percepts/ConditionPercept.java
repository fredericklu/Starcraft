package eisbw.percepts;

import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import eis.iilang.Percept;

import java.util.List;

public class ConditionPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public ConditionPercept(List<Parameter> conditions) {
    super(Percepts.Condition, new ParameterList(conditions));
  }

}
