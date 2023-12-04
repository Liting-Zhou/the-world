package model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This interface represents a read-only world for view to directly interact with.
 */
public interface ReadOnlyWorld {

  /**
   * Gets the map of the mansion.
   *
   * @return the map
   */
  BufferedImage getMap();

  /**
   * Gets the target.
   *
   * @return the target
   */
  Target getTarget();

  Pet getPet();

  /**
   * Gets the current player.
   *
   * @return the current player
   */
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

  /**
   * Gets the maximum number of turns can play.
   *
   * @return the maximum number of turns
   */
  int getMaxNumOfTurns();


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

  void getPlayerAndDisplay();

  void getRoomAndDisplay();

  /**
   * Displays information about the target.
   *
   * @return the display message of target information
   */
  String displayTargetInformation();

  /**
   * Displays the list of rooms in the world.
   */
  void displayListOfRooms();

  /**
   * Displays information about the specified player.
   *
   * @param player the player to be displayed
   * @return the display message of player information
   */
  String displayPlayerInformation(Player player);

  /**
   * Displays information about the target and players.
   *
   * @param room the room to be displayed
   * @return the display message of room information
   */
  String displayRoomInformation(Room room);
}
