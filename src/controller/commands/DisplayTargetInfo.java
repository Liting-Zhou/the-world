package controller.commands;

import controller.Command;
import model.World;

/**
 * Represents the command to display target information.
 */
public final class DisplayTargetInfo implements Command {

  @Override
  public void execute(World w) {
    System.out.printf(w.displayTargetInformation());
  }
}
