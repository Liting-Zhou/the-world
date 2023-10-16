package controller.commands;

import controller.Command;
import model.World;

public class DisplayPlayerInfo implements Command {

  @Override
  public void execute(World w) {
    w.displayPlayerInformation();
  }
}
