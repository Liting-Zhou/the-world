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
   * Returns neighbors of this room. Spaces that share a "wall" are neighbors.
   *
   * @return A list of Room representing neighbors.
   */
  List<Room> getNeighbors();

  /**
   * Saves the neighbors of this room.
   *
   * @param neighbors The list of neighbors
   */
  void setNeighbors(List<Room> neighbors);

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
   * Displays pet information.
   **/
  void displayPet();

  /**
   * Displays target information.
   **/
  void displayTarget();

  /**
   * Finds out if any player is here.
   *
   * @param players The list of players
   * @return true if any player is here, false otherwise
   */
  boolean isAnyPlayerHere(List<Player> players);
  /**
   * Finds out if any player other than the given player is here.
   *
   * @param player The player
   */
  public boolean isAnyOtherPlayerHere(Player player);
  /**
   * Displays information of all players in the room.
   *
   * @param players The list of players
   */
  void displayPlayers(List<Player> players);

  /**
   * Displays the weapon information in the room.
   */
  void displayWeapons();

  /**
   * Displays the room number and name of neighbors.
   */
  void displayNeighborsSimple();

  /**
   * Displays the neighbors of the room with all information.
   */
  void displayNeighborsAllInfo();
}
