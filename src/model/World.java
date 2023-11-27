package model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Represents the main body of the model of the World.
 */
public interface World {
  BufferedImage getMap();

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
   * Gets the target.
   *
   * @return the target
   */
  Target getTarget();

  Player getCurrentPlayer();

  /**
   * Gets the list of players in the world.
   *
   * @return The list of players.
   */
  List<Player> getListOfPlayers();

  /**
   * Gets the list of rooms in the world.
   *
   * @return The list of rooms.
   */
  List<Room> getListOfRooms();

  int getMaxNumOfTurns();

  /**
   * Sets the maximum number of turns.
   *
   * @param maxNumOfTurns the maximum number of turns
   */
  void setMaxNumOfTurns(int maxNumOfTurns);

  /**
   * Gets number of turns played.
   *
   * @return the number of turns played
   */
  int getNumOfTurnsPlayed();


  /**
   * When the health of target is less or equal to zero, game is over.
   *
   * @return the true if game over
   */
  Boolean isGameOver();

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
   * Displays the map of the mansion.
   */
  void saveMansionMap();

  void roundOfTarget();

  void catWander();

  void roundOfPlayer();


  void moveToRoom(int x, int y);

  /**
   * Displays information about the target.
   */
  void displayTargetInformation();

  /**
   * Displays the list of rooms in the world.
   */
  void displayListOfRooms();

  /**
   * Displays information about the specified player.
   */
  void displayPlayerInformation();

  /**
   * Displays information about the target and players.
   */
  void displayRoomInformation();
}
