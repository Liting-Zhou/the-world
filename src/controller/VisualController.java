package controller;

import model.World;
import view.View;

public class VisualController implements Features {

  private final World model;
  private View view;
  private boolean exitGame = false;

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
  public void exitGame() {
    exitGame = true;
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
    int maxNumOfTurns = model.getMaxNumOfTurns();
    while ((!model.isGameOver())
        && (model.getNumOfTurnsPlayed() <= maxNumOfTurns)
        && (!exitGame)) {
      view.setDisplay(
          String.format("Turn %d (max %d).\n Now is %s's turn.\n"
                  + "You can:\n"
                  + "(1) press 'W' and then click a neighbor room to move to"
                  + "(2) press 'P' to pick up a weapon if there is any"
                  + "(3) press 'L' to look around"
                  + "(4) press 'K' to attack the target when you are in the same space"
                  + "(5) press 'M' to move the pet", model.getNumOfTurnsPlayed(),
              maxNumOfTurns, model.getCurrentPlayer().getName()));

      model.catWander();
      model.roundOfTarget();
      view.setDisplay(String.format("Target has moved to the %s.\n",
          model.getTarget().getCurrentLocation().getRoomName()));
      view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
      model.roundOfPlayer();
      view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());

      if (model.isGameOver()) {
        String winner = model.getWinner().getName();
        view.setDisplay(String.format("Game over! %s wins!", winner));
        break;
      }
      if (model.getNumOfTurnsPlayed() > maxNumOfTurns) {
        view.setDisplay(String.format("Oops! You have run out of the maximum number of turns (%d)! "
            + "GAME OVER!\n", maxNumOfTurns));
        break;
      }
      if(exitGame){
        view.setDisplay("You choose to exit the game. Bye!");
        break;
      }
    }
  }


  @Override
  public void enterGame(){
      if (model.getListOfPlayers().isEmpty()) {
        view.setDisplay("No player added. Please add at least one player to start the game.");
        view.showSetUpPanel();
    }else{
        view.setDisplay("Game started!");
        view.displayGamePanel();
        view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
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
