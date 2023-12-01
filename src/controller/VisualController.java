package controller;

import java.util.List;
import model.HumanPlayer;
import model.Player;
import model.Target;
import model.WeaponImp;
import model.World;
import view.View;

public class VisualController implements Features {

  private final World model;
  private final int maxNumOfTurns;
  private View view;
  private boolean exitGame = false;
  private boolean playerMoveMode = false;
  private boolean movePetMode = false;
  private boolean displayMode = true;

  /**
   * Constructor.
   *
   * @param w the model to use
   */
  public VisualController(World w) {
    model = w;
    maxNumOfTurns = model.getMaxNumOfTurns();
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
  public void exitGame() {
    exitGame = true;
  }

  @Override
  public void gameSetUp() {
    // display welcome message
    view.showMessageDialog("", "Welcome to the Game of Kill Doctor Happy!\n\n"
        + "Author: Liting Zhou\n\n"
        + "Now, add some players to the game.\n");
    // add players
    view.showSetUpPanel();
  }


  @Override
  public void playNextTurn() {
    model.petWander();
    model.roundOfTarget();
//    view.setDisplay(String.format("Turn %d (max %d).\nTarget has moved to the %s.\n",
//        model.getNumOfTurnsPlayed(),
//        maxNumOfTurns, model.getTarget().getCurrentLocation().getRoomName()));
    view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
    view.setDisplay(
        String.format("Turn %d (max %d).\nNow is %s's turn.\n"
                + "You can:\n"
                + "(1) press M and then click a neighbor room to move to\n"
                + "(2) press P to pick up a weapon if there is any\n"
                + "(3) press L to look around\n"
                + "(4) press A to attack the target when you are in the same space\n"
                + "(5) press T to move the pet", model.getNumOfTurnsPlayed(),
            maxNumOfTurns, model.getCurrentPlayer().getName()));
    if (model.getCurrentPlayer().getTypeOfPlayer() == 1) {
      view.setDisplay(model.roundOfPlayer());
      view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
    } else {
      //human player
      view.resetFocus();
      //set current player index to next player
      model.updatePlayerTurn();
    }
    //add number of turns played
    model.updateTurnsPlayed();
    checkIsGameOver();
  }

  private void checkIsGameOver() {
    if (model.isGameOver()) {
      String winner = model.getWinner().getName();
      view.setDisplay(String.format("Game over! %s wins!", winner));
    }
    if (model.getNumOfTurnsPlayed() > maxNumOfTurns) {
      view.setDisplay(String.format("Oops! You have run out of the maximum number of turns (%d)! "
          + "GAME OVER!\n", maxNumOfTurns));
    }
    if (exitGame) {
      view.setDisplay("You choose to exit the game. Bye!");
    }
  }

  @Override
  public void enterGame() {
    if (model.getListOfPlayers().isEmpty()) {
      view.setDisplay("No player added. Please add at least one player to start the game.");
      view.showSetUpPanel();
    } else {
      view.setDisplay("Game started!\n\n"
          + "(1) Click any space to see the room information.\n"
          + "(2) Click white square to see the target information.\n"
          + "(3) Click circle to see the player information. "
          + "Blue represents human player and red represents computer player.\n"
          + "(4) Click the button above to play next turn.\n");
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
  public void attack() {
    HumanPlayer currentPlayer = (HumanPlayer) model.getCurrentPlayer();
    Target target = model.getTarget();
    if (currentPlayer.getCurrentLocation() != target.getCurrentLocation()) {
      view.setDisplay(
          "You can not attack the target because you are not in the same room. Choose another action.");
      view.resetFocus();
      return;
    }
    if (currentPlayer.getWeaponsCarried().isEmpty()) {
      StringBuilder sb = new StringBuilder();
      sb.append("You have no weapon. Just poke the target in the eye!\n");
      currentPlayer.attackWithNoWeapon(target);
      if (currentPlayer.canBeSeen()) {
        sb.append("Oops! You can be seen by other player. No damage made.");
      } else {
        sb.append("You can not be seen by other player. You made 1 damage to the target.");
      }
      sb.append(String.format("\n\nThis Turn ended.\nClick the button to play next turn."));
      view.setDisplay(sb.toString());
      checkIsGameOver();
    } else {
      view.setDisplay(
          "Choose a weapon to attack, or just poke in the eye.");
      view.showWeaponDialogForAttack(currentPlayer.getWeaponsCarried(), this);
    }
  }

  @Override
  public void attackAfterWeaponSelected(String weaponName) {
    HumanPlayer currentPlayer = (HumanPlayer) model.getCurrentPlayer();
    StringBuilder sb = new StringBuilder();
    if (weaponName.equals("Your fist")) {
      sb.append("You chose to poke the target in the eye!\n");
      currentPlayer.attackWithNoWeapon(model.getTarget());
      if (currentPlayer.canBeSeen()) {
        sb.append("Oops! You can be seen by other player. No damage made.");
      } else {
        sb.append("You can not be seen by other player. You made 1 damage to the target.");
      }
    } else {
      for (WeaponImp weapon : currentPlayer.getWeaponsCarried()) {
        if (weapon.getName().equals(weaponName)) {
          sb.append(String.format("You chose %s to attack the target.\n",
              weapon.getName()));
          currentPlayer.attackWithWeapon(weapon, model.getTarget());
          if (currentPlayer.canBeSeen()) {
            sb.append("Oops! You can be seen by other player. No damage made.");
          } else {
            sb.append(String.format(
                "You can not be seen by other player. You made %d damage to the target.",
                weapon.getPower()));
          }
        }
        break;
      }
    }
    sb.append(String.format("\n\nThis Turn ended.\nClick the button to play next turn."));
    view.setDisplay(sb.toString());
    checkIsGameOver();
  }

  @Override
  public void pickUpWeapon() {
    HumanPlayer currentPlayer = (HumanPlayer) model.getCurrentPlayer();
    if (currentPlayer.getWeaponsCarried().size() == currentPlayer.getMaxNumberOfWeapons()) {
      view.setDisplay(
          "You have reached the weapon limit. You can not pick up any more weapon.");
      view.resetFocus();
      return;
    }
    if (currentPlayer.getCurrentLocation().getWeapons().isEmpty()) {
      view.setDisplay(
          "No weapons in this room.");
      return;
    }
    if (currentPlayer.getCurrentLocation().getWeapons().size() == 1) {
      WeaponImp weapon = currentPlayer.getCurrentLocation().getWeapons().get(0);
      currentPlayer.getWeaponsCarried().add(weapon);
      view.setDisplay(String.format(
          "You picked up %s with power %d.\n\nThis Turn ended.\nClick the button to play next turn.",
          weapon.getName(),
          weapon.getPower()));
      currentPlayer.getCurrentLocation().removeWeapon(weapon);
    } else {
      view.setDisplay("Choose a weapon to pick up.");
      view.showWeaponDialogForPickUp(currentPlayer.getCurrentLocation().getWeapons(), this);
    }
  }

  @Override
  public void pickUpAfterWeaponSelected(String weaponName) {
    HumanPlayer currentPlayer = (HumanPlayer) model.getCurrentPlayer();
    for (WeaponImp weapon : currentPlayer.getCurrentLocation().getWeapons()) {
      if (weapon.getName().equals(weaponName)) {
        currentPlayer.getWeaponsCarried().add(weapon);
        view.setDisplay(String.format(
            "You picked up %s with power %d.\n\nThis Turn ended.\nClick the button to play next turn.",
            weapon.getName(),
            weapon.getPower()));
        currentPlayer.getCurrentLocation().removeWeapon(weapon);
      }
    }
  }

  @Override
  public void lookAround() {
    Player currentPlayer = model.getCurrentPlayer();
    view.showMessageDialog("Looking Around", currentPlayer.lookAround());
    view.setDisplay(String.format("This Turn ended.\nClick the button to play next turn."));
  }

  @Override
  public void moveThePet() {
    StringBuilder sb = new StringBuilder();
    sb.append("You chose to move the pet, ");
    sb.append(String.format("which is now in room %d.\nClick any room you want to teleport the pet to.", model.getPet().getCurrentLocation().getRoomNumber()));
    view.setDisplay(sb.toString());
    setMovePetMode(true);
  }

  @Override
    public void movePetToRoom(int x, int y) {
        model.movePetToRoom(x, y);
    view.setDisplay(String.format(
        "You have moved the pet to the %s. Next turn it will stay there.\n\nThis Turn ended.\nClick the button to play next turn.",
        model.getPet().getCurrentLocation().getRoomName()));
    }
  @Override
  public void setMovePetMode(boolean b) {
    movePetMode = b;
    displayMode = !b;
  }

  @Override
  public boolean getMovePetMode() {
    return movePetMode;
  }

  @Override
  public void displayRoomInfo(int x, int y) {
    String display = model.displayRoomInformation(model.findRoomByCoordinates(x, y));
    view.showMessageDialog("Room Information", display);
  }

  @Override
  public void displayPlayerInfo(Player player) {
    view.showMessageDialog("Player Information", model.displayPlayerInformation(player));
  }

  @Override
  public void displayTargetInfo() {
    view.showMessageDialog("Target Information", model.displayTargetInformation());

  }

  @Override
  public Target getTarget() {
    return model.getTarget();
  }

  @Override
  public List<Player> getPlayers() {
    return model.getListOfPlayers();
  }

  @Override
  public boolean getDisplayMode() {
    return displayMode;
  }

  @Override
  public boolean getPlayerMoveMode() {
    return playerMoveMode;
  }

  @Override
  public void setPlayerMoveMode(boolean b) {
    playerMoveMode = b;
    displayMode = !b;
  }

  @Override
  public void moveToRoom(int x, int y) {
    model.moveToRoom(x, y);
    view.setDisplay(String.format(
        "You have moved to the %s.\n\nThis Turn ended.\nClick the button to play next turn.",
        model.getCurrentPlayer().getCurrentLocation().getRoomName()));
    view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
  }
}
