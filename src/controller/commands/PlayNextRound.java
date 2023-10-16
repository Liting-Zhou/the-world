package controller.commands;

import controller.Command;
import java.io.IOException;
import model.World;

public class PlayNextRound implements Command {

  private Appendable out;

  public PlayNextRound(Appendable out) {
    this.out = out;
  }

  /**
   * Executes the command.
   *
   * @param w the World model to use.
   */
  @Override
  public void execute(World w) throws IOException {
    w.playNextRound();
    if (w.ifGameOver()) {
      out.append("Game over!");
      out.append("The winner is " + w.getWinner().getName());
    }
  }
}
