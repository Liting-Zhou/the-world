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
   * Finds out if target is here, if yes, display target information.
   */
  void displayTarget(Target target);

  /**
   * Finds out if any player is here, if yes, display player information.
   */
  void displayPlayers(List<Player> players);

  /**
   * Displays the weapon information in the room.
   */
  void displayWeapons();

  /**
   * Displays the neighbors of the room.
   */
  void displayNeighbors();
}
