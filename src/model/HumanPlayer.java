package model;

import java.util.ArrayList;
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
   *
   * @param listOfRooms the list of rooms
   */
  public void move(List<Room> listOfRooms) {
    List<Room> neighbors = this.getCurrentLocation().getNeighbors();

    //move to a neighboring space
    Scanner scanner = new Scanner(System.in);
    System.out.println("Which neighboring room do you want to enter? Enter the room number: ");
    //Integer roomNumber = scanner.nextInt();

    //check the input
    int roomNumber;
    while (true) {
      while (!scanner.hasNextInt()) {
        System.out.println("Invalid input. Please enter a valid number:");
        scanner.next(); // consume the invalid token
      }
      roomNumber = scanner.nextInt();
      if (!neighbors.contains(listOfRooms.get(roomNumber))) {
        System.out.println("Invalid number. Please enter again:");
      } else {
        break;
      }
    }
    this.updateLocation(listOfRooms.get(roomNumber));
    System.out.printf("You are now in room %d.%n", roomNumber);
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
      System.out.printf("You picked up %s with power %d.%n", weapons.get(0).getName(),
          weapons.get(0).getPower());
      System.out.print("Now you");
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapons.get(0));
    } else {
      System.out.printf(this.getCurrentLocation().displayWeapons());
      Scanner scanner = new Scanner(System.in);
      System.out.println("Which one do you want to pick up? Enter the corresponding number: ");
      Integer weaponNumber = scanner.nextInt();
      WeaponImp weapon = weapons.get(weaponNumber - 1);
      weaponsCarried.add(weapon);
      System.out.printf("You picked up %s with power %d.%n", weapon.getName(), weapon.getPower());
      System.out.print("Now you");
      displayWeaponInformation();
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapon);
    }
  }

  /**
   * Player attacks the target. If can be seen from other players, no damage made.
   *
   * @param target the target
   */
  public void attack(Target target) {
    //check if the player has weapon
    if (weaponsCarried.isEmpty()) {
      //poke target in the eye
      System.out.println("You have no weapon. Just poke the target in the eye!");
      attackWithNoWeapon(target);
    } else {
      //if yes, display weapon, ask player to choose one
      System.out.print("You");
      displayWeaponInformation();
      System.out.println("Which weapon do you want to choose? Enter the corresponding number. "
          + "Enter 0 if you want to poke the target in the eye!");
      Scanner scanner = new Scanner(System.in);

      //check the input
      int number;
      while (true) {
        while (!scanner.hasNextInt()) {
          System.out.println("Invalid input. Please enter a valid number:");
          scanner.next(); // consume the invalid token
        }
        number = scanner.nextInt();
        if (number < 0 || number > weaponsCarried.size()) {
          System.out.println("Invalid number. Please enter again:");
        } else {
          break;
        }
      }
      if (number == 0) {
        System.out.println("You chose to poke the target in the eye!");
        attackWithNoWeapon(target);
      } else {
        WeaponImp weapon = weaponsCarried.get(number - 1);
        weaponsCarried.remove(weapon);
        System.out.printf("You chose %s to attack the target.%n",
            weapon.getName());
        if (canBeSeen()) {
          System.out.println("You can be seen by other player. No damage made.");
        } else {
          System.out.println("You cannot be seen by other player. Damage made.");
          target.healthDamage(weapon.getPower());
        }
      }
    }
  }


  public void attackWithWeapon(Weapon weapon, Target target) {
    weaponsCarried.remove(weapon);
    System.out.printf("You chose %s to attack the target.%n",
        weapon.getName());
    if (canBeSeen()) {
      System.out.println("You can be seen by other player. No damage made.");
    } else {
      System.out.println("You cannot be seen by other player. Damage made.");
      target.healthDamage(weapon.getPower());
    }
  }


  public void attackWithNoWeapon(Target target) {
    if (canBeSeen()) {
      //if can be seen, no damage made
      System.out.println("You can be seen by other player. No damage made.");
    } else {
      //if cannot be seen, damage made
      System.out.println("You cannot be seen by other player. Damage made.");
      target.healthDamage(1);
    }
  }

  /**
   * Moves the pet.
   *
   * @param pet         the pet
   * @param listOfRooms the list of rooms
   */
  public void moveThePet(Pet pet, List<Room> listOfRooms) {
    Room currentRoom = pet.getCurrentLocation();
    List<Room> neighbors = currentRoom.getNeighbors();
    List<Integer> roomNumberOfNeighbors = new ArrayList<>();

    for (Room room : neighbors) {
      roomNumberOfNeighbors.add(room.getRoomNumber());
    }

    System.out.println();
    System.out.printf("The cat is now in room %d, %s.%n", currentRoom.getRoomNumber(),
        currentRoom.getRoomName());
    System.out.println(
        "Where do you want to teleport the cat? Select a room number from the list: ");
    System.out.println(roomNumberOfNeighbors);

    Scanner scanner = new Scanner(System.in);
    int number;
    while (true) {
      while (!scanner.hasNextInt()) {
        System.out.println("Invalid input. Please enter a valid number:");
        scanner.next(); // consume the invalid token
      }
      number = scanner.nextInt();
      if (!roomNumberOfNeighbors.contains(number)) {
        System.out.println("Invalid number. Please enter again:");
      } else {
        break;
      }
    }
    Room nextWanderRoom = listOfRooms.get(number);
    System.out.printf("Next turn, Fortune the cat will wander to room %d, the %s.%n",
        nextWanderRoom.getRoomNumber(), nextWanderRoom.getRoomName());
    pet.updateLocation(nextWanderRoom);
    pet.setMoved();
  }
}

