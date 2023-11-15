package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a cat in the game.
 */
public class Cat implements Pet {
  private String name;
  private Room currentLocation;
  private List<Room> order;

  /**
   * Constructs a new Cat object.
   *
   * @param name            The name of the cat.
   * @param currentLocation The starting location of the cat.
   */
  public Cat(String name, Room currentLocation) {
    this.name = name;
    this.currentLocation = currentLocation;
    this.order = depthFirstTraversal();
  }

  /**
   * A depth-first traversal algorithm to decide the route of the cat.
   *
   * @return the ordered list of the route.
   */
  private List<Room> depthFirstTraversal() {
    List<Room> newOrder = new ArrayList<>();
    newOrder.add(currentLocation);
    depthFirstTraversalHelper(newOrder, currentLocation);
    //System.out.println(newOrder);
    return newOrder;
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

  /**
   * Pet wanders in the world, following a depth-first traversal order initialized
   * when the object is instantiated.
   * If the pet is moved by a played last turn, it will not move this turn.
   */
  @Override
  public void wander() {
    if (Mansion.getFlag() == 1) {
      Mansion.setFlag(0);
      return;
    }
    int index = order.indexOf(currentLocation);
    if (index == order.size() - 1) {
      index = 0;
    } else {
      index = index + 1;
    }
    updateLocation(order.get(index));
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
