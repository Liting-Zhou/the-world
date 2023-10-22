package model;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
/**
 * This class contains test cases for the {@link ComputerPlayer} class.
 */
public class ComputerPlayerTest{
  private ComputerPlayer computerPlayer;
  private List<Room> rooms;
  private WeaponImp weapon1;
  private WeaponImp weapon2;
  private WeaponImp weapon3;

  @Before
  public void setUp() {
    weapon1 = new WeaponImp(5,"Axe", 0);
    weapon2 = new WeaponImp(4,"Sword", 1);
    weapon3 = new WeaponImp(3,"Knife", 0);
    List<WeaponImp> weapons1 = new ArrayList<>();
    weapons1.add(weapon1);
    weapons1.add(weapon3);
    List<WeaponImp> weapons2 = new ArrayList<>();
    weapons2.add(weapon2);
    Room room1 = new RoomInfo(0,0,0,1,1,"Test Room 1",weapons1);
    Room room2 = new RoomInfo(1,1,0,2,2,"Test Room 2",weapons2);
    rooms = new ArrayList<>();
    rooms.add(room1);
    rooms.add(room2);
    computerPlayer = new ComputerPlayer(2, 1, "Computer", room1, 2);
  }

  @Test
  public void testMove() {
    computerPlayer.move(rooms);
    assertTrue(rooms.contains(computerPlayer.getCurrentLocation()));
  }
  @Test
  public void testPickUpWeapon() {
    computerPlayer.pickUpWeapon();
    assertTrue(computerPlayer.weaponsCarried.contains(weapon1) || computerPlayer.weaponsCarried.contains(weapon2));
  }
}