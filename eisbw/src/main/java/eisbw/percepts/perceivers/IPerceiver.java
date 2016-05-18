package eisbw.percepts.perceivers;

import eis.iilang.Parameter;
import eis.iilang.Percept;

import java.util.List;

public interface IPerceiver {
  List<Percept> perceive();

  List<Parameter> getConditions();
}
