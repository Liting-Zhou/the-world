package controller.commands;

import controller.Command;
import model.MyWorld;

/**
 * Represents the command to display target information.
 */
public final class DisplayTargetInfo implements Command {

  @Override
  public void execute(MyWorld w) {
    w.displayTargetInformation();
  }
}
