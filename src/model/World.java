package model;

/**
 * Represents the main body of the model of the World.
 */
public interface World {
  /**
   * Adds human-controlled player to the game.
   *
   * @param playerName         The name of the player to add.
   * @param startingRoomNumber The starting room number of the player.
   * @param maxNumOfWeapons    The maximum number of weapons this player can carry.
   */
  void addHumanPlayer(String playerName, int startingRoomNumber, int maxNumOfWeapons);

  /**
   * Adds computer-controlled player to the game.
   *
   * @param playerName         The name of the player to add.
   * @param startingRoomNumber The starting room number of the player.
   * @param maxNumOfWeapons    The maximum number of weapons this player can carry.
   */
  void addComputerPlayer(String playerName, int startingRoomNumber, int maxNumOfWeapons);

  /**
   * Sets the maximum number of turns.
   */
  void setMaxNumOfTurns(int maxNumOfTurns);

  /**
   * Gets the target.
   *
   * @return the target
   */
  Target getTarget();

  /**
   * Gets number of turns played.
   *
   * @return the number of turns played
   */
  int getNumOfTurnsPlayed();

  /**
   * Gets the model.Mansion.
   *
   * @return the mansion
   */
  Mansion getMansion();

  /**
   * When the health of target is less or equal to zero, game is over.
   *
   * @return the true if game over
   */
  Boolean ifGameOver();

  /**
   * Gets the winner when game is over.
   *
   * @return the winner
   */
  Player getWinner();

  /**
   * Plays the next turn, in each turn, target moves and then one player moves.
   */
  void playNextTurn();


  /**
   * Updates the target after actions.
   *
   * @param target the target
   */
  void updateTarget(Target target);


  /**
   * Displays the map of the mansion.
   */
  void SaveMansionMap();

  /**
   * Displays information about the target.
   */
  void displayTargetInformation();

  /**
   * Displays information about the specified player.
   */
  void displayPlayerInformation();

  /**
   * Displays information about the target and players.
   */
  void displayRoomInformation();
}
