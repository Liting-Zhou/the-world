package controller;

import model.World;

public interface Command {
  /**
   * Executes the command.
   *
   * @param w the World model to use.
   */
  void execute(World w);
}