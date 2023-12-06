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
    super();
    this.indexOfPlayer = indexOfPlayer;
    this.typeOfPlayer = typeOfPlayer;
    this.weaponsCarried = new ArrayList<>();
    this.maxNumberOfWeapons = maxNumberOfWeapons;
    setName(name);
    setCurrentLocation(
        currentLocation);
    currentLocation.setPlayerFlag(true);
    currentLocation.addPlayer(this);
  }

  /**
   * Gets the index of the player.
   *
   * @return The index of the player
   */
  public int getIndexOfPlayer() {
    return indexOfPlayer;
  }

  /**
   * Sets the index of the player.
   *
   * @param indexOfPlayer The index of the player to set
   */
  public void setIndexOfPlayer(int indexOfPlayer) {
    this.indexOfPlayer = indexOfPlayer;
  }

  /**
   * Gets the type of the player.
   *
   * @return The type of the player. 0 for human, 1 for computer
   */
  public int getTypeOfPlayer() {
    return typeOfPlayer;
  }

  /**
   * Gets the maximum number of weapons the player can carry.
   *
   * @return The maximum number of weapons the player can carry
   */
  public int getMaxNumberOfWeapons() {
    return maxNumberOfWeapons;
  }

  /**
   * Gets the list of weapons carried by the player.
   *
   * @return The list of weapons carried by the player
   */
  public List<WeaponImp> getWeaponsCarried() {
    return weaponsCarried;
  }

  /**
   * Updates the location of the player. Also updates the player present flag in the previous
   * and current location.
   *
   * @param newLocation The new location of the player
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
   * Player looks around. Displays all the information about current room and neighbors.
   *
   * @return the display message of looking around
   */
  public String lookAround() {
    Room room = getCurrentLocation();
    StringBuilder sb = new StringBuilder();

    sb.append("~~~~~~~~~\n");
    sb.append("By looking around, you got the following information:\n");
    sb.append(String.format("Your current Location: Room %d, the %s%n",
        room.getRoomNumber(),
        room.getRoomName()));

    sb.append(room.displayWeapons());

    if (!room.isAnyOtherPlayerHere(this) && !room.isPetHere()
        && !room.isTargetHere()) {
      sb.append("No one else is in this room.\n");
    }
    if (room.isTargetHere()) {
      sb.append("The target is in this room.\n");
    }
    if (room.isPetHere()) {
      sb.append("The cat is in this room.\n");
    }
    if (room.isAnyOtherPlayerHere(this)) {
      for (Player p : room.getPlayersInTheRoom()) {
        if (p.getCurrentLocation().equals(room) && (!p.equals(this))) {
          sb.append(String.format("%s is here.%n", p.getName()));
        }
      }
    }
    sb.append(getCurrentLocation().displayNeighborsAllInfo());
    sb.append("~~~~~~~~~\n");
    return sb.toString();
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
    if (this == o) {
      return true;
    }
    if (!(o instanceof Player)) {
      return false;
    }
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

