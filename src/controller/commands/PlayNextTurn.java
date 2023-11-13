package controller.commands;

import controller.Command;
import java.io.IOException;
import model.MyWorld;

/**
 * Represents the command to play the next round.
 */
public final class PlayNextTurn implements Command {

  private Appendable out;

  public PlayNextTurn(Appendable out) {
    this.out = out;
  }

  /**
   * Executes the command.
   *
   * @param w the MyWorld model to use.
   */
  @Override
  public void execute(MyWorld w) throws IOException {
    w.playNextTurn();
//    if (w.isGameOver()) {
//      out.append("Game over!");
//      out.append(String.format("The winner is %s", w.getWinner().getName()));
//    }
  }
}
