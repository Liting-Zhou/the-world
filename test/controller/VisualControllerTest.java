package controller;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import model.Player;
import model.RoomImp;
import model.World;
import org.junit.Before;
import org.junit.Test;
import view.View;

/**
 * A test class for the VisualController class.
 */
public class VisualControllerTest {

  private View mockView;
  private World mockModel;
  private VisualController controller;
  private StringBuilder log;

  /**
   * Set up the test environment.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    mockModel = new MockModel(log);
    mockView = new MockView(log);
    controller = new VisualController(mockModel);
    controller.setView(mockView);
  }

  @Test
  public void testExitGame() {
    controller.exitGame();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.isGameOver is invoked\n"
        + "model.getNumOfTurnsPlayed is invoked\n"
        + "view.showMessageDialog is invoked\n"
        + "view.displayGamePanel is invoked\n"
        + "view.setDisplay is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testGameSetUp() {
    controller.gameSetUp();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "view.showSetUpPanel is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test(expected = FileNotFoundException.class)
  public void testNewGameWithNewConfig() throws FileNotFoundException {
    controller.newGameWithNewConfig();
  }


  @Test
  public void testEnterGame() {
    controller.enterGame();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getListOfPlayers is invoked\n"
        + "view.setDisplay is invoked\n"
        + "view.showSetUpPanel is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testPlayNextTurn() {
    controller.playNextTurn();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.petWander is invoked\n"
        + "model.roundOfTarget is invoked\n"
        + "model.getMap is invoked\n"
        + "model.getListOfPlayers is invoked\n"
        + "model.getTarget is invoked\n"
        + "view.refresh is invoked\n"
        + "model.getNumOfTurnsPlayed is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "view.setDisplay is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "view.resetFocus is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testAddComputerPlayer() {
    controller.addPlayer("mock player", 0, 0, 1);
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.addComputerPlayer is invoked\n"
        + "view.setDisplay is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testAddHumanPlayer() {
    controller.addPlayer("mock player", 0, 0, 0);
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.addHumanPlayer is invoked\n"
        + "view.setDisplay is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testAttack() {
    controller.attack();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "model.getTarget is invoked\n"
        + "view.setDisplay is invoked\n"
        + "view.resetFocus is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testAttackAfterWeaponSelected() {
    controller.attackAfterWeaponSelected("mock weapon");
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "view.setDisplay is invoked\n"
        + "model.updatePlayerTurn is invoked\n"
        + "model.updateTurnsPlayed is invoked\n"
        + "model.isGameOver is invoked\n"
        + "model.getNumOfTurnsPlayed is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testPickUpWeapon() {
    controller.pickUpWeapon();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "view.setDisplay is invoked\n"
        + "view.resetFocus is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testPickUpAfterWeaponSelected() {
    controller.pickUpAfterWeaponSelected("mock weapon");
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getCurrentPlayer is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testLookAround() {
    controller.lookAround();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "view.showMessageDialog is invoked\n"
        + "view.setDisplay is invoked\n"
        + "model.updatePlayerTurn is invoked\n"
        + "model.updateTurnsPlayed is invoked\n"
        + "model.isGameOver is invoked\n"
        + "model.getNumOfTurnsPlayed is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testMoveThePet() {
    controller.moveThePet();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getPet is invoked\n"
        + "view.setDisplay is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testMovePetToRoom() {
    controller.movePetToRoom(0, 0);
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.movePetToRoom is invoked\n"
        + "model.getPet is invoked\n"
        + "view.setDisplay is invoked\n"
        + "model.updatePlayerTurn is invoked\n"
        + "model.updateTurnsPlayed is invoked\n"
        + "model.isGameOver is invoked\n"
        + "model.getNumOfTurnsPlayed is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testSetMovePetMode() {
    controller.setMovePetMode(true);
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testGetMovePetMode() {
    controller.getMovePetMode();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testDisplayRoomInfo() {
    controller.displayRoomInfo(0, 0);
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.findRoomByCoordinates is invoked\n"
        + "model.displayRoomInformation is invoked\n"
        + "view.showMessageDialog is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testDisplayPlayerInfo() {
    controller.displayPlayerInfo(
        new Player(0, 0, "mock player", new RoomImp(1, 2, 3, 4, 5, "mock room", new ArrayList<>()),
            0));
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.displayPlayerInformation is invoked\n"
        + "view.showMessageDialog is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testDisplayTargetInfo() {
    controller.displayTargetInfo();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.displayTargetInformation is invoked\n"
        + "view.showMessageDialog is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testGetTarget() {
    controller.getTarget();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getTarget is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testGetPlayers() {
    controller.getPlayers();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getListOfPlayers is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testGetDisplayMode() {
    controller.getDisplayMode();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testGetPlayTurnMode() {
    controller.getPlayMode();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testSetPlayTurnMode() {
    controller.setPlayMode(true);
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testGetPlayerMoveMode() {
    controller.getPlayerMoveMode();
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testSetPlayerMoveMode() {
    controller.setPlayerMoveMode(true);
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testMoveToRoom() {
    controller.moveToRoom(0, 0);
    String expected = "model.getMaxNumOfTurns is invoked\n"
        + "view.setFeatures is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "model.moveToRoom is invoked\n"
        + "model.getCurrentPlayer is invoked\n"
        + "view.setDisplay is invoked\n"
        + "model.getMap is invoked\n"
        + "model.getListOfPlayers is invoked\n"
        + "model.getTarget is invoked\n"
        + "view.refresh is invoked\n"
        + "model.updatePlayerTurn is invoked\n"
        + "model.updateTurnsPlayed is invoked\n"
        + "model.isGameOver is invoked\n"
        + "model.getNumOfTurnsPlayed is invoked\n";
    assertEquals(expected, log.toString());
  }
}