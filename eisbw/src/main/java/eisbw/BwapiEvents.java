package eisbw;

import jnibwapi.BWAPIEventListener;
import jnibwapi.Position;

public class BwapiEvents implements BWAPIEventListener {

  @Override
  public void connected() {
    // Method stub

  }

  @Override
  public void matchStart() {
    // Method stub

  }

  @Override
  public void matchFrame() {
    // Method stub

  }

  @Override
  public void matchEnd(boolean winner) {
    // Method stub

  }

  @Override
  public void keyPressed(int keyCode) {
    // Method stub

  }

  @Override
  public void sendText(String text) {
    // Method stub

  }

  @Override
  public void receiveText(String text) {
    // Method stub

  }

  @Override
  public void playerLeft(int playerId) {
    // Method stub

  }

  @Override
  public void nukeDetect(Position pos) {
    // Method stub

  }

  @Override
  public void nukeDetect() {
    // Method stub

  }

  @Override
  public void unitDiscover(int unitId) {
    // Method stub

  }

  @Override
  public void unitEvade(int unitId) {
    // Method stub

  }

  @Override
  public void unitShow(int unitId) {
    // Method stub

  }

  @Override
  public void unitHide(int unitId) {
    // Method stub

  }

  @Override
  public void unitCreate(int unitId) {
    // Method stub

  }

  @Override
  public void unitDestroy(int unitId) {
    // Method stub

  }

  @Override
  public void unitMorph(int unitId) {
    // Method stub

  }

  @Override
  public void unitRenegade(int unitId) {
    // Method stub

  }

  @Override
  public void saveGame(String gameName) {
    // Method stub

  }

  @Override
  public void unitComplete(int unitId) {
    // Method stub

  }

  @Override
  public void playerDropped(int playerId) {
    // Method stub

  }

}
