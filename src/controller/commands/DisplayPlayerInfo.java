package controller.commands;

import controller.Command;
import model.MyWorld;

/**
 * Represents the command to display a specific player information.
 */
public final class DisplayPlayerInfo implements Command {

  @Override
  public void execute(MyWorld w) {
    w.displayPlayerInformation();
  }
}
