package controller.commands;

import controller.Command;
import model.MyWorld;

public final class DisplayRoomInfo implements Command {


  @Override
  public void execute(MyWorld w) {
    w.displayRoomInformation();
  }
}
