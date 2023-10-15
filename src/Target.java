import java.util.List;
import java.util.Scanner;

/**
 * Represents the target character in the game.
 * The target has health and move to the next room each round.
 */
public class Target extends Character {

  private int health;

  /**
   * Constructs a new Target object.
   *
   * @param name            The name of the target.
   * @param health          The initial health of the target.
   * @param currentLocation The initial current location of the target.
   */
  public Target(String name, int health, RoomInfo currentLocation) {
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
  public Target move(Mansion mansion, List<Player> players, List<RoomInfo> listOfRooms) {
    // get current location
    int currentLocation = getCurrentLocation().getRoomNumber();
    // if currently in room 21, move to room 0, otherwise move to the next room
    int moveToRoom = 0;
    if (currentLocation < listOfRooms.size() - 1) {
      moveToRoom = getCurrentLocation().getRoomNumber() + 1;
    }
    // move to next room
    RoomInfo newLocation = mansion.getRoomInfoByRoomNumber(moveToRoom);
    setCurrentLocation(newLocation);

    Target updatedTarget = this;
    System.out.println(
        "Target moves and now is in room " + updatedTarget.getCurrentLocation().getRoomNumber()
            + ".");
    //if any player is in the same room with target, raise actions.
    for (Player player : players) {
      if (newLocation == player.getCurrentLocation()) {
        //check if they can be seen
        List<RoomInfo> neighbors = newLocation.getNeighbors(listOfRooms);
        boolean canBeSeen = false;
        for (Player people : players) {
          RoomInfo currentRoom = people.getCurrentLocation();
          if (neighbors.contains(currentRoom)) {
            canBeSeen = true;
            System.out.println("You can be seen, no attack.");
            break;
          }
        }
        if (!canBeSeen) {
          Scanner scanner = new Scanner(System.in);
          System.out.println(
              player.getName() + "! You are now with target in the same room. "
                  + "And no one can see you. Do you want to attack? Enter yes or no: ");
          String attackTarget = scanner.nextLine();
          if ("yes".equals(attackTarget)) {
            updatedTarget = player.attack(newLocation, this);
          }
        }
      }
    }
    // return target with new location and updated health
    return updatedTarget;
  }
}
