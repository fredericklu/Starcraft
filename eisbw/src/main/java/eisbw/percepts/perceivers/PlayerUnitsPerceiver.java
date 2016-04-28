package eisbw.percepts.perceivers;

import eis.iilang.Percept;
import eisbw.BwapiUtility;
import eisbw.percepts.FriendlyPercept;
import eisbw.percepts.IsCloakedPercept;
import eisbw.percepts.IsMorphing;
import jnibwapi.JNIBWAPI;
import jnibwapi.Unit;

import java.util.ArrayList;
import java.util.List;

public class PlayerUnitsPerceiver extends Perceiver {
  private final BwapiUtility util;

  public PlayerUnitsPerceiver(JNIBWAPI api, BwapiUtility util) {
    super(api);
    this.util = util;
  }

  @Override
  public List<Percept> perceive() {
    List<Percept> percepts = new ArrayList<>();

    for (Unit unit : this.api.getMyUnits()) {
      percepts.add(new FriendlyPercept(util.getUnitName(unit), unit.getType().getName(), 
          unit.getID(), unit.getHitPoints(), unit.getShields(), unit.getPosition().getWX(), 
          unit.getPosition().getWY(), unit.getPosition().getBX(), unit.getPosition().getBY()));
      if (unit.isCloaked()) {
        percepts.add(new IsCloakedPercept(unit.getType().getName(), unit.getID()));
      }
      if (unit.isMorphing()) {
        percepts.add(new IsMorphing(unit.getBuildType().getName(), unit.getID()));
      }
    }

    return percepts;
  }
}
