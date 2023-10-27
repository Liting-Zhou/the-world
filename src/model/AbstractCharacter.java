package model;

import java.util.Objects;

/**
 * An abstract class representing a character in the game.
 * This class serves as a base class for the `model.Target` and `model.Player` classes.
 */
abstract class AbstractCharacter implements Character {
  protected int maxNumberOfWeapons = 0;
  private String name;
  private Room currentLocation;

  /**
   * Gets the name of this character.
   *
   * @return The name of the character.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this character.
   *
   * @param name The name to set for the character.
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the current location of this character.
   *
   * @return The current location of the character.
   */
  @Override
  public Room getCurrentLocation() {
    return currentLocation;
  }

  /**
   * Sets the current location of this character.
   *
   * @param currentLocation The current location to set for the character.
   */
  @Override
  public void setCurrentLocation(Room currentLocation) {
    this.currentLocation = currentLocation;
  }
}
