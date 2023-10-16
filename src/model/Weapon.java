package model;

import java.util.Objects;

/**
 * Represents a weapon in the game "The model.World."
 */
public final class Weapon {
  private final int power;
  private final String name;
  private final int belongRoomNumber;

  /**
   * Constructs a new model.Weapon object.
   *
   * @param power            The power of the weapon.
   * @param name             The name of the weapon.
   * @param belongRoomNumber The room number where the weapon belongs.
   */
  public Weapon(int power, String name, int belongRoomNumber) {
    this.power = power;
    this.name = name;
    this.belongRoomNumber = belongRoomNumber;
  }

  /**
   * Gets the power of the weapon.
   *
   * @return The power of the weapon.
   */
  public int getPower() {
    return power;
  }

  /**
   * Gets the name of the weapon.
   *
   * @return The name of the weapon.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the room number where the weapon belongs.
   *
   * @return The room number where the weapon belongs.
   */
  public int getBelongRoomNumber() {
    return belongRoomNumber;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o The object to compare for equality.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Weapon)) {
      return false;
    }
    Weapon weapon = (Weapon) o;
    return getPower() == weapon.getPower() && Objects.equals(getName(), weapon.getName());
  }

  /**
   * Returns a hash code value for the weapon.
   *
   * @return The hash code value for the weapon.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getPower(), getName());
  }

}
