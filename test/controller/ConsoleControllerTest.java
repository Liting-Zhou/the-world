package controller;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import model.MyWorld;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

/**
 * This class contains test cases for Controller.
 */
public class ConsoleControllerTest {
  /**
   * Initializes a rule to simulate input from the standard input stream.
   */
  @Rule
  public final TextFromStandardInputStream systemInMock
      = TextFromStandardInputStream.emptyStandardInputStream();
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private List<String> testConfigLines;
  private MyWorld mockWorld;
  private ConsoleController consoleController;

  /**
   * Sets up the test environment before each test case.
   */
  @Before
  public void setUp() {
    // create test configuration lines
    testConfigLines = new ArrayList<>();
    testConfigLines.add("10 10 Mansion A"); // mansion info
    testConfigLines.add("20 Target"); // target info
    testConfigLines.add("Peppa the Pig"); // target info
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
    mockWorld = new MyWorld(sr);
    consoleController = new ConsoleController(new StringReader("5\n"), new StringBuilder());

    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameWithInvalidMaxNumOfTurns() throws IllegalArgumentException, IOException {
    consoleController = new ConsoleController(new StringReader("-1\n"), new StringBuilder());
    consoleController.playGame(mockWorld);
  }

  @Test
  public void testPlayNextTurnCommand() throws IOException {
    systemInMock.provideLines("3", "Tom", "3", "3", "5", "3", "99");
    consoleController.playGame(mockWorld);
    assertEquals(2, mockWorld.getNumOfTurnsPlayed());
  }

  @Test
  public void testDisplayMapCommand() throws IOException {
    systemInMock.provideLines("2", "99");
    consoleController.playGame(mockWorld);
    String expectedOutput =
        "Game started!\n"
            + "***************\n"
            + "Options:\n"
            + "1. Display information about a specified room.\n"
            + "2. Generate the mansion_map.png.\n"
            + "3. Add a human-controlled player to the game.\n"
            + "4. Add a computer-controlled player to the game.\n"
            + "5. Play next turn.\n"
            + "6. Display a description of a specified player.\n"
            + "7. Display a description of the target.\n"
            + "99. Quit the game.\n"
            + "\nPlease choose an option (enter the corresponding number): \n"
            + "mansion_map.png saved successfully.\n\n"
            + "***************\nGame continues.\nOptions:\n"
            + "1. Display information about a specified room.\n"
            + "2. Generate the mansion_map.png.\n"
            + "3. Add a human-controlled player to the game.\n"
            + "4. Add a computer-controlled player to the game.\n"
            + "5. Play next turn.\n"
            + "6. Display a description of a specified player.\n"
            + "7. Display a description of the target.\n"
            + "99. Quit the game.\n"
            + "\nPlease choose an option (enter the corresponding number): \n"
            + "You chose to end the game. Bye!\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test
  public void testAddHumanPlayerCommand() throws IOException {
    systemInMock.provideLines("3", "Tom", "0", "3", "99");
    consoleController.playGame(mockWorld);
    assertEquals(1, mockWorld.getListOfPlayers().size());
    assertEquals("Tom", mockWorld.getListOfPlayers().get(0).getName());
  }

  @Test
  public void testAddComputerPlayerCommand() throws IOException {
    systemInMock.provideLines("4", "Jerry", "0", "3", "99");
    consoleController.playGame(mockWorld);
    assertEquals(1, mockWorld.getListOfPlayers().size());
    assertEquals("Jerry", mockWorld.getListOfPlayers().get(0).getName());
  }

  @Test
  public void testDisplayRoomInfoCommand() throws IOException {
    systemInMock.provideLines("1", "1", "99");
    consoleController.playGame(mockWorld);
    String expectedOutput = "Game started!\n"
        + "***************\nOptions:\n"
        + "1. Display information about a specified room.\n"
        + "2. Generate the mansion_map.png.\n"
        + "3. Add a human-controlled player to the game.\n"
        + "4. Add a computer-controlled player to the game.\n"
        + "5. Play next turn.\n"
        + "6. Display a description of a specified player.\n"
        + "7. Display a description of the target.\n"
        + "99. Quit the game.\n"
        + "\nPlease choose an option (enter the corresponding number): \n"
        + "\nThe mansion has the following rooms: \n"
        + "0: Room A\n1: Room B\n2: Room C\n3: Room D\n4: Room E\n5: Room F\n"
        + "6: Room G\n7: Room H\n8: Room I\n9: Room J\n10: Room K\n11: Room L\n"
        + "12: Room M\n13: Room N\n14: Room O\n15: Room P\n16: Room Q\n17: Room R\n"
        + "18: Room S\n19: Room T\n20: Room U\n21: Room V\n\n"
        + "Which room do you want to display? Please enter the room number (0-21): \n"
        + "\nRoom 1 information:\n-> There is no weapon in this room.\n"
        + "   Target is not here.\n   The cat is not here.\n   No player in this room.\n"
        + "\n***************\n"
        + "Game continues.\n"
        + "Options:\n"
        + "1. Display information about a specified room.\n"
        + "2. Generate the mansion_map.png.\n"
        + "3. Add a human-controlled player to the game.\n"
        + "4. Add a computer-controlled player to the game.\n"
        + "5. Play next turn.\n"
        + "6. Display a description of a specified player.\n"
        + "7. Display a description of the target.\n"
        + "99. Quit the game.\n"
        + "\nPlease choose an option (enter the corresponding number): \n"
        + "You chose to end the game. Bye!\n";
    assertEquals(expectedOutput, outContent.toString());

  }

  @Test
  public void testDisplayPlayerInfoCommand() throws IOException {
    systemInMock.provideLines("6", "Tom", "99");
    consoleController.playGame(mockWorld);
    String expectedOutput = "Game started!\n"
        + "***************\n"
        + "Options:\n"
        + "1. Display information about a specified room.\n"
        + "2. Generate the mansion_map.png.\n"
        + "3. Add a human-controlled player to the game.\n"
        + "4. Add a computer-controlled player to the game.\n"
        + "5. Play next turn.\n"
        + "6. Display a description of a specified player.\n"
        + "7. Display a description of the target.\n"
        + "99. Quit the game.\n"
        + "\nPlease choose an option (enter the corresponding number): \n\n"
        + "No player is added in the game yet.\n\n"
        + "***************\nGame continues.\nOptions:\n"
        + "1. Display information about a specified room.\n"
        + "2. Generate the mansion_map.png.\n"
        + "3. Add a human-controlled player to the game.\n"
        + "4. Add a computer-controlled player to the game.\n"
        + "5. Play next turn.\n"
        + "6. Display a description of a specified player.\n"
        + "7. Display a description of the target.\n"
        + "99. Quit the game.\n"
        + "\nPlease choose an option (enter the corresponding number): \n"
        + "Invalid input. Please enter a valid number:\n"
        + "You chose to end the game. Bye!\n";
    assertEquals(expectedOutput, outContent.toString());

  }

  @Test
  public void testDisplayTargetInfoCommand() throws IOException {
    systemInMock.provideLines("7", "99");
    consoleController.playGame(mockWorld);
    String expectedOutput = "Game started!\n"
        + "***************\n"
        + "Options:\n"
        + "1. Display information about a specified room.\n"
        + "2. Generate the mansion_map.png.\n"
        + "3. Add a human-controlled player to the game.\n"
        + "4. Add a computer-controlled player to the game.\n"
        + "5. Play next turn.\n"
        + "6. Display a description of a specified player.\n"
        + "7. Display a description of the target.\n"
        + "99. Quit the game.\n\n"
        + "Please choose an option (enter the corresponding number): \n"
        + "Target Information:\n"
        + "Name: Target\n"
        + "Current Location: Room 0, Room A.\n"
        + "Health: 20\n\n"
        + "***************\n"
        + "Game continues.\n"
        + "Options:\n"
        + "1. Display information about a specified room.\n"
        + "2. Generate the mansion_map.png.\n"
        + "3. Add a human-controlled player to the game.\n"
        + "4. Add a computer-controlled player to the game.\n"
        + "5. Play next turn.\n"
        + "6. Display a description of a specified player.\n"
        + "7. Display a description of the target.\n"
        + "99. Quit the game.\n\n"
        + "Please choose an option (enter the corresponding number): \n"
        + "You chose to end the game. Bye!\n";
    assertEquals(expectedOutput, outContent.toString());
  }

  /**
   * Tears down the test environment after tests.
   */
  @After
  public void tearDown() {
    System.setOut(System.out);
    System.setErr(System.err);
    mockWorld = null;
    testConfigLines = null;
  }

}