package model;

/**
 * An interface for Character, which represents a character in the game.
 */
public interface Character {
  /**
   * Gets the name of this character.
   *
   * @return The name of the character.
   */
  String getName();

  /**
   * Sets the name of this character.
   *
   * @param name The name to set for the character.
   */
  void setName(String name);

  /**
   * Gets the current location of this character.
   *
   * @return The current location of the character.
   */
  RoomInfo getCurrentLocation();

  /**
   * Sets the current location of this character.
   *
   * @param currentLocation The current location to set for the character.
   */
  void setCurrentLocation(RoomInfo currentLocation);
}
