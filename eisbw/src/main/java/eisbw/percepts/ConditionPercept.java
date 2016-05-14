package eisbw.percepts;

import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.util.List;

import eis.iilang.ParameterList;

public class ConditionPercept extends Percept {

  private static final long serialVersionUID = 1L;

  public ConditionPercept(List<Parameter> conditions) {
    super(Percepts.Condition, new ParameterList(conditions));
  }

}
