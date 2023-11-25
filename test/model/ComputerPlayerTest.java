package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link ComputerPlayer} class.
 */
public class ComputerPlayerTest {
  private ComputerPlayer computerPlayer;
  private List<Room> listOfRooms;
  private WeaponImp weapon1;
  private WeaponImp weapon2;
  private WeaponImp weapon3;

  /**
   * Sets up the test environment before each test case.
   */
  @Before
  public void setUp() {
    weapon1 = new WeaponImp(5, "Axe", 0);
    weapon2 = new WeaponImp(4, "Sword", 1);
    weapon3 = new WeaponImp(3, "Knife", 0);
    List<WeaponImp> weapons1 = new ArrayList<>();
    weapons1.add(weapon1);
    weapons1.add(weapon3);
    List<WeaponImp> weapons2 = new ArrayList<>();
    weapons2.add(weapon2);
    Room room1 = new RoomImp(1, 0, 0, 1, 1, "Test Room 1", weapons1);
    Room room2 = new RoomImp(2, 1, 0, 2, 2, "Test Room 2", weapons2);
    listOfRooms = new ArrayList<>();
    listOfRooms.add(room1);
    listOfRooms.add(room2);
    room1.setNeighbors(new ArrayList<>(List.of(room2)));
    room2.setNeighbors(new ArrayList<>(List.of(room1)));
    computerPlayer = new ComputerPlayer(2, 1, "Computer", room1, 2);
  }

  @Test
  public void testMove() {
    Room previousRoom = computerPlayer.getCurrentLocation();
    computerPlayer.move();
    assertTrue(listOfRooms.contains(computerPlayer.getCurrentLocation()));
    assertNotEquals(previousRoom, computerPlayer.getCurrentLocation());
  }

  @Test
  public void testPickUpWeapon() {
    computerPlayer.pickUpWeapon();
    assertTrue(computerPlayer.weaponsCarried.contains(weapon1)
        || computerPlayer.weaponsCarried.contains(weapon3));
  }

  @Test
  public void testAttack() {
    Target target = new Target("target", 10, computerPlayer.getCurrentLocation());
    //test when computer player has no weapon
    computerPlayer.attack(target);
    assertEquals(9, target.getHealth());

    //test when computer player has one weapon
    computerPlayer.weaponsCarried.add(weapon1);
    computerPlayer.attack(target);
    assertEquals(4, target.getHealth());
    assertEquals(0, computerPlayer.weaponsCarried.size());

    //test when computer player has multiple weapons, choose the weapon with the highest power
    computerPlayer.weaponsCarried.add(weapon2);
    computerPlayer.weaponsCarried.add(weapon3);
    computerPlayer.attack(target);
    assertEquals(0, target.getHealth());
    assertEquals(1, computerPlayer.weaponsCarried.size());
  }

  @Test
  public void testMoveThePet() {
    Pet cat = new Cat("pet", computerPlayer.getCurrentLocation());
    computerPlayer.moveThePet(cat);
    assertNotEquals(computerPlayer.getCurrentLocation(), cat.getCurrentLocation());
  }
}