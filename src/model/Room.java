package model;

import java.util.List;

/**
 * Represents information about a room in Doctor Lucky's Mansion, including its position, name,
 * weapons present, and neighbors.
 */
public interface Room {
  /**
   * Gets the x coordinate of upper left corner.
   *
   * @return x1
   */
  int getX1();

  /**
   * Gets the y coordinate of upper left corner.
   *
   * @return y1
   */
  int getY1();

  /**
   * Gets the x coordinate of bottom right corner.
   *
   * @return x2
   */
  int getX2();

  /**
   * Gets the y coordinate of bottom right corner.
   *
   * @return y2
   */
  int getY2();

  /**
   * Gets the name of the room.
   *
   * @return The name of the room
   */
  String getRoomName();

  /**
   * Gets the unique room number.
   *
   * @return The room number.
   */
  int getRoomNumber();

  /**
   * Decide if the given room is neighbor of this room.
   *
   * @param otherRoom The room to be decided if neighbors
   * @return true if the given room is neighbor of this room, false otherwise
   */
  boolean isNeighbor(Room otherRoom);

  /**
   * Gets the list of weapons present in the room.
   *
   * @return The list of weapons.
   */
  List<WeaponImp> getWeapons();

  /**
   * Finds the neighbors of this room. Spaces that share a "wall" are neighbors.
   *
   * @param listOfRooms The list of rooms
   * @return A list of Room representing neighbors.
   */
  List<Room> findNeighbors(List<Room> listOfRooms);

  /**
   * Gets the neighbors of this room.
   *
   * @return The list of neighbors
   */
  List<Room> getNeighbors();

  /**
   * Saves the neighbors of this room.
   *
   * @param neighbors The list of neighbors
   */
  void setNeighbors(List<Room> neighbors);

  /**
   * Sets the target flag, indicating if the target is in this room.
   *
   * @param targetFlag The target flag
   */
  void setTargetFlag(boolean targetFlag);

  /**
   * Sets the pet flag, indicating if the pet is in this room.
   *
   * @param petFlag The pet flag
   */
  void setPetFlag(boolean petFlag);

  /**
   * Sets the player flag, indicating if any player is in this room.
   *
   * @param playerFlag The player flag
   */
  void setPlayerFlag(boolean playerFlag);

  /**
   * Removes the given weapon from the room.
   *
   * @param weapon The weapon to be removed
   */
  void removeWeapon(WeaponImp weapon);

  /**
   * Finds out if target is here.
   *
   * @return true if the target is here, false otherwise
   */
  boolean isTargetHere();

  /**
   * Finds out if pet is here.
   *
   * @return true if the pet is here, false otherwise
   */
  boolean isPetHere();

  /**
   * Gets the players in the room.
   *
   * @return The list of players in the room
   */
  List<Player> getPlayersInTheRoom();


  /**
   * Adds the given player to the room.
   *
   * @param player The player to be added
   */
  void addPlayer(Player player);


  /**
   * Removes the given player from the room.
   *
   * @param player The player to be removed
   */
  void removePlayer(Player player);

  /**
   * Displays pet information.
   *
   * @return the display message of pet information
   **/
  String displayPet();

  /**
   * Displays target information.
   *
   * @return the display message of target information
   **/
  String displayTarget();

  /**
   * Finds out if any player is here.
   *
   * @return true if any player is here, false otherwise
   */
  boolean isAnyPlayerHere();

  /**
   * Finds out if any player other than the given player is here.
   *
   * @param player The player
   * @return true if any player other than the given player is here, false otherwise
   */
  boolean isAnyOtherPlayerHere(Player player);

  /**
   * Displays information of all players in the room.
   *
   * @return the display message of all players in the room
   **/
  String displayPlayers();

  /**
   * Displays the weapon information in the room.
   *
   * @return the display message of weapon information in the room
   */
  String displayWeapons();

  /**
   * Displays the room number and name of neighbors.
   */
  void displayNeighborsSimple();

  /**
   * Displays the neighbors of the room with all information.
   *
   * @return the display message of all information about the neighbors of the room
   */
  String displayNeighborsAllInfo();
}
