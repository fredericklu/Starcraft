package eisbw;

import jnibwapi.JNIBWAPI;

public class UpdateThread extends Thread {

  private Game game;
  private JNIBWAPI bwapi;
  private boolean running;

  /**
   * Constructs a thread.
   * @param game - the game data class.
   * @param bwapi - the API.
   */
  public UpdateThread(Game game, JNIBWAPI bwapi) {
    this.bwapi = bwapi;
    this.game = game;
    running = true;
  }

  /**
   * Terminate the thread.
   */
  public void terminate() {
    running = false;
  }
  
  @Override
  public void run() {
    Thread.currentThread().setName("Update thread");
    while (running) {
      game.update(bwapi);
      try {
        Thread.sleep(5);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
