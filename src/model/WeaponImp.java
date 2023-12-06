package model;

import java.util.Objects;

/**
 * Represents a weapon in the game.
 */
public final class WeaponImp implements Weapon {
  private final int power;
  private final String name;
  private final int belongRoomNumber;

  /**
   * Default Constructor.
   *
   * @param power            The power of the weapon.
   * @param name             The name of the weapon.
   * @param belongRoomNumber The room number where the weapon belongs.
   */
  public WeaponImp(int power, String name, int belongRoomNumber) {
    this.power = power;
    this.name = name;
    this.belongRoomNumber = belongRoomNumber;
  }

  /**
   * A constructor to clone a Weapon.
   *
   * @param weapon The weapon to be cloned.
   */
  public WeaponImp(Weapon weapon) {
    this.power = weapon.getPower();
    this.name = weapon.getName();
    this.belongRoomNumber = weapon.getBelongRoomNumber();
  }

  /**
   * Gets the power of the weapon.
   *
   * @return The power of the weapon.
   */
  @Override
  public int getPower() {
    return power;
  }

  /**
   * Gets the name of the weapon.
   *
   * @return The name of the weapon.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the room number where the weapon belongs.
   *
   * @return The room number where the weapon belongs.
   */
  @Override
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
    if (!(o instanceof WeaponImp)) {
      return false;
    }
    WeaponImp weapon = (WeaponImp) o;
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
