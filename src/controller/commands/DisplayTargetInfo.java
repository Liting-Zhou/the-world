package controller.commands;

import controller.Command;
import model.World;

public class DisplayTargetInfo implements Command {

  @Override
  public void execute(World w) {
    w.displayTargetInformation();
  }
}
