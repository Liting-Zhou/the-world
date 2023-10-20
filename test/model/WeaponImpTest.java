package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link WeaponImp} class.
 */
public class WeaponImpTest {
  private WeaponImp weapon1;
  private WeaponImp weapon2;
  private WeaponImp weapon3;

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    weapon1 = new WeaponImp(10, "WeaponImp A", 1);
    weapon2 = new WeaponImp(10, "WeaponImp A", 1);
    weapon3 = new WeaponImp(20, "WeaponImp B", 2);
  }

  @Test
  public void testGetPower() {
    int power = weapon1.getPower();
    assertEquals(10, power);
  }

  @Test
  public void testGetName() {
    String name = weapon1.getName();
    assertEquals("WeaponImp A", name);
  }

  @Test
  public void testGetBelongRoomNumber() {
    int roomNumber = weapon1.getBelongRoomNumber();
    assertEquals(1, roomNumber);
  }

  @Test
  public void testEquals() {
    assertTrue(weapon1.equals(weapon2));
    assertFalse(weapon1.equals(weapon3));
  }

  @Test
  public void testHashCode() {
    Assert.assertEquals(weapon1.hashCode(), weapon2.hashCode());
    Assert.assertNotEquals(weapon1.hashCode(), weapon3.hashCode());
  }
}