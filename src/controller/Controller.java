package controller;


import model.World;

/**
 * An interface for the game controller, which handles interactions between the user and the game model.
 */
public interface Controller {
  /**
   * Starts the game.
   *
   * @param w The game world to be controlled.
   * @param maxNumOfTurns The maximum number of turns for the game.
   * @throws IllegalArgumentException for invalid arguments.
   */
  void playGame(World w, int maxNumOfTurns)throws IllegalArgumentException;

}
