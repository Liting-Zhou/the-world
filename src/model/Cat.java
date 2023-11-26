package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a cat in the game.
 */
public class Cat implements Pet {
  private final String name;
  private final List<Room> order;
  private Room currentLocation;
  private boolean isMoved = false;
  //if the cat is moved by a player last turn, it will not move this turn

  /**
   * Constructs a new Cat object.
   *
   * @param name            The name of the cat.
   * @param currentLocation The starting location of the cat.
   */
  public Cat(String name, Room currentLocation) {
    this.name = name;
    this.currentLocation = currentLocation;
    this.currentLocation.setPetFlag(true);
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
    if (isMoved) {
      isMoved = false;
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
  public void setMoved() {
    isMoved = true;
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
    this.currentLocation.setPetFlag(false); //set the pet flag of the previous room to false
    this.currentLocation = room;
    this.currentLocation.setPetFlag(true); //set the pet flag of the current room to true
  }
}
