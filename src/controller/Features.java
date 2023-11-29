package controller;

import java.util.List;
import model.Player;
import model.Target;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller. How
 * the view uses them as callbacks is completely up to how the view is designed
 * (e.g. it could use them as a callback for a button, or a callback for a
 * dialog box, or a set of text inputs, etc.)
 *
 * <p>Each function is designed to take in the necessary data to complete that
 * functionality.
 */
public interface Features {
  /**
   * Process the input string entered by the user.
   *
   * @param input the string entered by the user
   */
  void processInput(String input);

  /**
   * Exit the game.
   **/
  void exitGame();

  void enterGame();

  void gameSetUp();

  /**
   * Add a player.
   *
   * @param name         the name of the player
   * @param startingRoom the starting room of the player
   * @param weaponLimits the weapon limits of the player
   * @param playerType   the type of the player, 0 for human, 1 for computer
   */
  void addPlayer(String name, int startingRoom, int weaponLimits, int playerType);

  void playNextTurn();

  /**
   * Player makes an attempt to attack the target.
   */
  void attack();


  void attackAfterWeaponSelected(String weaponName);

  /**
   * Player picks up a weapon.
   */
  void pickUpWeapon();

  void pickUpAfterWeaponSelected(String weaponName);

  /**
   * Player looks around the room.
   */
  void lookAround();

  /**
   * Player moves the pet.
   */
  void moveThePet();

  /**
   * Display information of a specific room.
   */
  void displayRoomInfo(int x, int y);

  /**
   * Display information of a specific player.
   */
  void displayPlayerInfo(Player player);

  /**
   * Display information of the target.
   */
  void displayTargetInfo();


  Target getTarget();

  List<Player> getPlayers();

  boolean getDisplayMode();

  boolean getPlayerMoveMode();

  void setPlayerMoveMode(boolean b);

  void moveToRoom(int x, int y);
}
