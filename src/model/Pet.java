package model;

/**
 * Represents a pet in the game.
 * The pet enters the game in the same space as the target character.
 * Any space that is occupied by the pet cannot be seen by its neighbors.
 * The pet can be moved to a specified space by players.
 */
public interface Pet {

  /**
   * Pet wanders in the world. Maybe follow depth-first traversal.
   * I use the BST constructed in lab08 to generate a post-order traversal.
   * [2 1 0 4 5 7 9 8 6 3 11 13 16 15 14 12 18 20 21 19 17 10]
   * Thus the pet would wander following this order.
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
