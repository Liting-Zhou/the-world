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
   **/
  public void randomAction() {
    int action = random.nextRandomInt(4);
    if (action == 0) {
      System.out.println(
          String.format("The random action of computer player %s is to move to a neighboring room.",
              this.getName()));
      move();
    } else if (action == 1) {
      System.out.println(
          String.format("The random action of computer player %s is to pick up a weapon.",
              this.getName()));
      pickUpWeapon();
    } else if (action == 2) {
      System.out.println(String.format("The random action of computer player %s is to look around.",
          this.getName()));
      lookAround();
    } else {
      System.out.println(
          String.format("The random action of computer player %s is to move the pet.",
              this.getName()));
      moveThePet();
    }
  }

  /**
   * Computer Player randomly picks an action to perform when there is no weapon in the room.
   **/
  public void randomActionNoWeapon() {
    int action = random.nextRandomInt(3);
    if (action == 0) {
      System.out.println(
          String.format("The random action of computer player %s is to move to a neighboring room.",
              this.getName()));
      move();
    } else if (action == 1) {
      System.out.println(String.format("The random action of computer player %s is to look around.",
          this.getName()));
      lookAround();
    } else {
      System.out.println(
          String.format("The random action of computer player %s is to move the pet.",
              this.getName()));
      moveThePet();
    }
  }

  /**
   * Player moves to a specific room.
   **/
  public void move() {
    //randomly move to a neighboring space
    List<Room> neighbors = this.getCurrentLocation().getNeighbors();
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

  /**
   * Attacks the target. Computer player only makes attempt to attack when it can not be seen.
   * Always use the weapon with the highest power.
   */
  public void attack() {
    //if no weapon carried, poking the target in the eye and reduce 1 health
    if (weaponsCarried.isEmpty()) {
      System.out.println(
          String.format("Computer player %s has no weapon. Just poke the target in the eye!",
              this.getName()));
      Mansion.getTarget().healthDamage(1);
    } else if (weaponsCarried.size() == 1) {
      //if one weapon carried, use the weapon to attack
      WeaponImp weapon = weaponsCarried.get(0);
      System.out.println(
          String.format("Computer player %s uses %s to attack the target, target gets %d damage.",
              this.getName(), weapon.getName(), weapon.getPower()));
      Mansion.getTarget().healthDamage(weapon.getPower());
      //remove the weapon from the game
      weaponsCarried.remove(weapon);
    } else {
      //if more than one weapon carried, use the weapon with the highest power to attack
      int maxPower = 0;
      WeaponImp weapon = null;
      for (WeaponImp w : weaponsCarried) {
        if (w.getPower() > maxPower) {
          maxPower = w.getPower();
          weapon = w;
        }
      }
      System.out.println(
          String.format("Computer player %s uses %s to attack the target, target gets %d damage.",
              this.getName(), weapon.getName(), weapon.getPower()));
      Mansion.getTarget().healthDamage(weapon.getPower());
      //remove the weapon from the game
      weaponsCarried.remove(weapon);
    }
  }

  /**
   * Moves the pet randomly.
   */
  public void moveThePet() {
    Pet pet = Mansion.getPet();
    System.out.println(
        String.format("The cat is now in room %d, %s", pet.getCurrentLocation().getRoomNumber(),
            pet.getCurrentLocation().getRoomName()));
    System.out.println("Where do you want to teleport the cat? Enter the room number: ");

    int number;
    while (true) {
      number = random.nextRandomInt(22);
      if (number != pet.getCurrentLocation().getRoomNumber()) {
        break;
      }
    }
    pet.updateLocation(Mansion.getRoomInfoByRoomNumber(number));
    Mansion.setFlag(1);
  }
}
