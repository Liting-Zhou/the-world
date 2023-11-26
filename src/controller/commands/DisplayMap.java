package controller.commands;

import controller.Command;
import model.MyWorld;
import model.World;

/**
 * Represents the command to display the map.
 */
public final class DisplayMap implements Command {
  /**
   * Executes the command.
   *
   * @param w the MyWorld model to use.
   */
  @Override
  public void execute(World w) {
    w.saveMansionMap();
  }
}
