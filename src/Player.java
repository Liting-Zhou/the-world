import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a player character in the game. Players can take actions, move between rooms,
 * and interact with other characters and objects.
 */
public class Player extends Character {
  private int indexOfPlayer;

  /**
   * Constructs a new Player object.
   *
   * @param indexOfPlayer   The index of the player.
   * @param name            The name of the player.
   * @param currentLocation The initial current location of the player.
   */
  public Player(int indexOfPlayer, String name, RoomInfo currentLocation) {
    super(); // Call the constructor of the superclass (Character).
    this.indexOfPlayer = indexOfPlayer;
    setName(name); // Set the name of the player using the inherited setName method.
    setCurrentLocation(
        currentLocation); // Set the current location using the inherited setCurrentLocation method.
  }

  /**
   * Gets the index of the player.
   *
   * @return The index of the player.
   */
  public int getIndexOfPlayer() {
    return indexOfPlayer;
  }

  /**
   * Sets the index of the player.
   *
   * @param indexOfPlayer The index of the player to set.
   */
  public void setIndexOfPlayer(int indexOfPlayer) {
    this.indexOfPlayer = indexOfPlayer;
  }

  /**
   * Updates the room information for the player based on their current location.
   *
   * @param newLocation The new location to set for the player.
   */
  public void updateRoomInfo(RoomInfo newLocation) {
    setCurrentLocation(newLocation);
  }

  /**
   * Performs an action for the player, either move to a specific room or stay.
   *
   * @param action      The action to perform ("move" or "stay").
   * @param newLocation The new location to move to (if action is "move").
   * @param target      The target character in the game.
   * @param players     The list of all players in the game.
   * @param listOfRooms The list of all rooms.
   * @return The updated target character.
   */
  public Target action(String action, RoomInfo newLocation, Target target, List<Player> players,
                       List<RoomInfo> listOfRooms) {
    Target updatedTarget = target;
    if ("stay".equals(action)) {
      // TODO if stay, what to do?
    } else if ("move".equals(action)) {
      //move to the new location
      setCurrentLocation(newLocation);
      //check if target in the same room
      RoomInfo targetLocation;
      targetLocation = target.getCurrentLocation();
      if (newLocation == targetLocation) {
        //check if they can be seen
        List<Integer> neighbors = newLocation.getNeighbors(listOfRooms);
        boolean canBeSeen = false;
        for (Player player : players) {
          int roomNumber = player.getCurrentLocation().getRoomNumber();
          if (neighbors.contains(roomNumber)) {
            canBeSeen = true;
            System.out.println("You can be seen, no attack.");
            break;
          }
        }
        if (!canBeSeen) {
          Scanner scanner = new Scanner(System.in);
          System.out.println("No one can see you. Do you want to attack? Enter yes or no: ");
          String attackTarget = scanner.nextLine();
          if ("yes".equals(attackTarget)) {
            updatedTarget = attack(newLocation, target);
          }
        }
      }
    }
    return updatedTarget;
  }

  /**
   * Performs an attack action for the player.
   *
   * @param room   The room where the attack takes place.
   * @param target The target character to attack.
   * @return The updated target character.
   */
  public Target attack(RoomInfo room, Target target) {
    List<Weapon> weapons = room.getWeapons();
    Weapon weapon = null;
    // if there are more than one weapons in the room, pick one randomly
    if (weapons.size() > 1) {
      // Create a Random object
      Random random = new Random();
      int randomIndex = random.nextInt(weapons.size());
      // Get the random weapon
      weapon = weapons.get(randomIndex);
    } else if (weapons.size() == 1) {
      weapon = weapons.get(0);
    }
    if (weapon != null) {
      int power = weapon.getPower();
      target.setHealth(power);
      System.out.println(
          "Target's health is deduced by " + power + " and now is " + target.getHealth() + ".");
    }

    return target;
  }

}

