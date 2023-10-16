package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link RoomInfo} class.
 */
public class RoomInfoTest {
  private RoomInfo thisRoom;
  private List<Weapon> weapons = new ArrayList<>();
  private List<RoomInfo> listOfRooms = new ArrayList<>();

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    weapons.add(new Weapon(3, "Test weapon", 1));

    thisRoom = new RoomInfo(1, 9, 2, 19, 4, "Test Room", weapons);
    listOfRooms.add(thisRoom);
  }

  @Test
  public void testGetRoomName() {
    String roomName = thisRoom.getRoomName();
    assertEquals("Test Room", roomName);
  }

  @Test
  public void testGetRoomNumber() {
    int roomNumber = thisRoom.getRoomNumber();
    assertEquals(1, roomNumber);
  }

  @Test
  public void testGetWeapons() {
    List<Weapon> roomWeapons = thisRoom.getWeapons();
    assertNotNull(roomWeapons);
    assertEquals(1, roomWeapons.size());
  }

  @Test
  public void testGetNeighbors() {
    //neighbors
    listOfRooms.add(new RoomInfo(2, 15, 0, 19, 2, "Neighbor Room", new ArrayList<>()));
    listOfRooms.add(new RoomInfo(3, 4, 4, 15, 7, "Neighbor Room", new ArrayList<>()));
    //not neighbor
    listOfRooms.add(new RoomInfo(4, 0, 0, 5, 2, "Neighbor Room", new ArrayList<>()));
    List<RoomInfo> neighbors = thisRoom.getNeighbors(listOfRooms);
    assertNotNull(neighbors);
    assertEquals(2, neighbors.size());
  }
}