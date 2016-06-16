package eisbw;

import jnibwapi.Position;

import org.junit.Before;
import org.junit.Test;

public class BwapiEventsTest {

  BwapiEvents events;

  @Before
  public void start() {
    events = new BwapiEvents();
  }

  @Test
  public void eventTests() {
    events.matchStart();
    events.connected();
    events.nukeDetect();
    events.keyPressed(0);
    events.matchFrame();
    events.nukeDetect(new Position(0, 0));
    events.playerDropped(0);
    events.playerLeft(0);
    events.receiveText("0");
    events.saveGame("0");
    events.sendText("0");
    events.unitComplete(0);
    events.unitCreate(0);
    events.unitDestroy(0);
    events.unitDiscover(0);
    events.unitEvade(0);
    events.unitHide(0);
    events.unitMorph(0);
    events.unitRenegade(0);
    events.unitShow(0);
    events.matchEnd(true);
  }
}
