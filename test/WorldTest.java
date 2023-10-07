import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

//TODO

/**
 * This class contains test cases for the {@link World} class.
 */
public class WorldTest {
  private World world;

  @Before
  public void setUp() {
    List<String> playerNames = new ArrayList<>();
    playerNames.add("Player1");
    playerNames.add("Player2");
    playerNames.add("Player3");

    try {
      Readable conFile = new BufferedReader(new FileReader("./res/mansion.txt"));
      world = new World(conFile, playerNames);
    } catch (IOException e) {
      System.err.println("Error reading configuration file: " + e.getMessage());
      world = null;
    }
  }

  @Test
  public void testGetTarget() {
    assertNotNull(world.getTarget());
    assertEquals("Doctor Lucky", world.getTarget().getName());
  }

  @Test
  public void testGetPlayers() {
    assertNotNull(world.getPlayers());
    assertEquals(3, world.getPlayers().size());
    assertEquals("Player1", world.getPlayers().get(0).getName());
    assertEquals("Player2", world.getPlayers().get(1).getName());
    assertEquals("Player3", world.getPlayers().get(2).getName());
  }

  @Test
  public void testIfGameOver() {
    assertFalse(world.ifGameOver());
    world.getTarget().setHealth(30);
    assertTrue(world.ifGameOver());
  }

  @Test
  public void testRoundOfTargetCharacter() {
    RoomInfo initialLocation = world.getTarget().getCurrentLocation();
    world.roundOfTargetCharacter();
    RoomInfo updatedLocation = world.getTarget().getCurrentLocation();
    assertNotEquals(initialLocation, updatedLocation);
  }


  @Test
  public void testUpdateTarget() {
    Target newTarget = new Target("NewTarget", 10, world.getMansion().getRoomInfoByRoomNumber(0));
    world.updateTarget(newTarget);
    assertEquals("NewTarget", world.getTarget().getName());
    assertEquals(10, world.getTarget().getHealth());
    assertEquals(0, world.getTarget().getCurrentLocation().getRoomNumber());
  }

}