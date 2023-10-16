package model;

import java.util.Objects;

/**
 * An abstract class representing a character in the game.
 * This class serves as a base class for the `model.Target` and `model.Player` classes.
 */
abstract class Character {
  private String name;
  private RoomInfo currentLocation;


  /**
   * Gets the name of this character.
   *
   * @return The name of the character.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this character.
   *
   * @param name The name to set for the character.
   */
  protected void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the current location of this character.
   *
   * @return The current location of the character.
   */
  protected RoomInfo getCurrentLocation() {
    return currentLocation;
  }

  /**
   * Sets the current location of this character.
   *
   * @param currentLocation The current location to set for the character.
   */
  protected void setCurrentLocation(RoomInfo currentLocation) {
    this.currentLocation = currentLocation;
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
    if (!(o instanceof Character)) {
      return false;
    }
    // The successful instanceof check means our cast will succeed:
    Character that = (Character) o;
    return Objects.equals(getName(), that.getName());
  }

  /**
   * Returns a hash code value for this character.
   *
   * @return The hash code value for this character.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }
}
