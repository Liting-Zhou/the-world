package controller.commands;

import controller.Command;
import model.World;

public class DisplayRoomInfo implements Command {


  @Override
  public void execute(World w) {
    w.displayRoomInformation();
  }
}
