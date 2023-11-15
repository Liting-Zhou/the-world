package controller.commands;

import controller.Command;
import model.MyWorld;

/**
 * Represents the command to play the next round.
 */
public final class PlayNextTurn implements Command {

  @Override
  public void execute(MyWorld w) {
    w.playNextTurn();
  }
}
