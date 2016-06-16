package eisbw.percepts.perceivers;

import eis.iilang.Percept;

import java.util.Map;
import java.util.Set;

/**
 * @author Danny & Harm - The interface of all perceivers.
 *
 */
@FunctionalInterface
public interface IPerceiver {
  /**
   * @param toReturn
   *          The percept and reference of which kind of percept it is.
   * @return A list of percepts.
   */
  Map<PerceptFilter, Set<Percept>> perceive(Map<PerceptFilter, Set<Percept>> toReturn);
}
