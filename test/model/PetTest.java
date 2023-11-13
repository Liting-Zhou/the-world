package model;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Pet.
 **/
public class PetTest {
  private Pet catTest;
  private Room room;
  private Room room2;
  private Mansion mansion;

  /**
   * Sets up the test environment before each test case.
   */
  @Before
  public void setUp() {
    room = new RoomInfo(2, 0, 0, 1, 1, "Test Room 1", null);
    room2 = new RoomInfo(1, 1, 0, 2, 2, "Test Room 2", null);
    List<Room> rooms = List.of(room, room2);
    catTest = new Cat("cat test", room);
    mansion = new Mansion("test", 20, 20, rooms);
    mansion.setPet(catTest);
  }

  @Test
  public void testStartingLocation() {
    assertEquals(room, catTest.getCurrentLocation());
  }

  @Test
  public void testWander() {
    catTest.wander();
    assertEquals(room2, catTest.getCurrentLocation());
  }

  @Test
  public void testGetName() {
    assertEquals("cat test", catTest.getName());
  }

  @Test
  public void testGetCurrentLocation() {
    assertEquals(room, catTest.getCurrentLocation());
  }

  @Test
  public void testUpdateLocation() {
    catTest.updateLocation(room2);
    assertEquals(room2, catTest.getCurrentLocation());
  }
}