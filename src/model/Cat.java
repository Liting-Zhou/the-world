package model;

/**
 * This class represents a cat in the game.
 */
public class Cat implements Pet {
  private String name;
  private Room currentLocation;

  public Cat(String name, Room currentLocation) {
    this.name = name;
    this.currentLocation = currentLocation;
  }

  /**
   * Pet wanders in the world. Maybe follow depth-first traversal.
   * I use the BST constructed in lab08 to generate a post-order traversal.
   * [2 1 0 4 5 7 9 8 6 3 11 13 16 15 14 12 18 20 21 19 17 10]
   * Thus the pet would wander following this order.
   */
  @Override
  public void wander() {
    if (Mansion.getFlag() == 1) {
      Mansion.setFlag(0);
      return;
    }
    int[] order = {2, 1, 0, 4, 5, 7, 9, 8, 6, 3, 11, 13, 16, 15, 14, 12, 18, 20, 21, 19, 17, 10};
    int currentRoom = currentLocation.getRoomNumber();
    int index = -1;
    for (int i = 0; i < order.length; i++) {
      if (order[i] == currentRoom) {
        if (i == 21) {
          index = 0;
        } else {
          index = i + 1;
        }
        break;
      }
    }
    updateLocation(Mansion.getRoomInfoByRoomNumber(index));
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Room getCurrentLocation() {
    return currentLocation;
  }

  @Override
  public void updateLocation(Room room) {
    this.currentLocation = room;
  }
}
