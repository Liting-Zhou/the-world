package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a cat in the game.
 */
public class Pet extends AbstractCharacter {
  private final List<Room> order;
  private boolean isMoved = false;

  /**
   * Constructs a new Pet object.
   *
   * @param name            The name of the cat.
   * @param currentLocation The starting location of the cat.
   */
  public Pet(String name, Room currentLocation) {
    super();
    setName(name);
    setCurrentLocation(currentLocation);
    currentLocation.setPetFlag(true);
    this.order = depthFirstTraversal();
  }

  /**
   * A depth-first traversal algorithm to decide the route of the cat.
   *
   * @return the ordered list of the route.
   */
  private List<Room> depthFirstTraversal() {
    List<Room> newOrder = new ArrayList<>();
    newOrder.add(this.getCurrentLocation());
    depthFirstTraversalHelper(newOrder, this.getCurrentLocation());
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
  public void wander() {
    if (isMoved) {
      isMoved = false;
      return;
    }
    int index = order.indexOf(this.getCurrentLocation());
    if (index == order.size() - 1) {
      index = 0;
    } else {
      index = index + 1;
    }
    updateLocation(order.get(index));
  }


  public void setMoved() {
    isMoved = true;
  }


  /**
   * Update the location of the pet.
   *
   * @param room the room to be updated to
   */
  public void updateLocation(Room room) {
    this.getCurrentLocation().setPetFlag(false); //set the pet flag of the previous room to false
    this.setCurrentLocation(room);
    this.getCurrentLocation().setPetFlag(true); //set the pet flag of the current room to true
  }
}
