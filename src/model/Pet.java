package model;

public interface Pet {

  /**
   * Pet moves in the world. Maybe follow depth-first traversal.
   */
  void move();

  /**
   * Gets the name of this pet.
   *
   * @return The name of the pet.
   */
  String getName();

}
