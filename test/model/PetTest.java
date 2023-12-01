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
  private Room room1;
  private Room room2;
  private Room room3;

  /**
   * Sets up the test environment before each test case.
   */
  @Before
  public void setUp() {
    room1 = new RoomImp(1, 0, 0, 1, 1, "Test Room 1", null);
    room2 = new RoomImp(2, 1, 0, 2, 2, "Test Room 2", null);
    room3 = new RoomImp(3, 2, 0, 3, 3, "Test Room 3", null);
    List<Room> rooms = List.of(room1, room2, room3);
    room1.setNeighbors(List.of(room2));
    room2.setNeighbors(List.of(room1, room3));
    room3.setNeighbors(List.of(room2));
    //mansion = new Mansion("test", 20, 20, rooms);
    catTest = new Pet("cat test", room1);
  }

  @Test
  public void testStartingLocation() {
    assertEquals(room1, catTest.getCurrentLocation());
  }

  @Test
  public void testWander() {
    catTest.wander();
    assertEquals(room2, catTest.getCurrentLocation());
    catTest.wander();
    assertEquals(room3, catTest.getCurrentLocation());
    catTest.wander();
    assertEquals(room1, catTest.getCurrentLocation());
  }

  @Test
  public void testGetName() {
    assertEquals("cat test", catTest.getName());
  }

  @Test
  public void testGetCurrentLocation() {
    assertEquals(room1, catTest.getCurrentLocation());
  }

  @Test
  public void testUpdateLocation() {
    catTest.updateLocation(room2);
    assertEquals(room2, catTest.getCurrentLocation());
  }
}