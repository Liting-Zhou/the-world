package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link Player} class.
 */
public class PlayerTest extends AbstractCharacter {
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
  public void testSetIndexOfPlayer() {
    player.setIndexOfPlayer(1);
    int indexOfPlayer = player.getIndexOfPlayer();
    assertEquals(1, indexOfPlayer);
  }

  @Test
  public void testGetTypeOfPlayer() {
    // Test for the getTypeOfPlayer() method
    int type = player.getTypeOfPlayer();
    // Check if the player type is either 0 or 1
    assertTrue(type == 0 || type == 1);
  }

  @Test
  public void testUpdateRoomInfo() {
    Room newLocation = new RoomInfo(1, 3, 0, 5, 2, "New Room", new ArrayList<>());
    player.updateRoomInfo(newLocation);
    Room updatedLocation = player.getCurrentLocation();
    Assert.assertEquals(newLocation, updatedLocation);
  }


  @Test
  public void testAction() {
    Room newLocation = new RoomInfo(1, 3, 0, 5, 2, "New Room", new ArrayList<>());
    Target updatedTarget = player.action("move", newLocation, target, players, listOfRooms);
    assertNotNull(updatedTarget);
  }


  @Test
  public void testAttack() {
    Room room = new RoomInfo(1, 3, 0, 5, 2, "New Room", new ArrayList<>());
    Target initialTarget = new Target("Test Target", 20, room);
    Target updatedTarget = player.attack(room, initialTarget);
    assertNotNull(updatedTarget);
  }

}