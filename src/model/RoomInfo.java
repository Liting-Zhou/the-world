package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents information about a room in Doctor Lucky's model.Mansion,
 * including its position, name, weapons present, and neighbors.
 */
public final class RoomInfo implements Room {

  private final int roomNumber;
  private final int x1; //left up corner
  private final int y1; //left up corner
  private final int x2; //right bottom corner
  private final int y2; //right bottom corner
  private final String roomName;
  private List<WeaponImp> weapons;
  private List<Room> neighbors = new ArrayList<>();

  /**
   * Constructs a new model.RoomInfo object.
   *
   * @param roomNumber The unique room number.
   * @param x1         The x-coordinate of the left-upper corner.
   * @param y1         The y-coordinate of the left-upper corner.
   * @param x2         The x-coordinate of the right-bottom corner.
   * @param y2         The y-coordinate of the right-bottom corner.
   * @param roomName   The name of the room.
   * @param weapons    The list of weapons present in the room.
   */
  public RoomInfo(int roomNumber, int x1, int y1, int x2, int y2, String roomName,
                  List<WeaponImp> weapons) {
    this.roomNumber = roomNumber;
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.roomName = roomName;
    this.weapons = weapons;
  }

  /**
   * Gets the x coordinate of upper left corner.
   *
   * @return x1
   */
  @Override
  public int getX1() {
    return x1;
  }

  /**
   * Gets the y coordinate of upper left corner.
   *
   * @return y1
   */
  @Override
  public int getY1() {
    return y1;
  }

  /**
   * Gets the x coordinate of bottom right corner.
   *
   * @return x2
   */
  @Override
  public int getX2() {
    return x2;
  }

  /**
   * Gets the y coordinate of bottom right corner.
   *
   * @return y2
   */
  @Override
  public int getY2() {
    return y2;
  }

  /**
   * Gets the name of the room.
   *
   * @return The name of the room
   */
  @Override
  public String getRoomName() {
    return roomName;
  }

  /**
   * Gets the unique room number.
   *
   * @return The room number.
   */
  @Override
  public int getRoomNumber() {
    return roomNumber;
  }

  /**
   * Gets the list of weapons present in the room.
   *
   * @return The list of weapons.
   */
  @Override
  public List<WeaponImp> getWeapons() {
    return weapons;
  }


  /**
   * Decide if the given room is neighbor of this room.
   *
   * @param otherRoom The room to be decided if neighbors
   * @return true if the given room is neighbor of this room, false otherwise
   */
  @Override
  public boolean isNeighbor(Room otherRoom) {
    return this.neighbors.contains(otherRoom);
  }


  /**
   * Returns neighbors of this room. Spaces that share a "wall" are neighbors.
   *
   * @return A list of Room representing neighbors.
   */
  @Override
  public List<Room> getNeighbors() {
    List<Room> listOfRooms = Mansion.getListOfRooms();
    List<Room> neighbors = new ArrayList<>();

    // 1. when (this.y2 == other.y1, or this.y1 == other.y2),
    //    check if this.x2 > other.x1 and this.x1 < other.x2
    // 2. when (this.x2 == other.x1, or this.x1 == other.x2),
    //    check if this.y1 < other.y2 and this.y2 > other.y1
    for (Room otherRoom : listOfRooms) {
      RoomInfo o = (RoomInfo) otherRoom;
      if (this != otherRoom && shareCoordinate(o)) {
        if (this.y2 == o.y1 || this.y1 == o.y2) {
          if (this.x2 > o.x1 && this.x1 < o.x2) {
            neighbors.add(otherRoom);
            continue;
          }
        }
        if (this.x2 == o.x1 || this.x1 == o.x2) {
          if (this.y1 < o.y2 && this.y2 > o.y1) {
            neighbors.add(otherRoom);
          }
        }
      }
    }
    return neighbors;
  }

  /**
   * Decide if two rooms share a coordinate, either x or y.
   *
   * @param otherRoom The room to be decided if neighbors
   */
  private boolean shareCoordinate(RoomInfo otherRoom) {

    return this.x1 == otherRoom.x1 || this.x1 == otherRoom.x2 || this.x2 == otherRoom.x1
        || this.x2 == otherRoom.x2 || this.y1 == otherRoom.y1 || this.y1 == otherRoom.y2
        || this.y2 == otherRoom.y1 || this.y2 == otherRoom.y2;
  }

  /**
   * Saves the neighbors of this room.
   *
   * @param neighbors The list of neighbors
   */
  @Override
  public void setNeighbors(List<Room> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * Removes the given weapon from the room.
   */
  @Override
  public void removeWeapon(WeaponImp weapon) {
    this.weapons.remove(weapon);
  }


  /**
   * Finds out if target is here.
   *
   * @return true if the target is here, false otherwise
   */
  @Override
  public boolean isTargetHere() {
    return this.equals(Mansion.getTarget().getCurrentLocation());
  }

  /**
   * Finds out if pet is here.
   *
   * @return true if the pet is here, false otherwise
   */
  @Override
  public boolean isPetHere() {
    Pet pet = Mansion.getPet();
    return this.equals(pet.getCurrentLocation());
  }

  /**
   * Displays the pet information in the room.
   */
  @Override
  public void displayPet() {
    System.out.println(
        String.format("   %s the cat is in room %d, the %s.", Mansion.getPet().getName(), roomNumber, roomName));
  }

  /**
   * Displays target information.
   **/
  @Override
  public void displayTarget() {
    if (isTargetHere()) {
      for (int j = 0; j < (roomName.length() + 7); j++) {
        System.out.print(" ");
      }
      System.out.println(
          String.format("Target is in room %d!", this.getRoomNumber()));
    } else {
      System.out.println("   Target is not here.");
    }
  }

  /**
   * Finds out if any player is here.
   *
   * @return true if any player is here, false otherwise
   */
  @Override
  public boolean isAnyPlayerHere() {
    List<Player> players = Mansion.getListOfPlayers();
    for (Player player : players) {
      if (this.equals(player.getCurrentLocation())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Finds out if any player other than the given player is here.
   *
   * @param player The player
   */
    @Override
    public boolean isAnyOtherPlayerHere(Player player) {
      List<Player> players= Mansion.getListOfPlayers();
        for (Player p : players) {
            if (this.equals(p.getCurrentLocation()) && !p.equals(player)) {
                return true;
            }
        }
        return false;
    }

  /**
   * Displays information of all players in the room.
   **/
  @Override
  public void displayPlayers() {
    List<Player> players = Mansion.getListOfPlayers();
    if (isAnyPlayerHere()) {
      for (Player player : players) {
        if (this.equals(player.getCurrentLocation())) {
          for (int j = 0; j < (roomName.length() + 7); j++) {
            System.out.print(" ");
          }
          System.out.println(
              String.format(
                  String.format("Player %s is in room %d!", player.getName(),
                      this.getRoomNumber())));
        }
      }
    } else {
      System.out.println("   No player in this room.");
    }
  }

  /**
   * Displays the weapon information in the room.
   */
  @Override
  public void displayWeapons() {
    if (weapons.isEmpty()) {
      System.out.println("-> There is no weapon in this room.");
    } else if (weapons.size() == 1) {
      //there are numbers of weapons in this room
      System.out.println(
          String.format("-> Weapon %s with power %d is in this room.", weapons.get(0).getName(),
              weapons.get(0).getPower()));
    } else {
      System.out.println(String.format("-> There are %d weapons in this room: ", weapons.size()));
      int i = 1;
      for (WeaponImp weapon : weapons) {
        for (int j = 0; j < (roomName.length() + 7); j++) {
          System.out.print(" ");
        }
        System.out.println(
            String.format("(%d) %s with power %d", i, weapon.getName(),
                weapon.getPower()));
        i += 1;
      }
    }
  }

  /**
   * Displays the room number and name of neighbors.
   */
  @Override
  public void displayNeighborsSimple() {
    if (neighbors.isEmpty()) {
      System.out.println("This room has no neighboring room.");
    } else {
      //System.out.println("The neighbors of the room are: ");
      for (Room neighbor : neighbors) {
        System.out.println(
            String.format("   %d. %s", neighbor.getRoomNumber(), neighbor.getRoomName()));
      }
    }
  }

  /**
   * Displays the neighbors of the room with all information.
   */
  @Override
  public void displayNeighborsAllInfo() {
    if (neighbors.isEmpty()) {
      System.out.println("This room has no neighboring room.");
    } else {
      System.out.println("The neighbors of this room are: ");
      for (Room neighbor : neighbors) {
        System.out.print(
            String.format("%d. %s ", neighbor.getRoomNumber(), neighbor.getRoomName()));
        neighbor.displayWeapons();
        //check if target in this room and display target information
        if (neighbor.isTargetHere()) {
          neighbor.displayTarget();
        }
        //check if pet in this room and display pet information
        if(neighbor.isPetHere()){
          neighbor.displayPet();
        }
        //check if any player in this room and display player information
        if (neighbor.isAnyPlayerHere()) {
          neighbor.displayPlayers();
        }
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RoomInfo)) {
      return false;
    }
    RoomInfo roomInfo = (RoomInfo) o;
    return getRoomNumber() == roomInfo.getRoomNumber() && getX1() == roomInfo.getX1()
        && getY1() == roomInfo.getY1() && getX2() == roomInfo.getX2() && getY2() == roomInfo.getY2()
        && Objects.equals(getRoomName(), roomInfo.getRoomName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRoomNumber(), getX1(), getY1(), getX2(), getY2(), getRoomName());
  }
}
