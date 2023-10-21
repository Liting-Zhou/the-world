package model;

public interface World {
  /**
   * Adds human-controlled player to the game.
   *
   * @param playerName         The name of the player to add.
   * @param startingRoomNumber The starting room number of the player.
   */
  void addHumanPlayer(String playerName, int startingRoomNumber);

  /**
   * Adds computer-controlled player to the game.
   *
   * @param playerName         The name of the player to add.
   * @param startingRoomNumber The starting room number of the player.
   */
  void addComputerPlayer(String playerName, int startingRoomNumber);

  /**
   * Gets the target.
   *
   * @return the target
   */
  Target getTarget();


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
   * Plays the next round, in each round, target moves and then one player moves.
   */
  void playNextRound();

  /**
   * Plays target move and update target information.
   */
  void roundOfTargetCharacter();

  /**
   * Updates the target after actions.
   *
   * @param target the target
   */
  void updateTarget(Target target);

  /**
   * model.Player's turn. model.Player can choose three actions:
   * 1.move to a neighboring space.
   * 2.pick up an item.
   * 3.look around by displaying information about where a specific player is in the world
   * including what spaces that can be seen from where they are.
   */
  void roundOfPlayers();

  /**
   * Displays the map of the mansion.
   */
  void displayMap();

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
