package model;

import java.util.List;
import java.util.Scanner;

/**
 * Represents the target character in the game.
 * The target has health and move to the next room each round.
 */
public final class Target extends AbstractCharacter {

  private int health;

  /**
   * Constructs a new model.Target object.
   *
   * @param name            The name of the target.
   * @param health          The initial health of the target.
   * @param currentLocation The initial current location of the target.
   */
  public Target(String name, int health, Room currentLocation) {
    super();
    this.health = health;
    setName(name); // Set the name of the target using the inherited setName method.
    setCurrentLocation(
        currentLocation); // Set the current location using the inherited setCurrentLocation method.
  }

  /**
   * Gets the current health of the target.
   *
   * @return The current health of the target.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Sets the health of the target after an attack.
   *
   * @param power The power of the weapon used in the attack.
   */
  public void setHealth(int power) {
    this.health -= power;
  }

  /**
   * Moves the target character to the next room. If encounter players, might get attacked.
   *
   * @param mansion     The mansion.
   * @param players     The list of all players in the game.
   * @param listOfRooms The list of all rooms
   * @return The updated target character with new location and health.
   */
  public Target move(Mansion mansion, List<Player> players, List<Room> listOfRooms) {
    // get current location
    int currentLocation = getCurrentLocation().getRoomNumber();
    // if currently in room 21, move to room 0, otherwise move to the next room
    int moveToRoom = 0;
    if (currentLocation < listOfRooms.size() - 1) {
      moveToRoom = getCurrentLocation().getRoomNumber() + 1;
    }
    // move to next room
    Room newLocation = mansion.getRoomInfoByRoomNumber(moveToRoom);
    setCurrentLocation(newLocation);

    Target updatedTarget = this;
    System.out.println(
        String.format("Target moves and now is in room %d.",
            updatedTarget.getCurrentLocation().getRoomNumber()));

    // return target with new location and updated health
    return updatedTarget;
  }
}
