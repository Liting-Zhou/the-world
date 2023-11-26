package controller;

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
   *
   * @return true if game is exited, false otherwise
   */
  boolean exitGame();

  /**
   * Play the game.
   */
  void playGame();

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
   * Player moves to a neighboring room.
   */
  void playerMove();

  /**
   * Player makes an attempt to attack the target.
   */
  void attack();

  /**
   * Player picks up an item.
   */
  void pickUpItem();

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
  void displayRoomInfo();

  /**
   * Display information of a specific player.
   */
  void displayPlayerInfo();

  /**
   * Display information of the target.
   */
  void displayTargetInfo();
}
