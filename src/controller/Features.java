package controller;

import java.io.FileNotFoundException;
import view.View;

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
   * Sets the view for the controller.
   *
   * @param v the view
   */
  void setView(View v);

  /**
   * Exit the game.
   **/
  void exitGame();

  /**
   * Enter the game.
   */
  void enterGame();

  /**
   * Reset the state when the game is restarted.
   */
  void resetState();

  /**
   * Set up the game.
   */
  void gameSetUp();

  /**
   * Start the game with a new configuration.
   *
   * @throws FileNotFoundException if the file is not found
   */
  void newGameWithNewConfig() throws FileNotFoundException;

  /**
   * Add a player.
   *
   * @param name         the name of the player
   * @param startingRoom the starting room of the player
   * @param weaponLimits the weapon limits of the player
   * @param playerType   the type of the player, 0 for human, 1 for computer
   */
  void addPlayer(String name, int startingRoom, int weaponLimits, int playerType);

  /**
   * Plays the next turn.
   */
  void playNextTurn();

  /**
   * Player makes an attempt to attemptToAttack the target.
   */
  void attemptToAttack();

  /**
   * Player attacks after weapon selected.
   *
   * @param weaponName the name of the weapon
   */
  void attackAfterWeaponSelected(String weaponName);

  /**
   * Player tries to pick up a weapon.
   */
  void attemptToPickUpWeapon();

  /**
   * Player picks up a weapon after weapon selected.
   *
   * @param weaponName the name of the weapon
   */
  void pickUpAfterWeaponSelected(String weaponName);

  /**
   * Player looks around the room.
   */
  void lookAround();

  /**
   * Player tries to move the pet.
   */
  void attemptToMoveThePet();

  /**
   * The pet is moved to a specific room.
   *
   * @param x the x coordinate of the room
   * @param y the y coordinate of the room
   */
  void movePetToRoom(int x, int y);

  /**
   * Gets whether the pet is moved.
   *
   * @return true if the pet is moved, false otherwise
   */
  boolean getMovePetMode();

  /**
   * Sets whether the pet is moved.
   *
   * @param b true if the pet is moved, false otherwise
   */
  void setMovePetMode(boolean b);


  /**
   * Gets the display mode. There are two modes, display mode and play mode.
   * In display mode, user can click the map for information.
   *
   * @return true if the display mode is on, false otherwise
   */
  boolean getDisplayMode();

  /**
   * Gets the play mode. There are two modes, display mode and play mode.
   *
   * @return true if the play mode is on, false otherwise
   */
  boolean getPlayMode();

  /**
   * Sets the play mode.
   *
   * @param b true if the play mode is on, false otherwise
   */
  void setPlayMode(boolean b);

  /**
   * Gets the player move mode. When this mode is on,
   * listen to click on the map and move the player.
   *
   * @return true if the player move mode is on, false otherwise
   */
  boolean getPlayerMoveMode();

  /**
   * Sets the player move mode.
   *
   * @param b true if the player move mode is on, false otherwise
   */
  void setPlayerMoveMode(boolean b);

  /**
   * Moves the player to a specific room.
   *
   * @param x the x coordinate of the room
   * @param y the y coordinate of the room
   */
  void moveToRoom(int x, int y);
}
