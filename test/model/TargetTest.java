package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link Target} class.
 */
public class TargetTest {
  private Target target;
  private Room firstRoom;
  private List<Player> players;
  private List<Room> listOfRooms;

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    firstRoom = new RoomImp(0, 0, 0, 2, 2, "First Room", new ArrayList<>());
    target = new Target("Test Target", 20, firstRoom);

    players = new ArrayList<>();
    listOfRooms = new ArrayList<>();
    listOfRooms.add(firstRoom);
  }

  @Test
  public void testGetName() {
    String name = target.getName();
    assertEquals("Test Target", name);
  }

  @Test
  public void testGetHealth() {
    int health = target.getHealth();
    assertEquals(20, health);
  }

  @Test
  public void testSetHealth() {
    target.healthDamage(2);
    int health = target.getHealth();
    assertEquals(18, health);
  }

  @Test
  public void testMove() {
    Room secondRoom = new RoomImp(1, 3, 3, 5, 5, "Second Room", new ArrayList<>());
    Room thirdRoom = new RoomImp(2, 3, 5, 8, 9, "Third Room", new ArrayList<>());
    players.add(new Player(1, 0, "Player 1", secondRoom, 3));
    players.add(new Player(2, 0, "Player 2", thirdRoom, 3));
    listOfRooms.add(secondRoom);
    listOfRooms.add(thirdRoom);

    target.move(listOfRooms);
    assertEquals(1, target.getCurrentLocation().getRoomNumber());
  }
}