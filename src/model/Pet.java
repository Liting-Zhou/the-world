package model;

public interface Pet {

  /**
   * Pet wanders in the world. Maybe follow depth-first traversal.
   */
  void wander();

  /**
   * Gets the name of this pet.
   *
   * @return The name of the pet.
   */
  String getName();

  /**
   * Gets the current location of this pet.
   *
   * @return The current location of the pet.
   */
  Room getCurrentLocation();

  /**
   * Updates the location of this pet.
   *
   * @param room The new location of this pet.
   */
  void updateLocation(Room room);
}
