package model;

import java.util.List;
import java.util.Scanner;

/**
 * Represents a human player in the game.
 */
public class HumanPlayer extends Player {
  /**
   * Constructs a new HumanPlayer object.
   *
   * @param indexOfPlayer      The index of the player.
   * @param typeOfPlayer       The type of the player. 0 for human, 1 for computer.
   * @param name               The name of the player.
   * @param currentLocation    The initial current location of the player.
   * @param maxNumberOfWeapons The maximum number of weapons the player can carry.
   */
  public HumanPlayer(int indexOfPlayer, int typeOfPlayer, String name, Room currentLocation,
                     int maxNumberOfWeapons) {
    super(indexOfPlayer, typeOfPlayer, name, currentLocation, maxNumberOfWeapons);
  }

  /**
   * Player moves to a specific room.
   */
  public void move() {
    //move to a neighboring space
    Scanner scanner = new Scanner(System.in);
    System.out.println("Which neighboring room do you want to enter? Enter the room number: ");
    Integer roomNumber = scanner.nextInt();
    //check if the room is a neighbor
    if (this.getCurrentLocation().isNeighbor(Mansion.getRoomInfoByRoomNumber(roomNumber))) {
      this.setCurrentLocation(Mansion.getRoomInfoByRoomNumber(roomNumber));
      System.out.println(String.format("You are now in room %d.", roomNumber));
    } else {
      System.out.println("The room is not a neighbor.");
    }
  }

  /**
   * Player picks up a weapon.
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
          String.format("You picked up %s with power %d.", weapons.get(0).getName(),
              weapons.get(0).getPower()));
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
      System.out.println(
          String.format("You picked up %s with power %d.", weapon.getName(), weapon.getPower()));
      System.out.print("Now you");
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapon);
    }
  }

  public void attack() {
    //TODO: implement this method
  }
}

