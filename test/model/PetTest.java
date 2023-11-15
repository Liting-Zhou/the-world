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
  private Room room3;
  private Mansion mansion;

  /**
   * Sets up the test environment before each test case.
   */
  @Before
  public void setUp() {
    room = new RoomImp(2, 0, 0, 1, 1, "Test Room 1", null);
    room2 = new RoomImp(1, 1, 0, 2, 2, "Test Room 2", null);
    room3 = new RoomImp(3, 2, 0, 3, 3, "Test Room 3", null);
    List<Room> rooms = List.of(room, room2, room3);
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