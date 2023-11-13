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
  private Room otherRoom;
  private Room anotherRoom;
  private List<WeaponImp> weapons = new ArrayList<>();
  private List<Room> listOfRooms = new ArrayList<>();
  private Mansion mansion;
  private Player player;

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    weapons.add(new WeaponImp(3, "Test weapon", 1));

    thisRoom = new RoomInfo(1, 9, 2, 19, 4, "Test Room this", weapons);
    otherRoom = new RoomInfo(2, 1, 1, 3, 3, "Test Room other", new ArrayList<>());
    anotherRoom = new RoomInfo(3, 3, 1, 5, 5, "Test Room 3", new ArrayList<>());
    listOfRooms.add(anotherRoom);
    listOfRooms.add(thisRoom);
    listOfRooms.add(otherRoom);
    player = new HumanPlayer(1, 0, "jack", otherRoom, 3);
    Target target = new Target("Test Target", 20, thisRoom);

    mansion = new Mansion("test", 20, 20, listOfRooms);
    mansion.addPlayer(player);
    mansion.setTarget(target);
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @Test
  public void testGetRoomName() {
    String roomName = thisRoom.getRoomName();
    assertEquals("Test Room this", roomName);
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
    List<Room> neighbors = thisRoom.getNeighbors();
    assertNotNull(neighbors);
    assertEquals(2, neighbors.size());
  }


  @Test
  public void testRemoveWeapon() {
    thisRoom.removeWeapon(weapons.get(0));
    assertEquals(0, thisRoom.getWeapons().size());
  }


  @Test
  public void testIsTargetHereWhenTargetIsPresent() {
    assertTrue(thisRoom.isTargetHere());
  }

  @Test
  public void testIsTargetHereWhenTargetIsNotPresent() {
    assertFalse(otherRoom.isTargetHere());
  }

  @Test
  public void testDisplayTarget() {
    Target target = mansion.getTarget();
    target.setCurrentLocation(otherRoom);
    // Simulate target not being in the room
    thisRoom.displayTarget();

    // Check if the output matches the expected value
    String expectedOutput = "   Target is not here.\n";
    assertEquals(expectedOutput, outContent.toString());

    // Simulate target being in the room
    target.setCurrentLocation(thisRoom);
    thisRoom.displayTarget();

    String expectedOutputHere =
        "   Target is not here.\n" + "                     Target is in room 1!\n";
    assertEquals(expectedOutputHere, outContent.toString());

  }

  @Test
  public void testIsPetHere() {
    Pet cat = new Cat("cat", thisRoom);
    mansion.setPet(cat);
    assertTrue(thisRoom.isPetHere());
  }

  @Test
  public void testDisplayPet() {
    Pet cat = new Cat("cat", thisRoom);
    mansion.setPet(cat);
    thisRoom.displayPet();
    String expectedOutputHere = "   cat the cat is in room 1.\n";
    assertEquals(expectedOutputHere, outContent.toString());
  }

  @Test
  public void testIsAnyPlayerHereWhenPlayerIsPresent() {
    player.setCurrentLocation(thisRoom);
    assertTrue(thisRoom.isAnyPlayerHere());
  }

  @Test
  public void testIsAnyPlayerHereWhenPlayerIsNotPresent() {
    assertFalse(thisRoom.isAnyPlayerHere());
  }

  @Test
  public void testIsAnyOtherPlayerHere() {
    Player player2 = new Player(2, 1, "rose", thisRoom, 3);
    mansion.addPlayer(player2);
    player.setCurrentLocation(thisRoom);
    assertTrue(thisRoom.isAnyOtherPlayerHere(player));

    player2.setCurrentLocation(otherRoom);
    assertFalse(thisRoom.isAnyOtherPlayerHere(player));
  }

  @Test
  public void testDisplayPlayers() {
    List<Player> players = new ArrayList<>();
    player.setCurrentLocation(thisRoom);
    Player player2 = new Player(2, 1, "rose", thisRoom, 3);
    mansion.addPlayer(player2);
    players = mansion.getListOfPlayers();
    thisRoom.displayPlayers();

    String expectedOutputHere = "   Player jack is in room 1.\n" + "   Player rose is in room 1.\n";
    assertEquals(expectedOutputHere, outContent.toString());
  }

  @Test
  public void testDisplayWeapons() {
    thisRoom.displayWeapons();
    String expectedOutputHere = "-> Weapon Test weapon with power 3 is in this room.\n";
    assertEquals(expectedOutputHere, outContent.toString());
  }

  @Test
  public void testDisplayNeighbors() {
    thisRoom.displayNeighborsSimple();
    String expectedOutputHere = "This room has no neighboring room.\n";
    assertEquals(expectedOutputHere, outContent.toString());
  }

  /**
   * Test of Displaying neighbors when pet is in one of the neighboring rooms.
   */
  @Test
  public void testDisplayNeighborsWithPet() {
    Pet cat = new Cat("cat", thisRoom);
    mansion.setPet(cat);

    String expectedoutput1 = "   3. Test Room 3\n";
    otherRoom.displayNeighborsSimple();
    assertEquals(expectedoutput1, outContent.toString());

    cat.updateLocation(anotherRoom);
    mansion.setPet(cat);
    String expectedoutput2 = "   3. Test Room 3\n"
        + "   You can not see room 3, the Test Room 3\n";
    otherRoom.displayNeighborsSimple();
    assertEquals(expectedoutput2, outContent.toString());
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