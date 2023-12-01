package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link Player} class.
 */
public class PlayerTest {
  private Player player;
  private Room initialLocation;
  private Room neighborLocation;
  private Target target;
  private List<Player> players;
  private List<Room> listOfRooms;

  /**
   * Sets up the test environment.
   */

  @Before
  public void setUp() {
    initialLocation = new RoomImp(0, 0, 0, 2, 2, "Initial Room", new ArrayList<>());
    neighborLocation = new RoomImp(1, 2, 0, 4, 4, "Neighbor Room", new ArrayList<>());

    player = new Player(0, 0, "Test Player", initialLocation, 3);

    target = new Target("Test Target", 20, initialLocation);

    players = new ArrayList<>();
    players.add(player);

    listOfRooms = new ArrayList<>();
    listOfRooms.add(initialLocation);
    listOfRooms.add(neighborLocation);

    initialLocation.setNeighbors(new ArrayList<>(List.of(neighborLocation)));
    neighborLocation.setNeighbors(new ArrayList<>(List.of(initialLocation)));
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
    Room newLocation = new RoomImp(1, 3, 0, 5, 2, "New Room", new ArrayList<>());
    player.updateLocation(newLocation);
    Room updatedLocation = player.getCurrentLocation();
    Assert.assertEquals(newLocation, updatedLocation);
  }

  @Test
  public void testCanBeSeen() {
    //test when the cat is in the same room as the player, while no players in neighboring room.
    Pet cat = new Cat("Test Pet", initialLocation);
    assertFalse(player.canBeSeen());

    // Test when there is no cat in the same room as the player,
    // while there are no players in neighboring room.
    cat.updateLocation(neighborLocation);
    assertFalse(player.canBeSeen());

    // Test when there is no cat in the same room as the player,
    // while there are players in neighboring room.
    Player player1 = new Player(1, 0, "Test Player neighbor",
        neighborLocation, 3);
    assertTrue(player.canBeSeen());

    // Test when the cat is in the same room as the player,
    // while there are players in neighboring room.
    cat.updateLocation(initialLocation);
    assertFalse(player.canBeSeen());
  }

  @Test
  public void testEquals() {
    Player player1 = new Player(0, 0, "Test Player", initialLocation, 3);
    assertEquals(player, player1);
  }

  @Test
  public void testHashCode() {
    Player player1 = new Player(0, 0, "Test Player", initialLocation, 3);
    assertEquals(player.hashCode(), player1.hashCode());
  }
}