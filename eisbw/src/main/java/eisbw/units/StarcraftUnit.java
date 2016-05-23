package eisbw.units;

import eis.iilang.Parameter;
import eis.iilang.Percept;
import eisbw.percepts.ConditionPercept;
import eisbw.percepts.perceivers.IPerceiver;

import java.util.LinkedList;
import java.util.List;

public class StarcraftUnit {

  protected final List<IPerceiver> perceivers;

  /**
   * A starcraft unit with perceivers.
   * @param perceivers - list with perceivers to percept from.
   */
  public StarcraftUnit(List<IPerceiver> perceivers) {
    this.perceivers = perceivers;
  }

  /**
   * Percept this units' percepts.
   * @return - a list of percepts.
   */
  public List<Percept> perceive() {
    List<Percept> percepts = new LinkedList<>();
    List<Parameter> conditions = new LinkedList<>();
    for (IPerceiver perceiver : this.perceivers) {
      percepts.addAll(perceiver.perceive());
      conditions.addAll(perceiver.getConditions());
    }
    percepts.add(new ConditionPercept(conditions));
    return percepts;
  }
}
