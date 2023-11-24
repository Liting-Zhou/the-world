package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
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
  private List<Room> rooms;
  private Target target;

  /**
   * Sets up the test environment before each test case.
   */

//  @Before
//  public void setUp() {
//    // Create a sample list of rooms for testing
//    rooms = new ArrayList<>();
//    rooms.add(new RoomImp(0, 0, 0, 2, 2, "Room A", new ArrayList<>()));
//    rooms.add(new RoomImp(1, 3, 0, 5, 2, "Room B", new ArrayList<>()));
//    target = new Target("Test Target", 20, rooms.get(0));
//    Player player = new HumanPlayer(0, 0, "Test Player", rooms.get(0), 3);
//
//    // Initialize the model.Mansion object for testing
//    mansion = new Mansion("Test Mansion", 3, 6, rooms);
//    mansion.addPlayer(player);
//    mansion.setTarget(target);
//  }

//  @Test
//  public void testGetRoomInfoByRoomNumber() {
//    Room roomA = mansion.getRoomByRoomNumber(0);
//    Room roomB = mansion.getRoomByRoomNumber(1);
//
//    assertNotNull(roomA);
//    assertNotNull(roomB);
//
//    Assert.assertEquals("Room A", roomA.getRoomName());
//    Assert.assertEquals("Room B", roomB.getRoomName());
//  }
//
//  @Test
//  public void testGetListOfPlayers() {
//    assertEquals("Test Player", mansion.getListOfPlayers().get(0).getName());
//  }
//
//  @Test
//  public void testGetTarget() {
//    assertEquals("Test Target", mansion.getTarget().getName());
//  }
//
//  @Test
//  public void testGetListOfRooms() {
//    List<Room> roomList = mansion.getListOfRooms();
//    assertNotNull(roomList);
//    assertEquals(rooms.size(), roomList.size());
//  }
//
//  @Test
//  public void testAddPlayer() {
//    Player player = new ComputerPlayer(1, 1, "Test Another Player", rooms.get(1), 2);
//    mansion.addPlayer(player);
//    assertEquals(2, mansion.getListOfPlayers().size());
//  }
//
//  @Test
//  public void testGetBufferedImage() {
//    BufferedImage image = mansion.getBufferedImage();
//    assertNotNull(image);
//    assertEquals(240, image.getWidth());
//    assertEquals(120, image.getHeight());
//  }
//
//  @Test
//  public void testDisplayListOfRooms() {
//    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//    System.setOut(new PrintStream(outputStreamCaptor));
//
//    // Calling the displayListOfRooms method
//    mansion.displayListOfRooms();
//
//    // Expected output
//    String expectedOutput = "The mansion has the following rooms: \n"
//        + "0: Room A\n"
//        + "1: Room B\n";
//
//    // Checking if the output matches the expected output
//    assertEquals(expectedOutput, outputStreamCaptor.toString());
//  }

}