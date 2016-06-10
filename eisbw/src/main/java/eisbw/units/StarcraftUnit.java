package eisbw.units;

import eis.eis2java.translation.Filter;
import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.ConditionPercept;
import eisbw.percepts.Percepts;
import eisbw.percepts.perceivers.IPerceiver;
import eisbw.percepts.perceivers.PerceptFilter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StarcraftUnit {

  protected final List<IPerceiver> perceivers;
  private boolean worker;

  /**
   * A starcraft unit with perceivers.
   * @param perceivers - list with perceivers to percept from.
   * @param worker - true iff the unit is a worker
   */
  public StarcraftUnit(List<IPerceiver> perceivers, boolean worker) {
    this.perceivers = perceivers;
    this.worker = worker;
  }

  /**
   * Percept this units' percepts.
   * @return - a list of percepts.
   */
  public Map<PerceptFilter, Set<Percept>> perceive() {
    Map<PerceptFilter, Set<Percept>> toReturn = new HashMap<>();
    Set<Percept> percepts = new HashSet<>();
    List<Parameter> conditions = new LinkedList<>();
    for (IPerceiver perceiver : this.perceivers) {
      perceiver.perceive(toReturn);
      conditions.addAll(perceiver.getConditions());
    }
    percepts.add(new ConditionPercept(conditions));
    toReturn.put(new PerceptFilter(Percepts.CONDITION, Filter.Type.ON_CHANGE), percepts);
    return toReturn;
  }

  public boolean isWorker() {
    return worker;
  }
}
