package controller;

import java.io.IOException;
import model.World;

public interface Command {
  /**
   * Executes the command.
   *
   * @param w the World model to use.
   */
  void execute(World w) throws IOException;
}