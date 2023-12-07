package controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import model.HumanPlayer;
import model.Pet;
import model.Player;
import model.Room;
import model.RoomImp;
import model.Target;
import model.World;

/**
 * This class represents a mock model for testing.
 */
public class MockModel implements World {
  private StringBuilder log;

  /**
   * Constructs a mock model.
   *
   * @param log the log
   */
  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addHumanPlayer(String playerName, int startingRoomNumber, int maxNumOfWeapons) {
    log.append("model.addHumanPlayer is invoked\n");
  }

  @Override
  public void addComputerPlayer(String playerName, int startingRoomNumber, int maxNumOfWeapons) {
    log.append("model.addComputerPlayer is invoked\n");
  }

  @Override
  public int getMaxNumOfTurns() {
    log.append("model.getMaxNumOfTurns is invoked\n");
    return 0;
  }

  @Override
  public void setMaxNumOfTurns(int maxNumOfTurns) {
    log.append("model.setMaxNumOfTurns is invoked\n");
  }

  @Override
  public int getNumOfTurnsPlayed() {
    log.append("model.getNumOfTurnsPlayed is invoked\n");
    return 0;
  }

  @Override
  public Boolean isGameOver() {
    log.append("model.isGameOver is invoked\n");
    return false;
  }

  @Override
  public Player getWinner() {
    log.append("model.getWinner is invoked\n");
    return null;
  }

  @Override
  public void getPlayerAndDisplay() {
    log.append("model.getPlayerAndDisplay is invoked\n");
  }

  @Override
  public void getRoomAndDisplay() {
    log.append("model.getRoomAndDisplay is invoked\n");
  }

  @Override
  public String displayTargetInformation() {
    log.append("model.displayTargetInformation is invoked\n");
    return "text return from model.displayTargetInformation()\n";
  }

  @Override
  public void displayListOfRooms() {
    log.append("model.displayListOfRooms is invoked\n");
  }

  @Override
  public String displayPlayerInformation(Player player) {
    log.append("model.displayPlayerInformation is invoked\n");
    return "text return from model.displayPlayerInformation()\n";
  }

  @Override
  public String displayRoomInformation(Room room) {
    log.append("model.displayRoomInformation is invoked\n");
    return "text return from model.displayRoomInformation()\n";
  }

  @Override
  public void playNextTurn() {
    log.append("model.playNextTurn is invoked\n");
  }

  @Override
  public void resetState() {
    log.append("model.resetState is invoked\n");
  }

  @Override
  public void saveMansionMap() {
    log.append("model.saveMansionMap is invoked\n");
  }

  @Override
  public void updateTurnsPlayed() {
    log.append("model.updateTurnsPlayed is invoked\n");
  }

  @Override
  public void roundOfTarget() {
    log.append("model.roundOfTarget is invoked\n");
  }

  @Override
  public void petWander() {
    log.append("model.petWander is invoked\n");
  }

  @Override
  public String roundOfPlayer() {
    log.append("model.roundOfPlayer is invoked\n");
    return "text return from model.roundOfPlayer()\n";
  }

  @Override
  public void updatePlayerTurn() {
    log.append("model.updatePlayerTurn is invoked\n");
  }

  @Override
  public void movePetToRoom(int x, int y) {
    log.append("model.movePetToRoom is invoked\n");
  }

  @Override
  public void moveToRoom(int x, int y) {
    log.append("model.moveToRoom is invoked\n");
  }

  @Override
  public Room findRoomByCoordinates(int x, int y) {
    log.append("model.findRoomByCoordinates is invoked\n");
    return null;
  }


  @Override
  public BufferedImage getMap() {
    log.append("model.getMap is invoked\n");
    return null;
  }

  @Override
  public Target getTarget() {
    log.append("model.getTarget is invoked\n");
    return new Target("mock target", 10,
        new RoomImp(1, 2, 3, 4, 5, "mock room", new ArrayList<>()));
  }

  @Override
  public Pet getPet() {
    log.append("model.getPet is invoked\n");
    return new Pet("mock pet", new RoomImp(1, 2, 3, 4, 5, "mock room", new ArrayList<>()));
  }

  @Override
  public Player getCurrentPlayer() {
    log.append("model.getCurrentPlayer is invoked\n");
    return new HumanPlayer(0, 0, "mock player",
        new RoomImp(1, 2, 3, 4, 5, "mock room", new ArrayList<>()), 0);
  }

  @Override
  public List<Player> getListOfPlayers() {
    log.append("model.getListOfPlayers is invoked\n");
    return new ArrayList<>();
  }

  @Override
  public List<Room> getListOfRooms() {
    log.append("model.getListOfRooms is invoked\n");
    return null;
  }

}
