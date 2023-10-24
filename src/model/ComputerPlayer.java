package model;

import java.util.List;

/**
 * This class represents a computer player in the game.
 */
public class ComputerPlayer extends Player {
  private RandomNumber random;

  /**
   * Constructs a new ComputerPlayer object.
   *
   * @param indexOfPlayer      The index of the player.
   * @param typeOfPlayer       The type of the player. 0 for human, 1 for computer.
   * @param name               The name of the player.
   * @param currentLocation    The initial current location of the player.
   * @param maxNumberOfWeapons The maximum number of weapons the player can carry.
   */
  public ComputerPlayer(int indexOfPlayer, int typeOfPlayer, String name, Room currentLocation,
                        int maxNumberOfWeapons) {
    super(indexOfPlayer, typeOfPlayer, name, currentLocation, maxNumberOfWeapons);
    this.random = new RandomNumber();
  }

  /**
   * Computer Player randomly picks an action to perform.
   *
   * @param rooms The list of rooms in the game.
   */
  public void randomAction(List<Room> rooms) {
    int action = random.nextRandomInt(3);
    if (action == 0) {
      System.out.println(
          String.format("The random action of computer player %s is to move to a neighboring room.",
              this.getName()));
      move(rooms);
    } else if (action == 1) {
      System.out.println(
          String.format("The random action of computer player %s is to pick up a weapon.",
              this.getName()));
      pickUpWeapon();
    } else {
      System.out.println(String.format("The random action of computer player %s is to look around.",
          this.getName()));
      lookAround();
    }
  }

  /**
   * Computer Player randomly picks an action to perform when there is no weapon in the room.
   *
   * @param rooms The list of rooms in the game.
   */
  public void randomActionNoWeapon(List<Room> rooms) {
    int action = random.nextRandomInt(2);
    if (action == 0) {
      System.out.println(
          String.format("The random action of computer player %s is to move to a neighboring room.",
              this.getName()));
      move(rooms);
    } else {
      System.out.println(String.format("The random action of computer player %s is to look around.",
          this.getName()));
      lookAround();
    }
  }

  /**
   * Player moves to a specific room.
   *
   * @param rooms The list of rooms in the game.
   */
  public void move(List<Room> rooms) {
    //randomly move to a neighboring space
    List<Room> neighbors = this.getCurrentLocation().getNeighbors(rooms);
    int index = random.nextRandomInt(neighbors.size());
    Room moveToRoom = neighbors.get(index);
    this.setCurrentLocation(moveToRoom);
    System.out.println(
        String.format("Player %s is now in room %d.", this.getName(), moveToRoom.getRoomNumber()));
  }

  /**
   * Player picks up a weapon.
   */
  public void pickUpWeapon() {
    //check if the player can carry more weapons
    if (weaponsCarried.size() == maxNumberOfWeapons) {
      System.out.println(String.format("Player %s cannot carry more weapons.", this.getName()));
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
          String.format("Player %s picked up %s with power %d.", this.getName(),
              weapons.get(0).getName(),
              weapons.get(0).getPower()));
      System.out.print(String.format("Now %s", this.getName()));
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapons.get(0));
    } else {
      //randomly pick a weapon
      int weaponNumber = random.nextRandomInt(weapons.size());
      WeaponImp weapon = weapons.get(weaponNumber);
      weaponsCarried.add(weapon);
      System.out.println(
          String.format(String.format("Player %s picked up %s with power %d.", this.getName(),
              weapon.getName(), weapon.getPower())));
      System.out.print(String.format("Now %s", this.getName()));
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapon);
    }
  }
}
