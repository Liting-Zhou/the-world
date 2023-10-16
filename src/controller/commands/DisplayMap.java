package controller.commands;

import controller.Command;
import model.World;

public class DisplayMap implements Command {
  /**
   * Executes the command.
   *
   * @param w the World model to use.
   */
  @Override
  public void execute(World w) {
    w.displayMap();
  }
}
