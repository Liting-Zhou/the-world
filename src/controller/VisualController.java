package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import model.HumanPlayer;
import model.MyWorld;
import model.Player;
import model.Room;
import model.Target;
import model.WeaponImp;
import model.World;
import view.View;

/**
 * This class represents a controller which supports UI interaction.
 */
public class VisualController implements Features {

  private final int initStateOfMaxNumOfTurns;
  private int maxNumOfTurns;
  private World model;
  private View view;
  private boolean exitGame = false;
  private boolean playerMoveMode = false;
  private boolean movePetMode = false;
  private boolean displayMode = true; //false when in play mode
  private boolean playMode = false;

  /**
   * Constructor.
   *
   * @param w the model to use
   */
  public VisualController(World w) {
    model = w;
    initStateOfMaxNumOfTurns = model.getMaxNumOfTurns();
  }

  /**
   * Mutator for the view.
   *
   * @param v the view to use
   */
  @Override
  public void setView(View v) {
    view = v;
    view.setFeatures(this);
  }


  @Override
  public void exitGame() {
    exitGame = true;
    checkIsGameOver();
  }

  @Override
  public void resetState() {
    model.resetState();
    exitGame = false;
    playerMoveMode = false;
    movePetMode = false;
    displayMode = true;
    playMode = false;
    maxNumOfTurns = initStateOfMaxNumOfTurns;
  }

  @Override
  public void gameSetUp() {
    // add players
    view.showSetUpPanel();
  }

  @Override
  public void newGameWithNewConfig() throws FileNotFoundException {
    String path = view.showInputDialog("Provide the PATH of the configuration file: ");
    Readable reader = new BufferedReader(new FileReader(path));
    model = new MyWorld(reader);
    String maxTurns = view.showInputDialog("Provide the maximum number of turns: ");
    model.setMaxNumOfTurns(Integer.parseInt(maxTurns));
    maxNumOfTurns = Integer.parseInt(maxTurns);
    exitGame = false;
    playerMoveMode = false;
    movePetMode = false;
    displayMode = true;
    playMode = false;
    gameSetUp();
  }

  @Override
  public boolean checkPlayerNumber() {
    if (model.getListOfPlayers().size() > 10) {
      view.setDisplay("You can not add more than 10 players.");
      return false;
    } else {
      return true;
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
      view.displayGamePanel(true);
      view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
    }
  }

  @Override
  public void playNextTurn() {
    model.petWander();
    model.roundOfTarget();
    view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
    view.setDisplay(
        String.format("Turn %d (max %d).\nNow is %s's turn.\n%s is in room %d.\n"
                + "Choose an action:\n"
                + "(1) press M and then click a neighbor room to move to\n"
                + "(2) press P to pick up a weapon if there is any\n"
                + "(3) press L to look around\n"
                + "(4) press A to attack the target when you are in the same space\n"
                + "(5) press T to move the pet", model.getNumOfTurnsPlayed(),
            maxNumOfTurns, model.getCurrentPlayer().getName(), model.getCurrentPlayer().getName(),
            model.getCurrentPlayer().getCurrentLocation().getRoomNumber()));
    if (model.getCurrentPlayer().getTypeOfPlayer() == 1) {
      view.setDisplay(model.roundOfPlayer()); //update current player index already
      view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
      model.updateTurnsPlayed();
      checkIsGameOver();
    } else {
      //human player
      playMode = true;
      view.resetFocus();
    }
  }

  private void checkIsGameOver() {
    //make it prompt
    if (model.isGameOver()) {
      String winner = model.getWinner().getName();
      view.showMessageDialog("Game Over", String.format("Game over! %s wins!", winner));
      view.displayGamePanel(false);
      view.setDisplay("You can restart the game by clicking the menu.");
    }
    if (model.getNumOfTurnsPlayed() > maxNumOfTurns) {
      view.showMessageDialog("Game Over", String.format(
          "Oops! You have run out of the maximum number of turns (%d)! GAME OVER!",
          maxNumOfTurns));
      view.displayGamePanel(false);
      view.setDisplay("You can restart the game by clicking the menu.");
    }
    if (exitGame) {
      view.showMessageDialog("Game Over", String.format("You choose to exit the game. Bye!"));
      view.displayGamePanel(false);
      view.setDisplay("You can restart the game by clicking the menu.");
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
          "You can not attack the target because you are not in the same room. "
              + "Choose another action.");
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
      setPlayMode(false);
      model.updatePlayerTurn();
      model.updateTurnsPlayed();
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
    if (("Your fist").equals(weaponName)) {
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
    setPlayMode(false);
    model.updatePlayerTurn();
    model.updateTurnsPlayed();
    checkIsGameOver();
  }

  @Override
  public void pickUpWeapon() {
    HumanPlayer currentPlayer = (HumanPlayer) model.getCurrentPlayer();
    if (currentPlayer.getWeaponsCarried().size() == currentPlayer.getMaxNumberOfWeapons()) {
      view.setDisplay(
          "You have reached the weapon limit. You can not pick up any more weapon. "
              + "Choose another action.");
      view.resetFocus();
      return;
    }
    if (currentPlayer.getCurrentLocation().getWeapons().isEmpty()) {
      view.setDisplay(
          "No weapons in this room.\n\nThis Turn ended.\nClick the button to play next turn.");
      setPlayMode(false);
      model.updatePlayerTurn();
      model.updateTurnsPlayed();
      checkIsGameOver();
      return;
    }
    if (currentPlayer.getCurrentLocation().getWeapons().size() == 1) {
      WeaponImp weapon = currentPlayer.getCurrentLocation().getWeapons().get(0);
      currentPlayer.getWeaponsCarried().add(weapon);
      view.setDisplay(String.format(
          "You picked up %s with power %d.\n\nThis Turn ended."
              + "\nClick the button to play next turn.",
          weapon.getName(),
          weapon.getPower()));
      currentPlayer.getCurrentLocation().removeWeapon(weapon);
      setPlayMode(false);
      model.updatePlayerTurn();
      model.updateTurnsPlayed();
      checkIsGameOver();
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
            "You picked up %s with power %d.\n\nThis Turn ended."
                + "\nClick the button to play next turn.",
            weapon.getName(),
            weapon.getPower()));
        currentPlayer.getCurrentLocation().removeWeapon(weapon);
        setPlayMode(false);
        model.updatePlayerTurn();
        model.updateTurnsPlayed();
        checkIsGameOver();
      }
    }
  }

  @Override
  public void lookAround() {
    Player currentPlayer = model.getCurrentPlayer();
    view.showMessageDialog("Looking Around", currentPlayer.lookAround());
    view.setDisplay(String.format("This Turn ended.\nClick the button to play next turn."));
    setPlayMode(false);
    model.updatePlayerTurn();
    model.updateTurnsPlayed();
    checkIsGameOver();
  }

  @Override
  public void moveThePet() {
    StringBuilder sb = new StringBuilder();
    sb.append("You chose to move the pet, ");
    sb.append(
        String.format("which is now in room %d.\nClick any room you want to teleport the pet to.",
            model.getPet().getCurrentLocation().getRoomNumber()));
    view.setDisplay(sb.toString());
    setMovePetMode(true);
  }

  @Override
  public void movePetToRoom(int x, int y) {
    model.movePetToRoom(x, y);
    view.setDisplay(String.format(
        "You have moved the pet to the %s. Next turn it will stay there.\n\nThis Turn ended."
            + "\nClick the button to play next turn.",
        model.getPet().getCurrentLocation().getRoomName()));
    model.updatePlayerTurn();
    model.updateTurnsPlayed();
    checkIsGameOver();
  }

  @Override
  public boolean getMovePetMode() {
    return movePetMode;
  }

  @Override
  public void setMovePetMode(boolean b) {
    movePetMode = b;
    displayMode = !b;
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
  public boolean getPlayMode() {
    return playMode;
  }

  @Override
  public void setPlayMode(boolean b) {
    playMode = b;
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
    Room previousRoom = model.getCurrentPlayer().getCurrentLocation();
    model.moveToRoom(x, y);
    Room currentRoom = model.getCurrentPlayer().getCurrentLocation();
    if (previousRoom.equals(currentRoom)) {
      view.setDisplay(String.format(
          "You chose the current location or a non-neighboring room.\nYou are still in the %s."
              + "\n\nThis Turn ended.\nClick the button to play next turn.",
          currentRoom.getRoomName()));
    } else {
      view.setDisplay(String.format(
          "You have moved to the %s.\n\nThis Turn ended.\nClick the button to play next turn.",
          model.getCurrentPlayer().getCurrentLocation().getRoomName()));
    }
    view.refresh(model.getMap(), model.getListOfPlayers(), model.getTarget());
    model.updatePlayerTurn();
    model.updateTurnsPlayed();
    checkIsGameOver();
  }
}
