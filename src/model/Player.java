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
  protected List<WeaponImp> weaponsCarried;

  /**
   * Constructs a new Player object.
   *
   * @param indexOfPlayer   The index of the player.
   * @param typeOfPlayer    The type of the player. 0 for human, 1 for computer.
   * @param name            The name of the player.
   * @param currentLocation The initial current location of the player.
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
   * Player look around. Displays all information about neighboring rooms.
   */
  public void lookAround() {
    displayPlayerInformation();
  }

  /**
   * Displays the player information, including name, weapon carried, current location,
   * and neighbors.
   */
  public void displayPlayerInformation() {
    System.out.println("--------------");
    System.out.println(
        String.format("Your current Location: Room %d", getCurrentLocation().getRoomNumber()));
    getCurrentLocation().displayNeighborsAllInfo();
  }

  /**
   * Displays the weapon carried by the player.
   */
  public void displayWeaponInformation() {
    if (weaponsCarried.isEmpty()) {
      System.out.println(" has/have no weapon.");
    } else {
      System.out.println(String.format(" has/have %d weapon(s):", weaponsCarried.size()));
      for (WeaponImp weapon : weaponsCarried) {
        System.out.println(String.format("%s with power %s", weapon.getName(), weapon.getPower()));
      }
    }
  }
}

