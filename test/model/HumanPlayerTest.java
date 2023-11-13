package model;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link HumanPlayer} class.
 */
public class HumanPlayerTest {
  private HumanPlayer humanPlayer;
  private Random random;

  private Mansion mansion;

  /**
   * Sets up the test environment before each test case.
   */
  @Before
  public void setUp() {
    WeaponImp weapon1 = new WeaponImp(5, "Axe", 0);
    WeaponImp weapon2 = new WeaponImp(4, "Sword", 1);
    WeaponImp weapon3 = new WeaponImp(3, "Knife", 0);
    List<WeaponImp> weapons1 = new ArrayList<>();
    weapons1.add(weapon1);
    weapons1.add(weapon3);
    List<WeaponImp> weapons2 = new ArrayList<>();
    weapons2.add(weapon2);
    Room room1 = new RoomInfo(0, 0, 0, 1, 1, "Test Room 1", weapons1);
    Room room2 = new RoomInfo(1, 1, 0, 2, 2, "Test Room 2", weapons2);
    humanPlayer = new HumanPlayer(1, 0, "Test Player", room1, 2);
    List<Room> rooms = List.of(room1, room2);
    mansion = new Mansion("test", 20, 20, rooms);

    random = new RandomNumber(1, 0, 1, 2);
  }

  @Test
  public void testMove() {
    int input = random.nextRandomInt(4);
    InputStream in = new ByteArrayInputStream(String.valueOf(input).getBytes());
    System.setIn(in);
    humanPlayer.move();
    assertEquals(1, humanPlayer.getCurrentLocation().getRoomNumber());
  }

  @Test
  public void testPickUpWeapon() {
    // Test picking up the first weapon
    int input1 = random.nextRandomInt(4);
    InputStream in1 = new ByteArrayInputStream(String.valueOf(input1).getBytes());
    System.setIn(in1);
    humanPlayer.pickUpWeapon();
    assertEquals(1, humanPlayer.weaponsCarried.size());

    // Test picking up the second weapon
    int input2 = random.nextRandomInt(4);
    InputStream in2 = new ByteArrayInputStream(String.valueOf(input2).getBytes());
    System.setIn(in2);
    humanPlayer.pickUpWeapon();
    assertEquals(2, humanPlayer.weaponsCarried.size());

    // Test trying to pick up a weapon when the player has reached the maximum limit
    humanPlayer.pickUpWeapon();
    assertEquals(2, humanPlayer.weaponsCarried.size());
  }

  @Test
  public void testAttackWhenCanNotBeSeen() {
    Pet cat = new Cat("cat", humanPlayer.getCurrentLocation());
    Target target = new Target("target", 10, humanPlayer.getCurrentLocation());
    mansion.setPet(cat);
    mansion.setTarget(target);

    // 1. Test attacking a target with weapon
    int input1 = random.nextRandomInt(4);
    InputStream in1 = new ByteArrayInputStream(String.valueOf(input1).getBytes());
    System.setIn(in1);
    humanPlayer.pickUpWeapon();

    //1.1 player has a weapon but still choose to poke the target in the eye
    int input2 = random.nextRandomInt(4);
    InputStream in2 = new ByteArrayInputStream(String.valueOf(input2).getBytes());
    System.setIn(in2);
    humanPlayer.attack();
    assertEquals(9, target.getHealth());
    assertEquals(1, humanPlayer.weaponsCarried.size());

    //1.2 player has more than a weapon and choose a weapon to attack
    int input3 = random.nextRandomInt(4);
    InputStream in3 = new ByteArrayInputStream(String.valueOf(input3).getBytes());
    System.setIn(in3);
    humanPlayer.pickUpWeapon();

    int input4 = random.nextRandomInt(4);
    InputStream in4 = new ByteArrayInputStream(String.valueOf(input4).getBytes());
    System.setIn(in4);
    humanPlayer.attack();
    assertEquals(6, target.getHealth());
    assertEquals(1, humanPlayer.weaponsCarried.size());

    // Test attacking a target with no weapon
    Room room3 = new RoomInfo(3, 2, 0, 3, 3, "Test Room 3", new ArrayList<>());
    HumanPlayer playerWithNoWeapon = new HumanPlayer(2, 0, "Test Player 2", room3, 2);
    target.setCurrentLocation(room3);
    playerWithNoWeapon.attack();
    assertEquals(5, target.getHealth());
  }

  @Test
  public void testAttackWhenCanBeSeen() {
    Room room3 = new RoomInfo(3, 2, 0, 3, 3, "Test Room 3", new ArrayList<>());
    Pet cat = new Cat("cat", room3);
    Target target = new Target("target", 10, humanPlayer.getCurrentLocation());
    mansion.setPet(cat);
    mansion.setTarget(target);

    Player otherPlayer =
        new HumanPlayer(2, 0, "Test Player 2", Mansion.getRoomInfoByRoomNumber(1), 2);
    mansion.addPlayer(otherPlayer);
    humanPlayer.attack();
    assertEquals(10, target.getHealth());
  }

  @Test
  public void testMoveThePet() {
    Pet cat = new Cat("cat", humanPlayer.getCurrentLocation());
    mansion.setPet(cat);
    int input = random.nextRandomInt(22);
    InputStream in = new ByteArrayInputStream(String.valueOf(input).getBytes());
    System.setIn(in);
    humanPlayer.moveThePet();
    assertEquals(1, cat.getCurrentLocation().getRoomNumber());
  }
}