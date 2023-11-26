package controller.commands;

import controller.Command;
import model.MyWorld;
import model.World;

/**
 * Represents the command to play the next round.
 */
public final class PlayNextTurn implements Command {

  @Override
  public void execute(World w) {
    w.playNextTurn();
  }
}
