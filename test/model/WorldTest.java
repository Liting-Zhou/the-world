package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class contains test cases for the {@link MyWorld} class.
 */
public class WorldTest {
  //TODO re-implement this!
//  private MyWorld world;
//
//  /**
//   * Sets up the test environment before each test case.
//   */
//
//  @Before
//  public void setUp() {
//
//    try {
//      Readable conFile = new BufferedReader(new FileReader("./res/mansion.txt"));
//      world = new MyWorld(conFile);
//    } catch (IOException e) {
//      System.err.println("Error reading configuration file: " + e.getMessage());
//      world = null;
//    }
//    world.
//    world.addHumanPlayer("Player1");
//    world.addComputerPlayer("Player2");
//    world.addComputerPlayer("Player3");
//  }
//
//  @Test
//  public void testGetTarget() {
//    assertNotNull(world.getTarget());
//    Assert.assertEquals("Doctor Lucky", world.getTarget().getName());
//  }
//
//  @Test
//  public void testGetPlayers() {
//    assertNotNull(world.getPlayers());
//    Assert.assertEquals(3, world.getPlayers().size());
//    Assert.assertEquals("Player1", world.getPlayers().get(0).getName());
//    Assert.assertEquals("Player2", world.getPlayers().get(1).getName());
//    Assert.assertEquals("Player3", world.getPlayers().get(2).getName());
//  }
//
//  @Test
//  public void testIfGameOver() {
//    assertFalse(world.ifGameOver());
//    world.getTarget().setHealth(30);
//    assertTrue(world.ifGameOver());
//  }
//
//  @Test
//  public void testRoundOfTargetCharacter() {
//    RoomInfo initialLocation = world.getTarget().getCurrentLocation();
//    world.roundOfTargetCharacter();
//    RoomInfo updatedLocation = world.getTarget().getCurrentLocation();
//    Assert.assertNotEquals(initialLocation, updatedLocation);
//  }
//
//
//  @Test
//  public void testUpdateTarget() {
//    Target newTarget = new Target("NewTarget", 10, world.getMansion().getRoomInfoByRoomNumber(0));
//    world.updateTarget(newTarget);
//    Assert.assertEquals("NewTarget", world.getTarget().getName());
//    Assert.assertEquals(10, world.getTarget().getHealth());
//    Assert.assertEquals(0, world.getTarget().getCurrentLocation().getRoomNumber());
//  }

}