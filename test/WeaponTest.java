import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link Weapon} class.
 */
public class WeaponTest {
  private Weapon weapon1;
  private Weapon weapon2;
  private Weapon weapon3;
  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    weapon1 = new Weapon(10, "Weapon A", 1);
    weapon2 = new Weapon(10, "Weapon A", 1);
    weapon3 = new Weapon(20, "Weapon B", 2);
  }

  @Test
  public void testGetPower() {
    int power = weapon1.getPower();
    assertEquals(10, power);
  }

  @Test
  public void testGetName() {
    String name = weapon1.getName();
    assertEquals("Weapon A", name);
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
    assertEquals(weapon1.hashCode(), weapon2.hashCode());
    assertNotEquals(weapon1.hashCode(), weapon3.hashCode());
  }
}