package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains test cases for the {@link Target} class.
 */
public class TargetTest {
  private Target target;
  private RoomInfo firstRoom;
  private Mansion mansion;
  private List<Player> players;
  private List<RoomInfo> listOfRooms;

  /**
   * Sets up the test environment before each test case.
   */

  @Before
  public void setUp() {
    firstRoom = new RoomInfo(1, 0, 0, 2, 2, "First Room", new ArrayList<>());
    //mansion = new model.Mansion("Test model.Mansion", 5, 5, new ArrayList<>());
    target = new Target("Test model.Target", 20, firstRoom);

    players = new ArrayList<>();
    listOfRooms = new ArrayList<>();
    listOfRooms.add((RoomInfo) firstRoom);
  }

  @Test
  public void testGetHealth() {
    int health = target.getHealth();
    assertEquals(20, health);
  }

  @Test
  public void testSetHealth() {
    target.setHealth(2);
    int health = target.getHealth();
    assertEquals(18, health);
  }

  @Test
  public void testMove() {
    RoomInfo secondRoom = new RoomInfo(2, 3, 3, 5, 5, "Second Room", new ArrayList<>());
    RoomInfo thirdRoom = new RoomInfo(3, 3, 5, 8, 9, "Third Room", new ArrayList<>());
    players.add(new Player(1, 0, "model.Player 1", secondRoom));
    players.add(new Player(2, 0, "model.Player 2", thirdRoom));
    listOfRooms.add((RoomInfo) secondRoom);
    listOfRooms.add((RoomInfo) thirdRoom);
    mansion = new Mansion("Test model.Mansion", 10, 10, listOfRooms);

    Target updatedTarget = target.move(mansion, players, listOfRooms);
    assertNotNull(updatedTarget);
  }
}