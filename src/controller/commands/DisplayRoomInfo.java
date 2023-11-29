package controller.commands;

import controller.Command;
import model.World;

/**
 * Represents the command to display a specific room information.
 */
public final class DisplayRoomInfo implements Command {


  @Override
  public void execute(World w) {
    w.getRoomAndDisplay();
  }
}
