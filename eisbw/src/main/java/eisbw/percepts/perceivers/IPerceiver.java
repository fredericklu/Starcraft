package eisbw.percepts.perceivers;

import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.util.List;
import java.util.Map;

public interface IPerceiver {
  Map<PerceptFilter,List<Percept>> perceive(Map<PerceptFilter,List<Percept>> toReturn);

  List<Parameter> getConditions();
}
