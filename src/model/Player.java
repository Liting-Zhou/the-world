package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a player character in the game. Players can take actions, move between rooms,
 * and interact with other characters and objects.
 */
public class Player extends AbstractCharacter {
  private int indexOfPlayer;
  private int typeOfPlayer; // 0 for human, 1 for computer
  private List<WeaponImp> weaponsCarried;

  /**
   * Constructs a new model.Player object.
   *
   * @param indexOfPlayer   The index of the player.
   * @param name            The name of the player.
   * @param currentLocation The initial current location of the player.
   */
  public Player(int indexOfPlayer, int typeOfPlayer, String name, Room currentLocation,int maxNumberOfWeapons) {
    super(); // Call the constructor of the superclass (model.AbstractCharacter).
    this.indexOfPlayer = indexOfPlayer;
    this.typeOfPlayer = typeOfPlayer;
    this.weaponsCarried = new ArrayList<>();
    this.maxNumberOfWeapons = maxNumberOfWeapons;
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
   * Gets the type of the player.
   *
   * @return The type of the player. 0 for human, 1 for computer.
   */
  public int getTypeOfPlayer() {
    return typeOfPlayer;
  }

  /**
   * Gets the maximum number of weapons the player can carry.
   *
   * @return The maximum number of weapons the player can carry.
   */
  public int getMaxNumberOfWeapons() {
    return maxNumberOfWeapons;
  }

  /**
   * Updates the room information for the player based on their current location.
   *
   * @param newLocation The new location to set for the player.
   */
  public void updateRoomInfo(Room newLocation) {
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
    System.out.println(String.format("You are now in room %d.", roomNumber));
  }

  /**
   * Player pick up a weapon.
   */
  public void pickUpWeapon() {
    //check if the player can carry more weapons
    if (weaponsCarried.size() == maxNumberOfWeapons) {
      System.out.println("You cannot carry more weapons.");
      return;
    }
    //list the weapons in the room
    List<WeaponImp> weapons = this.getCurrentLocation().getWeapons();
    //see if there is any weapon in the room
    if (weapons.isEmpty()) {
      System.out.println("No weapons in this room.");
      //pick up the weapon
    } else if (weapons.size() == 1) {
      weaponsCarried.add(weapons.get(0));
      System.out.println(
          String.format("You picked up %s with power %d.", weapons.get(0).getName(), weapons.get(0).getPower()));
      System.out.print("Now you");
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapons.get(0));
    } else {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Which one do you want to pick up? Enter the corresponding number: ");
      Integer weaponNumber = scanner.nextInt();
      WeaponImp weapon = weapons.get(weaponNumber - 1);
      weaponsCarried.add(weapon);
      System.out.println(String.format("You picked up %s with power %d.", weapon.getName(), weapon.getPower()));
      System.out.print("Now you");
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapon);
    }
  }

  /**
   * Player look around.
   */
  public void lookAround() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Which player do you want to look around? Please enter the name: ");
    String playerName = scanner.nextLine();

    //find the player by player name
    for (Player player : MyWorld.getPlayers()) {
      if (player.getName().equalsIgnoreCase(playerName)) {
        player.displayPlayerInformation();
      }
    }
  }

  /**
   * Displays the player information, including name, weapon carried, current location, and neighbors.
   */
  public void displayPlayerInformation() {
    System.out.println("--------------");
    System.out.print(String.format("Player %s", getName()));
    displayWeaponInformation();
    System.out.println(String.format("Current Location: Room %d", getCurrentLocation().getRoomNumber()));
    getCurrentLocation().displayNeighbors();
  }
  /**
   * Displays the weapon carried by the player.
   *
   */
  public void displayWeaponInformation() {
    if (weaponsCarried.isEmpty()) {
      System.out.println(" has/have no weapon.");
    } else {
      for (WeaponImp weapon : weaponsCarried) {
        System.out.println(" has/have the following weapon(s):");
        System.out.println(String.format("%s with power %s", weapon.getName(), weapon.getPower()));
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
  public Target action(String action, Room newLocation, Target target, List<Player> players,
                       List<Room> listOfRooms) {
    Target updatedTarget = target;
    if ("stay".equals(action)) {
      // TODO if stay, what to do?
    } else if ("move".equals(action)) {
      //move to the new location
      setCurrentLocation(newLocation);
      //check if target in the same room
      Room targetLocation;
      targetLocation = target.getCurrentLocation();
      if (newLocation == targetLocation) {
        //check if they can be seen
        List<Room> neighbors = newLocation.getNeighbors(listOfRooms);
        boolean canBeSeen = false;
        for (Player player : players) {
          Room currentRoom = player.getCurrentLocation();
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
  public Target attack(Room room, Target target) {
    List<WeaponImp> weapons = room.getWeapons();
    WeaponImp weapon = null;
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
          String.format("Target's health is deduced by %d and now is %d.", power, target.getHealth()));
    }

    return target;
  }

}

