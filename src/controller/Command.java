package controller;

import model.World;

/**
 * Represents a command in the game "MyWorld".
 */
public interface Command {
  /**
   * Executes the command.
   *
   * @param w the MyWorld model to use.
   */
  void execute(World w);
}