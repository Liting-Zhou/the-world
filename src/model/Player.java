package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a player character in the game. Players can take actions, move between rooms,
 * and interact with other characters and objects.
 */
public class Player extends AbstractCharacter {
  private final int typeOfPlayer; // 0 for human, 1 for computer
  protected List<WeaponImp> weaponsCarried;
  private int indexOfPlayer;


  /**
   * Constructs a new Player object.
   *
   * @param indexOfPlayer      The index of the player.
   * @param typeOfPlayer       The type of the player. 0 for human, 1 for computer.
   * @param name               The name of the player.
   * @param currentLocation    The initial current location of the player.
   * @param maxNumberOfWeapons The maximum number of weapons the player can carry.
   */
  public Player(int indexOfPlayer, int typeOfPlayer, String name, Room currentLocation,
                int maxNumberOfWeapons) {
    super(); // Call the constructor of the superclass (model.AbstractCharacter).
    this.indexOfPlayer = indexOfPlayer;
    this.typeOfPlayer = typeOfPlayer;
    this.weaponsCarried = new ArrayList<>();
    this.maxNumberOfWeapons = maxNumberOfWeapons;
    setName(name); // Set the name of the player using the inherited setName method.
    setCurrentLocation(
        currentLocation); // Set the current location using the inherited setCurrentLocation method.
    currentLocation.setPlayerFlag(true);
    currentLocation.addPlayer(this);
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
   * Updates the location of the player. Also updates the player present flag in the previous
   * and current location.
   *
   * @param newLocation The new location of the player.
   */
  public void updateLocation(Room newLocation) {
    Room previousLocation = this.getCurrentLocation();
    List<Player> playersInTheRoom = previousLocation.getPlayersInTheRoom();
    if (playersInTheRoom.size() == 1) {
      previousLocation.setPlayerFlag(false);
    }
    previousLocation.removePlayer(this);
    this.setCurrentLocation(newLocation);
    newLocation.setPlayerFlag(true);
    newLocation.addPlayer(this);
  }

  /**
   * Determines if this player can be seen by other players.
   *
   * @return true if this player can be seen by other players, false otherwise.
   */
  public boolean canBeSeen() {
    //if any other player is here, you can be seen
    if (getCurrentLocation().isAnyOtherPlayerHere(this)) {
      return true;
    }
    // if cat is here, cannot be seen
    if (getCurrentLocation().isPetHere()) {
      return false;
    } else {
      //check if there is any player in the neighboring rooms
      List<Room> neighbors = getCurrentLocation().getNeighbors();
      for (Room neighbor : neighbors) {
        if (neighbor.isAnyOtherPlayerHere(this)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Player look around. Displays all information about neighboring rooms.
   */
  public void lookAround() {
    displayLookAroundInformation();
  }

  /**
   * Displays the player information, including name, weapon carried, current location,
   * and neighbors.
   */
  public void displayLookAroundInformation() {
    Room room = getCurrentLocation();
    System.out.println("~~~~~~~~~");
    System.out.println("By looking around, you got the following information:");
    System.out.printf("Your current Location: Room %d, the %s%n",
        room.getRoomNumber(),
        room.getRoomName());
    room.displayWeapons();
    if (!room.isAnyOtherPlayerHere(this) && !room.isPetHere()
        && !room.isTargetHere()) {
      System.out.println("No one else is in this room.");
    }
    if (room.isTargetHere()) {
      System.out.println("The target is in this room.");
    }
    if (room.isPetHere()) {
      System.out.println("The cat is in this room.");
    }
    if (room.isAnyOtherPlayerHere(this)) {
      for (Player p : room.getPlayersInTheRoom()) {
        if (p.getCurrentLocation().equals(room) && (!p.equals(this))) {
          System.out.printf("%s is here.%n", p.getName());
        }
      }
    }
    getCurrentLocation().displayNeighborsAllInfo();
    System.out.println("~~~~~~~~~");
  }

  /**
   * Displays the weapon carried by the player.
   */
  public void displayWeaponInformation() {
    if (weaponsCarried.isEmpty()) {
      System.out.println(" has/have no weapon.");
    } else {
      System.out.printf(" has/have %d weapon(s):%n", weaponsCarried.size());
      int i = 1;
      for (WeaponImp weapon : weaponsCarried) {
        System.out.printf("(%d) %s with power %s%n", i, weapon.getName(), weapon.getPower());
        i += 1;
      }
    }
  }


  /**
   * Checks if this character is equal to another object.
   *
   * @param o The object to compare for equality.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }
    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Player)) {
      return false;
    }
    // The successful instanceof check means our cast will succeed:
    Player that = (Player) o;

    return Objects.equals(getName(), that.getName())
        && Objects.equals(weaponsCarried, that.weaponsCarried)
        && Objects.equals(typeOfPlayer, that.typeOfPlayer);
  }

  /**
   * Returns a hash code value for this character.
   *
   * @return The hash code value for this character.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getName(), weaponsCarried, typeOfPlayer);
  }
}

