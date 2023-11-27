package controller;

import model.World;
import view.View;

public class VisualController implements Features {

  private final World model;
  private View view;

  /**
   * Constructor.
   *
   * @param w the model to use
   */
  public VisualController(World w) {
    model = w;
  }

  /**
   * Mutator for the view.
   *
   * @param v the view to use
   */
  public void setView(View v) {
    view = v;
    // give the feature callbacks to the view
    view.setFeatures(this);
  }

  @Override
  public void processInput(String input) {
    // name of the player, starting room, and weapon limit
  }

  @Override
  public boolean exitGame() {
    return true;
  }

  @Override
  public void gameSetUp() {
    // display welcome message
    view.setDisplay("Welcome to the Game of Kill Doctor Happy!\n"
        + "Author: Liting Zhou\n\n"
        + "Now, add some players to the game.\n");
    // add players
    view.showSetUpPanel();
  }

  @Override
  public void playGame() {
    if (model.getListOfPlayers().isEmpty()) {
      view.setDisplay("No player added. Please add at least one player to start the game.");
      view.showSetUpPanel();
      return;
    }
    view.setDisplay("Game started!");
    view.displayGamePanel();

    view.refresh(model.getBufferedImage(), model.getListOfPlayers(), model.getTarget());
    int maxNumOfTurns = model.getMaxNumOfTurns();

    while ((!model.isGameOver())
        && (model.getNumOfTurnsPlayed() <= maxNumOfTurns)
        && (!exitGame())) {
      model.playNextTurn();
      view.setDisplay(
          String.format("Turn %d (max %d).\n Now is %s's turn.", model.getNumOfTurnsPlayed(),
              maxNumOfTurns, model.getCurrentPlayer().getName()));
      view.refresh(model.getBufferedImage(), model.getListOfPlayers(), model.getTarget());
    }
  }


  @Override
  public void addPlayer(String name, int startingRoom, int weaponLimits, int playerType) {
    if (playerType == 0) {
      model.addHumanPlayer(name, startingRoom, weaponLimits);
    } else {
      model.addComputerPlayer(name, startingRoom, weaponLimits);
    }
    view.setDisplay(
        String.format("Player %s added to the game. You can add up to 10 players.", name));
  }

  @Override
  public void playerMove() {

  }

  @Override
  public void attack() {

  }

  @Override
  public void pickUpItem() {

  }

  @Override
  public void lookAround() {

  }

  @Override
  public void moveThePet() {

  }

  @Override
  public void displayRoomInfo() {

  }

  @Override
  public void displayPlayerInfo() {

  }

  @Override
  public void displayTargetInfo() {

  }
}
