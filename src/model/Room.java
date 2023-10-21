package model;

import java.util.List;

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
   * Gets the list of weapons present in the room.
   *
   * @return The list of weapons.
   */
  List<WeaponImp> getWeapons();

  /**
   * Returns neighbors of this room. Spaces that share a "wall" are neighbors.
   *
   * @param listOfRooms The list of all rooms
   * @return A list of Room representing neighbors.
   */
  List<Room> getNeighbors(List<Room> listOfRooms);

  /**
   * Saves the neighbors of this room.
   *
   * @param neighbors The list of neighbors
   */
  void setNeighbors(List<Room> neighbors);

  /**
   * Removes the given weapon from the room.
   */
  void removeWeapon(WeaponImp weapon);

  /**
   * Finds out if target is here.
   *
   * @param target The target to be decided if here
   * @return true if the target is here, false otherwise
   */
  boolean isTargetHere(Target target);

  /**
   * Displays target information.
   *
   * @param target The target to be displayed
   */
  void displayTarget(Target target);

  /**
   * Finds out if any player is here.
   *
   * @param players The list of players
   * @return true if any player is here, false otherwise
   */
  boolean isAnyPlayerHere(List<Player> players);

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
