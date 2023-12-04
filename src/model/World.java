package model;

/**
 * Represents the main body of the model of the World.
 */
public interface World extends ReadOnlyWorld {

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
   *
   * @param maxNumOfTurns the maximum number of turns
   */
  void setMaxNumOfTurns(int maxNumOfTurns);


  /**
   * Plays the next turn, in each turn, target moves and then one player moves.
   */
  void playNextTurn();


  /**
   * Saves the map of the mansion.
   */
  void saveMansionMap();

  /**
   * Add turns after each turn.
   */
  void updateTurnsPlayed();

  /**
   * Play the round of target. For each turn, target moves first.
   */
  void roundOfTarget();

  /**
   * Pet wanders before each turn.
   */
  void petWander();

  /**
   * Play the round of player. For each turn, player moves after target moves.
   *
   * @return the display message after player's turn
   */
  String roundOfPlayer();


  /**
   * Updates the current player index after each turn.
   */
  void updatePlayerTurn();

  void movePetToRoom(int x, int y);

  /**
   * Moves the player to a specific room, found by the coordinates.
   *
   * @param x the x coordinate of the room
   * @param y the y coordinate of the room
   */
  void moveToRoom(int x, int y);

  Room findRoomByCoordinates(int x, int y);
}
