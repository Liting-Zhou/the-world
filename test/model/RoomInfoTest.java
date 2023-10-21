package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link RoomInfo} class.
 */
public class RoomInfoTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private Room thisRoom;
  private List<WeaponImp> weapons = new ArrayList<>();
  private List<Room> listOfRooms = new ArrayList<>();

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    weapons.add(new WeaponImp(3, "Test weapon", 1));

    thisRoom = new RoomInfo(1, 9, 2, 19, 4, "Test Room", weapons);
    listOfRooms.add(thisRoom);

    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
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
    List<WeaponImp> roomWeapons = thisRoom.getWeapons();
    assertNotNull(roomWeapons);
    assertEquals(1, roomWeapons.size());
  }

  @Test
  public void testGetNeighbors() {
    //neighbors
    listOfRooms.add(new RoomInfo(2, 15, 0, 19, 2, "Neighbor Room", new ArrayList<>()));
    listOfRooms.add(new RoomInfo(3, 4, 4, 15, 7, "Neighbor Room", new ArrayList<>()));
    //not neighbor
    listOfRooms.add(new RoomInfo(4, 0, 0, 5, 2, "Not Neighbor Room", new ArrayList<>()));
    List<Room> neighbors = thisRoom.getNeighbors(listOfRooms);
    assertNotNull(neighbors);
    assertEquals(2, neighbors.size());
  }


  @Test
  public void testRemoveWeapon() {
    thisRoom.removeWeapon(weapons.get(0));
    assertEquals(0, thisRoom.getWeapons().size());
  }

  @Test
  public void testDisplayTarget() {
    Target target =
        new Target("testTarget", 10, new RoomInfo(3, 1, 2, 3, 4, "testRoom", new ArrayList<>()));
    // Simulate target not being in the room
    thisRoom.displayTarget(target);

    // Check if the output matches the expected value
    String expectedOutput = "Target is not here!\n";
    assertEquals(expectedOutput, outContent.toString());

    // Simulate target being in the room
    target.setCurrentLocation(thisRoom);
    thisRoom.displayTarget(target);

    String expectedOutputHere = "Target is not here!\n" + "Target is here!\n";
    assertEquals(expectedOutputHere, outContent.toString());

  }

  @Test
  public void testDisplayPlayers() {
    List<Player> players = new ArrayList<>();
    Player player1 = new Player(1,0,"jack",thisRoom,3);
    Player player2 = new Player(2,1,"rose",thisRoom,3);
    players.add(player1);
    players.add(player2);
    thisRoom.displayPlayers(players);

    String expectedOutputHere = "Player jack is here!\n" + "Player rose is here!\n";
    assertEquals(expectedOutputHere, outContent.toString());
  }

  @Test
  public void testDisplayWeapons() {
    thisRoom.displayWeapons();
    String expectedOutputHere = "WeaponImp Test weapon with power 3 is in this room.\n";
    assertEquals(expectedOutputHere, outContent.toString());
  }

  @Test
  public void testDisplayNeighbors() {
    thisRoom.displayNeighborsSimple();
    String expectedOutputHere = "This room has no neighboring room.\n";
    assertEquals(expectedOutputHere, outContent.toString());

  }

  @Test
  public void testEquals() {
    Room room1 = new RoomInfo(1, 0, 0, 0, 0, "Room1", new ArrayList<>());
    Room room2 = new RoomInfo(1, 0, 0, 0, 0, "Room1", new ArrayList<>());
    Room room3 = new RoomInfo(2, 0, 0, 0, 0, "Room2", new ArrayList<>());

    assertTrue(room1.equals(room2));
    assertFalse(room1.equals(room3));
  }

  @Test
  public void testHashCode() {
    Room room1 = new RoomInfo(1, 0, 0, 0, 0, "Room1", new ArrayList<>());
    Room room2 = new RoomInfo(1, 0, 0, 0, 0, "Room1", new ArrayList<>());
    Room room3 = new RoomInfo(2, 0, 0, 0, 0, "Room2", new ArrayList<>());

    assertEquals(room1.hashCode(), room2.hashCode());
    assertNotEquals(room1.hashCode(), room3.hashCode());
  }

  @After
  public void restoreStreams() {
    System.setOut(System.out);
    System.setErr(System.err);
  }
}