package eisbw.percepts.perceivers;

import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IPerceiver {
  Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter,Set<Percept>> toReturn);

  List<Parameter> getConditions();
}
