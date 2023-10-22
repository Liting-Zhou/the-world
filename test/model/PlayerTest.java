package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link Player} class.
 */
public class PlayerTest{
  private Player player;
  private Room initialLocation;
  private Target target;
  private List<Player> players;
  private List<Room> listOfRooms;

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    // Create an initial room location for the player
    initialLocation = new RoomInfo(0, 0, 0, 2, 2, "Initial Room", new ArrayList<>());

    // Initialize the player object for testing
    player = new Player(0, 0, "Test Player", initialLocation, 3);

    // Initialize the target character for testing
    target = new Target("Test Target", 20, initialLocation);

    // Initialize a list of players for testing
    players = new ArrayList<>();
    players.add(player);

    // Initialize a list of rooms for testing
    listOfRooms = new ArrayList<>();
    listOfRooms.add(initialLocation);
  }

  @Test
  public void testGetIndexOfPlayer() {
    int indexOfPlayer = player.getIndexOfPlayer();
    assertEquals(0, indexOfPlayer);
  }

  @Test
  public void testGetTypeOfPlayer() {
    assertEquals(0, player.getTypeOfPlayer());
  }

  @Test
  public void testGetMaxNumberOfWeapons() {
    assertEquals(3, player.getMaxNumberOfWeapons());
  }

  @Test
  public void testSetIndexOfPlayer() {
    player.setIndexOfPlayer(1);
    int indexOfPlayer = player.getIndexOfPlayer();
    assertEquals(1, indexOfPlayer);
  }

  @Test
  public void testUpdateRoomInfo() {
    Room newLocation = new RoomInfo(1, 3, 0, 5, 2, "New Room", new ArrayList<>());
    player.updateRoomInfo(newLocation);
    Room updatedLocation = player.getCurrentLocation();
    Assert.assertEquals(newLocation, updatedLocation);
  }

}