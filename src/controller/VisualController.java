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
  public void playGame(int maxNumOfTurns) {
    if (maxNumOfTurns <= 0) {
      throw new IllegalArgumentException("Invalid arguments provided.");
    }
    model.setMaxNumOfTurns(maxNumOfTurns);
    while ((!model.isGameOver())
        && (model.getNumOfTurnsPlayed() <= maxNumOfTurns)
        && (!exitGame())) {
      view.setDisplay(
          String.format("Turn %d (max %d).\n Now is %s's turn.", model.getNumOfTurnsPlayed(),
              maxNumOfTurns, model.getCurrentPlayer().getName()));
    }
  }

  @Override
  public void addHumanPlayer() {

  }

  @Override
  public void addComputerPlayer() {

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
