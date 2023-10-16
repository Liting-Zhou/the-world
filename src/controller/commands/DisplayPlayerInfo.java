package controller.commands;

import controller.Command;
import model.World;

public final class DisplayPlayerInfo implements Command {

  @Override
  public void execute(World w) {
    w.displayPlayerInformation();
  }
}
