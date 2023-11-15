package model;

import java.util.ArrayList;
import java.util.List;

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
    updateLocation(Mansion.getRoomByRoomNumber(order[index]));
  }

  /**
   * A depth-first traversal algorithm to decide the route of the cat.
   *
   * @return the order list of the route.
   */
  private List<Room> depthFirstTraversal() {
    Room startingRoom = Mansion.getRoomByRoomNumber(0);
    List<Room> order = new ArrayList<>();
    order.add(currentLocation);
    depthFirstTraversalHelper(order, currentLocation);
    System.out.println(order);
    return order;
  }

  private void depthFirstTraversalHelper(List<Room> order, Room location) {
    List<Room> neighbors = location.getNeighbors();
    for (Room neighbor : neighbors) {
      if (order.contains(neighbor)) {
        continue;
      }
      order.add(neighbor);
      depthFirstTraversalHelper(order, neighbor);
    }
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
