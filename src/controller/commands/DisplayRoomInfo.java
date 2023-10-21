package controller.commands;

import controller.Command;
import model.MyWorld;

/**
 * Represents the command to display a specific room information.
 */
public final class DisplayRoomInfo implements Command {


  @Override
  public void execute(MyWorld w) {
    w.displayRoomInformation();
  }
}
