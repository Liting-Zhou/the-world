import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents information about a room in Doctor Lucky's Mansion, including its position, name,
 * weapons present, and neighbors.
 */
public class RoomInfo {

  private final int roomNumber;
  private final int x1; //left up corner
  private final int y1; //left up corner
  private final int x2; //right bottom corner
  private final int y2; //right bottom corner
  private final String roomName;
  private final List<Weapon> weapons;
  private List<RoomInfo> neighbors = new ArrayList<>();

  /**
   * Constructs a new RoomInfo object.
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
                  List<Weapon> weapons) {
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
  public int getX1() {
    return x1;
  }

  /**
   * Gets the y coordinate of upper left corner.
   *
   * @return y1
   */
  public int getY1() {
    return y1;
  }

  /**
   * Gets the x coordinate of bottom right corner.
   *
   * @return x2
   */
  public int getX2() {
    return x2;
  }

  /**
   * Gets the y coordinate of bottom right corner.
   *
   * @return y2
   */
  public int getY2() {
    return y2;
  }

  /**
   * Gets the name of the room.
   *
   * @return The name of the room
   */
  public String getRoomName() {
    return roomName;
  }

  /**
   * Gets the unique room number.
   *
   * @return The room number.
   */
  public int getRoomNumber() {
    return roomNumber;
  }

  /**
   * Gets the list of weapons present in the room.
   *
   * @return The list of weapons.
   */
  public List<Weapon> getWeapons() {
    return weapons;
  }

  /**
   * Displays the weapon information in the room.
   */
  public void displayWeapons() {
    if (weapons.isEmpty()) {
      System.out.println("No weapons in this room.");
    } else if (weapons.size() == 1) {
      //there are numbers of weapons in this room
      System.out.println(
          "Weapon " + weapons.get(0).getName() + " with power " + weapons.get(0).getPower()
              + " is in this room.");
    } else {
      System.out.println("There are " + weapons.size() + " weapons in this room: ");
      int i = 1;
      for (Weapon weapon : weapons) {
        System.out.println(i + "." + weapon.getName() + " with power " + weapon.getPower());
        i += 1;
      }
    }
  }

  /**
   * Displays the neighbors of the room.
   */
  public void displayNeighbors() {
    for (RoomInfo neighbor : neighbors) {
      System.out.println(neighbor.getRoomNumber() + ": " + neighbor.getRoomName());
    }
  }


  /**
   * Decide if the given room is neighbor of this room.
   *
   * @param otherRoom The room to be decided if neighbors
   * @return true if the given room is neighbor of this room, false otherwise
   */
  public boolean isNeighbor(RoomInfo otherRoom) {
    return this.neighbors.contains(otherRoom);
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
   * Returns neighbors of this room. Spaces that share a "wall" are neighbors.
   *
   * @param listOfRooms The list of all rooms
   * @return A list of integers representing neighboring room numbers.
   */
  public List<RoomInfo> getNeighbors(List<RoomInfo> listOfRooms) {
    List<RoomInfo> neighbors = new ArrayList<>();

    // 1. when (this.y2 == other.y1, or this.y1 == other.y2), check if this.x2 > other.x1 and this.x1 < other.x2
    // 2. when (this.x2 == other.x1, or this.x1 == other.x2), check if this.y1 < other.y2 and this.y2 > other.y1
    for (RoomInfo otherRoom : listOfRooms) {
      if (this != otherRoom && shareCoordinate(otherRoom)) {
        if (this.y2 == otherRoom.y1 || this.y1 == otherRoom.y2) {
          if (this.x2 > otherRoom.x1 && this.x1 < otherRoom.x2) {
            neighbors.add(otherRoom);
            continue;
          }
        }
        if (this.x2 == otherRoom.x1 || this.x1 == otherRoom.x2) {
          if (this.y1 < otherRoom.y2 && this.y2 > otherRoom.y1) {
            neighbors.add(otherRoom);
          }
        }
      }
    }
    return neighbors;
  }

  /**
   * Saves the neighbors of this room.
   *
   * @param neighbors The list of neighbors
   */
  public void setNeighbors(List<RoomInfo> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * Removes the given weapon from the room.
   */
  public void removeWeapon(Weapon weapon) {
    this.weapons.remove(weapon);
  }

  /**
   * Finds out if target is here, if yes, display target information.
   */
  public void displayTarget(Target target) {
    if (this.equals(target.getCurrentLocation())) {
      System.out.println("Target is here!");
    } else {
      System.out.println("Target is not here!");
    }
  }

  /**
   * Finds out if any player is here, if yes, display player information.
   */
  public void displayPlayers(List<Player> players) {
    int i = 0;
    for (Player player : players) {
      if (this.equals(player.getCurrentLocation())) {
        System.out.println("Player " + player.getName() + " is here!");
        i += 1;
      }
    }
    if (i == 0) {
      System.out.println("No player is here!");
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
    return getRoomNumber() == roomInfo.getRoomNumber() && getX1() == roomInfo.getX1() &&
        getY1() == roomInfo.getY1() && getX2() == roomInfo.getX2() && getY2() == roomInfo.getY2() &&
        Objects.equals(getRoomName(), roomInfo.getRoomName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRoomNumber(), getX1(), getY1(), getX2(), getY2(), getRoomName());
  }
}
