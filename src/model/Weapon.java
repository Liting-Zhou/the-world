package model;

public interface Weapon {
  /**
   * Gets the power of the weapon.
   *
   * @return The power of the weapon.
   */
  int getPower();

  /**
   * Gets the name of the weapon.
   *
   * @return The name of the weapon.
   */
  String getName();

  /**
   * Gets the room number where the weapon belongs.
   *
   * @return The room number where the weapon belongs.
   */
  int getBelongRoomNumber();
}
