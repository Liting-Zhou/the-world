package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * This class contains test cases for the {@link Mansion} class.
 */
public class MansionTest {
  private Mansion mansion;
  private List<RoomInfo> rooms;

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    // Create a sample list of rooms for testing
    rooms = new ArrayList<>();
    rooms.add(new RoomInfo(0, 0, 0, 2, 2, "Room A", new ArrayList<>()));
    rooms.add(new RoomInfo(1, 3, 0, 5, 2, "Room B", new ArrayList<>()));

    // Initialize the model.Mansion object for testing
    mansion = new Mansion("Test model.Mansion", 3, 6, rooms);
  }

  @Test
  public void testGetListOfRooms() {
    List<RoomInfo> roomList = mansion.getListOfRooms();
    assertNotNull(roomList);
    assertEquals(rooms.size(), roomList.size());
  }

  @Test
  public void testGetRoomInfoByRoomNumber() {
    RoomInfo roomA = mansion.getRoomInfoByRoomNumber(0);
    RoomInfo roomB = mansion.getRoomInfoByRoomNumber(1);

    assertNotNull(roomA);
    assertNotNull(roomB);

    Assert.assertEquals("Room A", roomA.getRoomName());
    Assert.assertEquals("Room B", roomB.getRoomName());
  }

}