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
 * This class contains test cases for the {@link RoomImp} class.
 */
public class RoomImpTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private Room thisRoom;
  private Room otherRoom;
  private Room anotherRoom;
  private List<WeaponImp> weapons = new ArrayList<>();
  private List<Room> listOfRooms = new ArrayList<>();
  private Player player;
  private Target target;
  private Pet cat;

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    weapons.add(new WeaponImp(3, "Test weapon", 1));

    thisRoom = new RoomImp(1, 9, 2, 19, 4, "Test Room this", weapons);
    otherRoom = new RoomImp(2, 1, 1, 3, 3, "Test Room other", new ArrayList<>());
    anotherRoom = new RoomImp(3, 3, 1, 5, 5, "Test Room 3", new ArrayList<>());
    listOfRooms.add(anotherRoom);
    listOfRooms.add(thisRoom);
    listOfRooms.add(otherRoom);
    otherRoom.setNeighbors(new ArrayList<>(List.of(anotherRoom)));
    anotherRoom.setNeighbors(new ArrayList<>(List.of(otherRoom)));
    player = new HumanPlayer(1, 0, "jack", otherRoom, 3);
    target = new Target("Test Target", 20, thisRoom);
    cat = new Pet("cat", thisRoom);
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
    listOfRooms.add(new RoomImp(4, 15, 0, 19, 2, "Neighbor Room", new ArrayList<>()));
    listOfRooms.add(new RoomImp(5, 4, 4, 15, 7, "Neighbor Room", new ArrayList<>()));
    //not neighbor
    listOfRooms.add(new RoomImp(6, 0, 0, 5, 2, "Not Neighbor Room", new ArrayList<>()));
    thisRoom.setNeighbors(new ArrayList<>(List.of(listOfRooms.get(3), listOfRooms.get(4))));
    List<Room> neighbors = thisRoom.getNeighbors();
    assertNotNull(neighbors);
    assertEquals(2, neighbors.size());
  }

  @Test
  public void testIsNeighbor(){
    assertTrue(anotherRoom.isNeighbor(otherRoom));
    assertFalse(otherRoom.isNeighbor(thisRoom));
  }

  @Test
  public void testIsTargetHere(){
    assertTrue(thisRoom.isTargetHere());
    assertFalse(otherRoom.isTargetHere());
  }

  @Test
  public void testIsPetHere(){
    assertTrue(thisRoom.isPetHere());
    assertFalse(otherRoom.isPetHere());
  }


  @Test
  public void testGetPlayersInTheRoom() {
    List<Player> playersInTheRoom = otherRoom.getPlayersInTheRoom();
    assertNotNull(playersInTheRoom);
    assertEquals(1, playersInTheRoom.size());
  }

  @Test
  public void testAddPlayer(){
    Player player = new HumanPlayer(1, 0, "jack", thisRoom, 3);
    otherRoom.addPlayer(player);
    assertEquals(2, otherRoom.getPlayersInTheRoom().size());
  }

  @Test
  public void testRemovePlayer(){
    Player player = new HumanPlayer(1, 0, "jack", otherRoom, 3);
    otherRoom.removePlayer(player);
    assertEquals(1, otherRoom.getPlayersInTheRoom().size());
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
    target.updateLocation(otherRoom);
    // Simulate target not being in the room
    String output1=thisRoom.displayTarget();

    // Check if the output matches the expected value
    String expectedOutput = "   Target is not here.\n";
    assertEquals(expectedOutput, output1);

    // Simulate target being in the room
    target.updateLocation(thisRoom);
    String output=thisRoom.displayTarget();

    String expectedOutputHere =
        "   Target is in room 1!\n";
    assertEquals(expectedOutputHere, output);

  }

  @Test
  public void testDisplayPet() {
    String output=thisRoom.displayPet();
    String expectedOutputHere = "   The cat is in room 1.\n";
    assertEquals(expectedOutputHere, output);
  }

  @Test
  public void testIsAnyPlayerHereWhenPlayerIsPresent() {
    player.updateLocation(thisRoom);
    assertTrue(thisRoom.isAnyPlayerHere());
  }

  @Test
  public void testIsAnyPlayerHereWhenPlayerIsNotPresent() {
    assertFalse(thisRoom.isAnyPlayerHere());
  }

  @Test
  public void testIsAnyOtherPlayerHere() {
    Player player2 = new Player(2, 1, "rose", thisRoom, 3);
    player.updateLocation(thisRoom);
    assertTrue(thisRoom.isAnyOtherPlayerHere(player));

    player2.updateLocation(otherRoom);
    assertFalse(thisRoom.isAnyOtherPlayerHere(player));
  }

  @Test
  public void testDisplayPlayers() {
    List<Player> players = new ArrayList<>();
    player.updateLocation(thisRoom);
    Player player2 = new Player(2, 1, "rose", thisRoom, 3);
    String output=thisRoom.displayPlayers();

    String expectedOutputHere = "   Player jack is in room 1.\n" + "   Player rose is in room 1.\n";
    assertEquals(expectedOutputHere, output);
  }

  @Test
  public void testDisplayWeapons() {
    String output=thisRoom.displayWeapons();
    String expectedOutputHere = "-> Weapon Test weapon with power 3 is in this room.\n";
    assertEquals(expectedOutputHere, output);
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
    String expectedoutput1 = "   3. Test Room 3\n";
    otherRoom.displayNeighborsSimple();
    assertEquals(expectedoutput1, outContent.toString());

    cat.updateLocation(anotherRoom);
    String expectedoutput2 = "   3. Test Room 3\n"
        + "   You can not see room 3, the Test Room 3\n";
    otherRoom.displayNeighborsSimple();
    assertEquals(expectedoutput2, outContent.toString());
  }

  @Test
  public void testEquals() {
    Room room1 = new RoomImp(1, 0, 0, 0, 0, "Room1", new ArrayList<>());
    Room room2 = new RoomImp(1, 0, 0, 0, 0, "Room1", new ArrayList<>());
    Room room3 = new RoomImp(2, 0, 0, 0, 0, "Room2", new ArrayList<>());

    assertTrue(room1.equals(room2));
    assertFalse(room1.equals(room3));
  }

  @Test
  public void testHashCode() {
    Room room1 = new RoomImp(1, 0, 0, 0, 0, "Room1", new ArrayList<>());
    Room room2 = new RoomImp(1, 0, 0, 0, 0, "Room1", new ArrayList<>());
    Room room3 = new RoomImp(2, 0, 0, 0, 0, "Room2", new ArrayList<>());

    assertEquals(room1.hashCode(), room2.hashCode());
    assertNotEquals(room1.hashCode(), room3.hashCode());
  }

  @After
  public void restoreStreams() {
    System.setOut(System.out);
    System.setErr(System.err);
  }
}