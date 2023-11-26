package controller.commands;

import controller.Command;
import model.World;

/**
 * Represents the command to display a specific player information.
 */
public final class DisplayPlayerInfo implements Command {

  @Override
  public void execute(World w) {
    w.displayPlayerInformation();
  }
}
