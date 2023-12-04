package model;

import java.util.List;

/**
 * This class represents a computer player in the game.
 */
public class ComputerPlayer extends Player {
  private final RandomNumber random;

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
   * @param pet   the pet in the game
   * @param rooms the list of rooms in the game
   * @return the display message of the action
   **/
  public String randomAction(Pet pet, List<Room> rooms) {
    StringBuilder sb = new StringBuilder();
    int action = random.nextRandomInt(4);
    if (action == 0) {
      sb.append(String.format(
          "The random action of computer player %s is to move to a neighboring room.%n",
          this.getName()));
      sb.append(move());
    } else if (action == 1) {
      sb.append(String.format("The random action of computer player %s is to pick up a weapon.%n",
          this.getName()));
      sb.append(pickUpWeapon());
    } else if (action == 2) {
      sb.append(String.format("The random action of computer player %s is to look around.%n",
          this.getName()));
      sb.append(lookAround());
    } else {
      sb.append(String.format("The random action of computer player %s is to move the pet.%n",
          this.getName()));
      sb.append(moveThePet(pet, rooms));
    }
    return sb.toString();
  }

  /**
   * Computer Player randomly picks an action to perform when there is no weapon in the room.
   *
   * @param pet   the pet in the game
   * @param rooms the list of rooms in the game
   * @return the display message of the action
   **/
  public String randomActionNoWeapon(Pet pet, List<Room> rooms) {
    StringBuilder sb = new StringBuilder();
    int action = random.nextRandomInt(3);
    if (action == 0) {
      sb.append(String.format(
          "The random action of computer player %s is to move to a neighboring room.%n",
          this.getName()));
      sb.append(move());
    } else if (action == 1) {
      sb.append(String.format("The random action of computer player %s is to look around.%n",
          this.getName()));
      sb.append(lookAround());
    } else {
      sb.append(String.format("The random action of computer player %s is to move the pet.%n",
          this.getName()));
      sb.append(moveThePet(pet, rooms));
    }
    return sb.toString();
  }

  /**
   * Player moves to a specific room.
   *
   * @return the display message of the action
   **/
  public String move() {
    //randomly move to a neighboring space
    List<Room> neighbors = this.getCurrentLocation().getNeighbors();
    int index = random.nextRandomInt(neighbors.size());
    Room moveToRoom = neighbors.get(index);
    this.updateLocation(moveToRoom);
    return String.format("Player %s is now in room %d.%n", this.getName(),
        moveToRoom.getRoomNumber());
  }

  /**
   * Player picks up a weapon.
   *
   * @return the display message of the action
   */
  public String pickUpWeapon() {
    StringBuilder sb = new StringBuilder();
    //check if the player can carry more weapons
    if (weaponsCarried.size() == maxNumberOfWeapons) {
      sb.append(String.format("Player %s cannot carry more weapons.%n", this.getName()));
      return sb.toString();
    }
    //list the weapons in the room
    List<WeaponImp> weapons = this.getCurrentLocation().getWeapons();
    //see if there is any weapon in the room
    if (weapons.isEmpty()) {
      sb.append("But no weapons in this room.");
      //pick up the weapon
    } else if (weapons.size() == 1) {
      weaponsCarried.add(weapons.get(0));
      sb.append(String.format("Player %s picked up %s with power %d.%n", this.getName(),
          weapons.get(0).getName(),
          weapons.get(0).getPower()));
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapons.get(0));
    } else {
      //randomly pick a weapon
      int weaponNumber = random.nextRandomInt(weapons.size());
      WeaponImp weapon = weapons.get(weaponNumber);
      weaponsCarried.add(weapon);
      sb.append(String.format("Player %s picked up %s with power %d.%n", this.getName(),
          weapon.getName(), weapon.getPower()));
      //update the room information with weapons removed
      this.getCurrentLocation().removeWeapon(weapon);
    }
    return sb.toString();
  }

  /**
   * Attacks the target. Computer player only makes attempt to attack when it can not be seen.
   * Always use the weapon with the highest power.
   *
   * @param target The target to be attacked
   * @return the display message of the action
   */
  public String attack(Target target) {
    StringBuilder sb = new StringBuilder();
    sb.append(
        String.format("Computer player %s has chance to attack the target.%n", this.getName()));
    //if no weapon carried, poking the target in the eye and reduce 1 health
    if (weaponsCarried.isEmpty()) {
      sb.append(String.format(
          "%s has no weapon. Just poke the target in the eye!%nTarget gets 1 damage.%n",
          this.getName()));
      target.healthDamage(1);
    } else if (weaponsCarried.size() == 1) {
      //if one weapon carried, use the weapon to attack
      WeaponImp weapon = weaponsCarried.get(0);
      sb.append(String.format("%s uses %s to attack the target, target gets %d damage.%n",
          this.getName(), weapon.getName(), weapon.getPower()));
      target.healthDamage(weapon.getPower());
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
      sb.append(String.format("%s uses %s to attack the target, target gets %d damage.%n",
          this.getName(), weapon.getName(), weapon.getPower()));
      target.healthDamage(weapon.getPower());
      //remove the weapon from the game
      weaponsCarried.remove(weapon);
    }
    return sb.toString();
  }

  /**
   * Moves the pet randomly.
   *
   * @param pet   the pet in the game
   * @param rooms the list of rooms in the game
   * @return the display message of the action
   */
  public String moveThePet(Pet pet, List<Room> rooms) {
    StringBuilder sb = new StringBuilder();
    Room currentRoom = pet.getCurrentLocation();
    sb.append(
        String.format("The pet is currently in room %d, the %s.%n", currentRoom.getRoomNumber(),
            currentRoom.getRoomName()));

    Room nextWanderRoom = rooms.get(random.nextRandomInt(rooms.size()));
    sb.append(
        String.format("Next turn, Fortune the cat will wander to room %d, the %s, as you wish.%n",
            nextWanderRoom.getRoomNumber(), nextWanderRoom.getRoomName()));
    pet.updateLocation(nextWanderRoom);
    pet.setMoved();
    return sb.toString();
  }
}
