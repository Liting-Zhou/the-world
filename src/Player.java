import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a player character in the game. Players can take actions, move between rooms,
 * and interact with other characters and objects.
 */
public class Player extends Character {
  private int indexOfPlayer;
  private int typeOfPlayer; // 0 for human, 1 for computer
  private List<Weapon> weaponsCarried;

  /**
   * Constructs a new Player object.
   *
   * @param indexOfPlayer   The index of the player.
   * @param name            The name of the player.
   * @param currentLocation The initial current location of the player.
   */
  public Player(int indexOfPlayer, int typeOfPlayer,String name, RoomInfo currentLocation) {
    super(); // Call the constructor of the superclass (Character).
    this.indexOfPlayer = indexOfPlayer;
    this.typeOfPlayer = typeOfPlayer;
    this.weaponsCarried = new ArrayList<>();
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
   * Player moves to a specific room.
   */
  public void move() {
    //move to a neighboring space
    Scanner scanner = new Scanner(System.in);
    System.out.println("Which neighboring room do you want to enter? Enter the room number: ");
    Integer roomNumber = scanner.nextInt();
    //TODO check if the room is a neighbor
    this.setCurrentLocation(Mansion.getRoomInfoByRoomNumber(roomNumber));
    System.out.println("You are now in room " + roomNumber + ".");
  }

  /**
   * Player pick up a weapon.
   */
  public void pickUpWeapon() {
    List<Weapon> weapons = this.getCurrentLocation().getWeapons();
    if (weapons.isEmpty()) {
      System.out.println("No weapons in this room.");
    } else if (weapons.size() == 1) {
      weaponsCarried.add(weapons.get(0));
      System.out.println(
          "You picked up " + weapons.get(0).getName() + " with power " + weapons.get(0).getPower() +
              ".");
      System.out.print("Now you");
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapons.get(0));
    } else {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Which one do you want to pick up? Enter the corresponding number: ");
      Integer weaponNumber = scanner.nextInt();
      Weapon weapon = weapons.get(weaponNumber - 1);
      weaponsCarried.add(weapon);
      System.out.println("You picked up " + weapon.getName() + " with power " + weapon.getPower()
          + ".");
      System.out.print("Now you");
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapon);
    }
  }

  public void lookAround() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Which player do you want to look around? Please enter the name: ");
    String playerName = scanner.nextLine();

    //find the player by player name
    for (Player player : World.getPlayers()) {
      if (player.getName().equalsIgnoreCase(playerName)) {
        player.displayPlayerInformation(player);
      }
    }
  }

  private void displayPlayerInformation(Player player) {
    System.out.println("--------------");
    System.out.print("Player " + player.getName());
    player.displayWeaponInformation();
    System.out.println("Current Location: Room " + player.getCurrentLocation().getRoomNumber());
    System.out.println("The neighbors of the room are: ");
    player.getCurrentLocation().displayNeighbors();
  }

  public void displayWeaponInformation() {
    if (weaponsCarried.isEmpty()) {
      System.out.println(" has/have no weapon.");
    } else {
      for (Weapon weapon : weaponsCarried) {
        System.out.println(" has/have the following weapon(s):");
        System.out.println(weapon.getName() + " with power" + weapon.getPower());
      }
    }
  }

  /**
   * Performs an action for the player, either move to a neighbor room or stay.
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
        List<RoomInfo> neighbors = newLocation.getNeighbors(listOfRooms);
        boolean canBeSeen = false;
        for (Player player : players) {
          RoomInfo currentRoom = player.getCurrentLocation();
          if (neighbors.contains(currentRoom)) {
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

