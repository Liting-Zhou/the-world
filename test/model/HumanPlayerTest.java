package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.Assert.*;

public class HumanPlayerTest{
  private HumanPlayer humanPlayer;
  private Random random;

  @Before
  public void setUp() {
    WeaponImp weapon1 = new WeaponImp(5,"Axe", 0);
    WeaponImp weapon2 = new WeaponImp(4,"Sword", 1);
    WeaponImp weapon3 = new WeaponImp(3,"Knife", 0);
    List<WeaponImp> weapons1 = new ArrayList<>();
    weapons1.add(weapon1);
    weapons1.add(weapon3);
    List<WeaponImp> weapons2 = new ArrayList<>();
    weapons2.add(weapon2);
    Room room1 = new RoomInfo(0,0,0,1,1,"Test Room 1",weapons1);
    Room room2 = new RoomInfo(1,1,1,2,2,"Test Room 2",weapons2);
    humanPlayer = new HumanPlayer(1, 0, "Test Player", room1, 2);
    List<Room> rooms = List.of(room1, room2);
    Mansion mansion = new Mansion("Test Mansion", 3, 6, rooms);
    random =new RandomNumber(1,0,1);

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
}