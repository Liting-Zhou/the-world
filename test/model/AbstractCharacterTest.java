package model;

import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * This class contains test cases for the {@link AbstractCharacter} class.
 */
public class AbstractCharacterTest {
  private AbstractCharacter character;
  private Room room;

  @Before
  public void setUp() {
    room = new RoomInfo(0,0,0,1,1,"Test Room 1",null);
    character = new Player(0, 0, "Test Player", room, 3);
  }

  @Test
  public void testGetName() {
    assertEquals("Test Player", character.getName());
  }

  @Test
  public void testSetName() {
    character.setName("NewName");
    assertEquals("NewName", character.getName());
  }

  @Test
  public void testGetCurrentLocation() {
    assertEquals(room, character.getCurrentLocation());
  }

  @Test
  public void testSetCurrentLocation() {
    Room newRoom = new RoomInfo(1,4,4,5,5,"Test Room 2",null);
    character.setCurrentLocation(newRoom);
    assertEquals(newRoom, character.getCurrentLocation());
  }
  @Test
  public void testEquals() {
    character.setName("Character1");
    AbstractCharacter otherCharacter = new Player(0, 0, "Test Player", room, 3) {
    };
    otherCharacter.setName("Character1");
    assertTrue(character.equals(otherCharacter));
  }

  @Test
  public void testHashCode() {
    character.setName("TestCharacter");
    int expectedHashCode = Objects.hash("TestCharacter");
    assertEquals(expectedHashCode, character.hashCode());
  }
}