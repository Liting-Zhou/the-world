import java.util.ArrayList;
import java.util.List;

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
  private List<Weapon> weapons;

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
   * Returns neighbors of this room. Spaces that share a "wall" are neighbors.
   *
   * @param listOfRooms The list of all rooms
   * @return A list of integers representing neighboring room numbers.
   */
  public List<Integer> getNeighbors(List<RoomInfo> listOfRooms) {
    List<Integer> neighboringRoomNumbers = new ArrayList<>();

    int lenOfWidthThisRoom = x2 - x1;
    int lenOfHeightThisRoom = y2 - y1;
    double centerXofThisRoom = (x1 + x2) * 0.5;
    double centerYofThisRoom = (y1 + y2) * 0.5;
    // Iterate through all rooms to find neighbors based on shared "walls."
    for (RoomInfo otherRoom : listOfRooms) {
      if (this != otherRoom) {
        // calculate the max length of center points of the two rooms to be neighbors.
        int lenOfWidthOtherRoom = otherRoom.getX2() - otherRoom.getX1();
        int lenOfHeightOtherRoom = otherRoom.getY2() - otherRoom.getY1();
        double maxLen = Math.pow((Math.pow((lenOfWidthThisRoom + lenOfWidthOtherRoom) * 0.5, 2)
            + Math.pow((lenOfHeightThisRoom + lenOfHeightOtherRoom) * 0.5, 2)), 0.5);
        //calculate the real length of center points of the two rooms.
        double centerXofOtherRoom = (otherRoom.getX1() + otherRoom.getX2()) * 0.5;
        double centerYofOtherRoom = (otherRoom.getY1() + otherRoom.getY2()) * 0.5;
        double horizontalLen = Math.abs(centerXofOtherRoom - centerXofThisRoom);
        double verticalLen = Math.abs(centerYofOtherRoom - centerYofThisRoom);
        double realLen = Math.pow((Math.pow(horizontalLen, 2)
            + Math.pow(verticalLen, 2)), 0.5);

        boolean sharesWall = false;
        // share wall if the real length of center points is less than the maximal length
        if (realLen < maxLen) {
          sharesWall = true;
        }

        if (sharesWall) {
          neighboringRoomNumbers.add(otherRoom.getRoomNumber());
        }
      }
    }

    return neighboringRoomNumbers;
  }
}
