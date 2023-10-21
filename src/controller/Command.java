package controller;

import java.io.IOException;
import model.MyWorld;

/**
 * Represents a command in the game "MyWorld".
 */
public interface Command {
  /**
   * Executes the command.
   *
   * @param w the MyWorld model to use.
   */
  void execute(MyWorld w) throws IOException;
}