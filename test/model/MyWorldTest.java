package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link MyWorld} class.
 */
public class MyWorldTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private MyWorld myWorld;
  private List<String> testConfigLines;
  private List<Room> rooms;
  private Target target;

  /**
   * Sets up the test environment before each test case.
   */
  @Before
  public void setUp() {
    // create test configuration lines
    testConfigLines = new ArrayList<>();
    testConfigLines.add("10 10 Mansion A"); // mansion info
    testConfigLines.add("20 Target"); // target info
    testConfigLines.add("Peppa the Pig"); // pet
    testConfigLines.add("22"); // room count
    testConfigLines.add("0 0 3 3 Room A"); // room 0
    testConfigLines.add("4 4 7 7 Room B"); // room 1
    testConfigLines.add("8 8 10 10 Room C"); // room 2
    testConfigLines.add("0 0 3 3 Room D"); // room 3
    testConfigLines.add("4 4 7 7 Room E"); // room 4
    testConfigLines.add("8 8 10 10 Room F"); // room 5
    testConfigLines.add("0 0 3 3 Room G"); // room 6
    testConfigLines.add("4 4 7 7 Room H"); // room 7
    testConfigLines.add("8 8 10 10 Room I"); // room 8
    testConfigLines.add("0 0 3 3 Room J"); // room 9
    testConfigLines.add("4 4 7 7 Room K"); // room 10
    testConfigLines.add("8 8 10 10 Room L"); // room 11
    testConfigLines.add("0 0 3 3 Room M"); // room 12
    testConfigLines.add("4 4 7 7 Room N"); // room 13
    testConfigLines.add("8 8 10 10 Room O"); // room 14
    testConfigLines.add("0 0 3 3 Room P"); // room 15
    testConfigLines.add("4 4 7 7 Room Q"); // room 16
    testConfigLines.add("8 8 10 10 Room R"); // room 17
    testConfigLines.add("0 0 3 3 Room S"); // room 18
    testConfigLines.add("4 4 7 7 Room T"); // room 19
    testConfigLines.add("8 8 10 10 Room U"); // room 20
    testConfigLines.add("0 0 3 3 Room V"); // room 21
    testConfigLines.add("3");
    testConfigLines.add("0 1 Knife");
    testConfigLines.add("2 3 Gun");
    testConfigLines.add("4 5 Poison");

    // create input stream
    StringBuilder sb = new StringBuilder();
    for (String line : testConfigLines) {
      sb.append(line).append("\n");
    }
    Readable sr = new StringReader(sb.toString());
    myWorld = new MyWorld(sr);
    rooms = myWorld.getListOfRooms();
    target = myWorld.getTarget();
    System.setOut(new PrintStream(outContent));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testMyWorldConstructorInvalidFile() throws IllegalArgumentException, IOException {
    new MyWorld(null);
  }

  /**
   * A test that verifies the world specification is read successfully.
   */
  @Test
  public void testWorldSpecificationRead() {
    // verify the list of rooms
    assertEquals(22, myWorld.getListOfRooms().size());

    // verify the list of weapons
    assertEquals("Knife", myWorld.getListOfRooms().get(0)
        .getWeapons().get(0).getName());

    // verify the target
    assertEquals("Target", target.getName());
    assertEquals(20, target.getHealth());
    assertEquals(0, target.getCurrentLocation().getRoomNumber());
  }

  @Test
  public void testAddPlayer() {
    myWorld.addHumanPlayer("Player1", 0, 1);
    myWorld.addComputerPlayer("Player2", 1, 2);
    assertEquals(0, myWorld.getListOfPlayers().get(0).getCurrentLocation().getRoomNumber());
    assertEquals(1, myWorld.getListOfPlayers().get(1).getCurrentLocation().getRoomNumber());
  }

  @Test
  public void testGetPlayers() {
    List<Player> expectedPlayers = new ArrayList<>();
    expectedPlayers.add(new HumanPlayer(1, 0, "Player1", rooms.get(0), 1));
    expectedPlayers.add(new ComputerPlayer(2, 1, "Player2", rooms.get(1), 2));
    expectedPlayers.add(new ComputerPlayer(3, 1, "Player3", rooms.get(2), 3));
    myWorld.addHumanPlayer("Player1", 0, 1);
    myWorld.addComputerPlayer("Player2", 1, 2);
    myWorld.addComputerPlayer("Player3", 2, 3);
    assertEquals(expectedPlayers, myWorld.getListOfPlayers());
  }


  @Test
  public void testSetMaxNumOfTurns() {
    myWorld.setMaxNumOfTurns(50);
    assertEquals(50, myWorld.maxNumOfTurns);
  }

  @Test
  public void testGetTarget() {
    assertNotNull(target);
    assertEquals("Target", target.getName());
  }

  @Test
  public void testGetNumOfTurnsPlayed() {
    int retrievedNumOfTurnsPlayed = myWorld.getNumOfTurnsPlayed();
    assertEquals(1, retrievedNumOfTurnsPlayed);
  }

  @Test
  public void testIsGameOver() {
    assertFalse(myWorld.isGameOver());
    target.healthDamage(30);
    assertTrue(myWorld.isGameOver());
  }

  @Test
  public void testGetWinner() {
    assertNull(myWorld.getWinner());
  }

  @Test
  public void testPlayNextTurn() {
    String inputString = "3";
    System.setIn(new ByteArrayInputStream(inputString.getBytes()));
    myWorld.playNextTurn();
    assertEquals(1, myWorld.getNumOfTurnsPlayed());
  }


  @Test
  public void testDisplayTargetInformation() {
    // Call the method
    myWorld.displayTargetInformation();
    String expectedOutput = "Target Information:\n"
        + "Name: Target\n" + "Current Location: Room 0, Room A.\n"
        + "Health: 20\n";

    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testDisplayPlayerInformation() {
    myWorld.addHumanPlayer("Player", 0, 3);
    Player player = myWorld.getListOfPlayers().get(0);
    player.weaponsCarried.add(new WeaponImp(5, "Axe", 0));

    String input = "Player";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    myWorld.displayPlayerInformation();
    String expectedOutput = "Human-controlled player Player is added to the game!\n\n"
        + "List of players: \n"
        + "Player\n\n"
        + "Which player do you want to display? Please enter the name: \n"
        + "Invalid input. Please enter a valid name:\n"
        + "\nInformation of player Player: \n"
        + "Player has/have 1 weapon(s):\n"
        + "(1) Axe with power 5\n"
        + "Maximum number of weapons can carry: 3\n"
        + "This is a human player.\n"
        + "Current Location: Room 0, the Room A.\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testDisplayRoomInformationWithWeapon() {
    String inputString = "0";
    System.setIn(new ByteArrayInputStream(inputString.getBytes()));
    myWorld.displayRoomInformation();
    String expectedOutput = "\nThe mansion has the following rooms: \n"
        + "0: Room A\n"
        + "1: Room B\n"
        + "2: Room C\n"
        + "3: Room D\n"
        + "4: Room E\n"
        + "5: Room F\n"
        + "6: Room G\n"
        + "7: Room H\n"
        + "8: Room I\n"
        + "9: Room J\n"
        + "10: Room K\n"
        + "11: Room L\n"
        + "12: Room M\n"
        + "13: Room N\n"
        + "14: Room O\n"
        + "15: Room P\n"
        + "16: Room Q\n"
        + "17: Room R\n"
        + "18: Room S\n"
        + "19: Room T\n"
        + "20: Room U\n"
        + "21: Room V\n"
        + "\nWhich room do you want to display? Please enter the room number (0-21): \n"
        + "\nRoom 0 information:\n"
        + "-> Weapon Knife with power 1 is in this room.\n"
        + "   Target is in room 0!\n"
        + "   The cat is in room 0.\n"
        + "   No player in this room.\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testDisplayRoomInformationWithoutWeapon() {
    String inputString = "1";
    System.setIn(new ByteArrayInputStream(inputString.getBytes()));
    myWorld.displayRoomInformation();
    String expectedOutput = "\nThe mansion has the following rooms: \n"
        + "0: Room A\n"
        + "1: Room B\n"
        + "2: Room C\n"
        + "3: Room D\n"
        + "4: Room E\n"
        + "5: Room F\n"
        + "6: Room G\n"
        + "7: Room H\n"
        + "8: Room I\n"
        + "9: Room J\n"
        + "10: Room K\n"
        + "11: Room L\n"
        + "12: Room M\n"
        + "13: Room N\n"
        + "14: Room O\n"
        + "15: Room P\n"
        + "16: Room Q\n"
        + "17: Room R\n"
        + "18: Room S\n"
        + "19: Room T\n"
        + "20: Room U\n"
        + "21: Room V\n"
        + "\nWhich room do you want to display? Please enter the room number (0-21): \n"
        + "\nRoom 1 information:\n"
        + "-> There is no weapon in this room.\n"
        + "   Target is not here.\n"
        + "   The cat is not here.\n"
        + "   No player in this room.\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testTargetStartingRoom() {
    assertEquals(0, target.getCurrentLocation().getRoomNumber());
  }

  /**
   * A test that verifies the target moves to room 0 when it is in room 21.
   */
  @Test
  public void testEndStart() {
    target.setCurrentLocation(rooms.get(21));
    target.move(rooms);
    assertEquals(0, target.getCurrentLocation().getRoomNumber());
  }

  @After
  public void tearDown() {
    myWorld = null;
    testConfigLines = null;
  }

}