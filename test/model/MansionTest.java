package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    mansion = new Mansion("Test Mansion", 3, 6, rooms);
  }

  @Test
  public void testGetListOfRooms() {
    List<RoomInfo> roomList = mansion.getListOfRooms();
    assertNotNull(roomList);
    assertEquals(rooms.size(), roomList.size());
  }

  @Test
  public void testGetRoomInfoByRoomNumber() {
    Room roomA = mansion.getRoomInfoByRoomNumber(0);
    Room roomB = mansion.getRoomInfoByRoomNumber(1);

    assertNotNull(roomA);
    assertNotNull(roomB);

    Assert.assertEquals("Room A", roomA.getRoomName());
    Assert.assertEquals("Room B", roomB.getRoomName());
  }

  @Test
  public void testDisplayListOfRooms() {
    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));

    // Calling the displayListOfRooms method
    mansion.displayListOfRooms();

    // Expected output
    String expectedOutput = "The mansion has the following rooms: \n"
        + "0: Room A\n"
        + "1: Room B\n";

    // Checking if the output matches the expected output
    assertEquals(expectedOutput, outputStreamCaptor.toString());
  }

}