package model;

import java.util.List;

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
    setName(name);
    setCurrentLocation(
        currentLocation);
    currentLocation.setTargetFlag(true);
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
   * Resets the health of the target.
   *
   * @param health The initial health of the target.
   */
  public void resetHealth(int health) {
    this.health = health;
  }

  /**
   * Sets the health of the target after an attemptToAttack.
   *
   * @param power The power of the weapon used in the attemptToAttack.
   */
  public void healthDamage(int power) {
    this.health -= power;
  }

  /**
   * Moves the target character to the next room. If encounter players, might get attacked.
   *
   * @param listOfRooms The list of all rooms
   */
  public void move(List<Room> listOfRooms) {
    // get current location
    Room currentLocation = getCurrentLocation();
    int currentLocationNumber = currentLocation.getRoomNumber();
    // if currently in room 21, move to room 0, otherwise move to the next room
    int moveToRoom = 0;
    if (currentLocationNumber < listOfRooms.size() - 1) {
      moveToRoom = currentLocation.getRoomNumber() + 1;
    }
    // move to next room
    Room newLocation = listOfRooms.get(moveToRoom);
    updateLocation(newLocation);

    System.out.printf("Target moves and now is in room %d.%n",
        this.getCurrentLocation().getRoomNumber());
  }

  /**
   * Updates the location of the target, also updates the target present flag in the previous
   * and current location.
   *
   * @param newLocation The new location of the target.
   */
  public void updateLocation(Room newLocation) {
    Room previousLocation = this.getCurrentLocation();
    previousLocation.setTargetFlag(false);
    setCurrentLocation(newLocation);
    newLocation.setTargetFlag(true);
  }
}
