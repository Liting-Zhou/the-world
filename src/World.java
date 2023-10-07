import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the game "The World", based on the classical board game Kill Doctor Lucky.
 */
public final class World {
  private Target target;
  private List<Player> players;
  private List<RoomInfo> listOfRooms;
  private Mansion mansion;
  private List<Weapon> weapons;
  private int indexOfPlayer = 0;
  private int startingRoom = 16;

  /**
   * Constructs a new "The World" game, initializing the world map and players
   * from a configuration file.
   *
   * @param conFile           The configuration file containing game setup information.
   * @param listOfPlayerNames The list of the names of players in clockwise order.
   * @throws IllegalArgumentException if the provided configuration file is invalid.
   */
  public World(Readable conFile, List<String> listOfPlayerNames) {
    // Read configuration file
    if (conFile == null) {
      throw new IllegalArgumentException("Invalid configuration file.");
    }
    // Read and process the configuration file to set up the game world.
    // TODO Maybe refactor to construct a parser for conFile first
    List<String> lines = new ArrayList<>();
    Scanner scan = new Scanner(conFile);
    while (scan.hasNextLine()) {
      lines.add(scan.nextLine());
    }
    scan.close();
    // Parse and set up the game components, including target, rooms, mansion, and players.
    initializeWeapons(lines);
    initializeRooms(lines);
    initializeMansion(lines);
    initializeTarget(lines);
    initializePlayers(listOfPlayerNames);
    //create a graphical representation of the world map.
    mansion.getBufferedImage();
  }

  /**
   * Initializes the list of weapons in the game.
   */
  private void initializeWeapons(List<String> lines) {
    int weaponsCount =
        Integer.parseInt(lines.get(25)); // This line contains the total number of weapons.
    int lineIndex = 26;

    // Initialize the list of weapons.
    weapons = new ArrayList<>();

    // Iterate through weapon information lines.
    for (int i = 0; i < weaponsCount; i++) {
      String weaponInfo = lines.get(lineIndex);
      String[] weaponData = weaponInfo.split(" ");

      // Parse room number, weapon power, and weapon name.
      int roomNumber = Integer.parseInt(weaponData[0]);
      int weaponPower = Integer.parseInt(weaponData[1]);
      String weaponName = weaponData[2];

      // Create a Weapon object with the parsed data and add it to the list of weapons.
      Weapon weapon = new Weapon(weaponPower, weaponName, roomNumber);
      weapons.add(weapon);

      lineIndex++;
    }
  }

  /**
   * Initializes the list of rooms in the game.
   *
   * @param lines A list of strings representing lines from the configuration file.
   */
  private void initializeRooms(List<String> lines) {
    int roomCount = Integer.parseInt(lines.get(2)); // Line 2 contains the room count.
    int lineIndex = 3; // Starting from line 3 for room data.

    listOfRooms = new ArrayList<>();
    for (int i = 0; i < roomCount; i++) {
      String roomInfo = lines.get(lineIndex);
      String[] roomData = roomInfo.split(" ");

      // Parse room coordinates and name.
      int topLeftX = Integer.parseInt(roomData[0]);
      int topLeftY = Integer.parseInt(roomData[1]);
      int bottomRightX = Integer.parseInt(roomData[2]);
      int bottomRightY = Integer.parseInt(roomData[3]);
      String roomName;
      //if there is only single word for the name of room
      if (roomData.length == 5) {
        roomName = roomData[4];
      } else {
        //if more than one word for the name of room
        String[] restOfRoomData = new String[roomData.length - 4];
        for (int j = 4; j < roomData.length; j++) {
          restOfRoomData[j - 4] = roomData[j];
        }
        roomName = String.join(" ", restOfRoomData);
      }


      // find the list of weapons belong to a specific room
      List<Weapon> listOfWeaponsSpecificRoom = new ArrayList<>();
      for (Weapon item : weapons) {
        int roomNumber = item.getBelongRoomNumber();
        if (roomNumber == lineIndex) {
          listOfWeaponsSpecificRoom.add(item);
        }
      }

      // Create a Room object with the parsed data.
      RoomInfo room =
          new RoomInfo(lineIndex - 3, topLeftX, topLeftY, bottomRightX, bottomRightY, roomName,
              listOfWeaponsSpecificRoom);

      // Add the created Room object to the list of rooms.
      listOfRooms.add(room);

      lineIndex++;
    }
  }

  /**
   * Initializes the game's mansion layout.
   */
  private void initializeMansion(List<String> lines) {
    String mansionInfo = lines.get(0); // Line 0 contains mansion dimensions and name.
    String[] mansionData = mansionInfo.split(" ");

    // Parse mansion dimensions and name.
    int mansionWidth = Integer.parseInt(mansionData[0]);
    int mansionHeight = Integer.parseInt(mansionData[1]);
    String mansionName = mansionData[2];

    // Create the Mansion object with the parsed data.
    mansion = new Mansion(mansionName, mansionHeight, mansionWidth, listOfRooms);

  }

  /**
   * Initializes the game's target character,
   * who has health 30 and starting location room number 16.
   */
  private void initializeTarget(List<String> lines) {
    String targetInfo = lines.get(1); // Line 1 contains target information.
    String[] targetData = targetInfo.split(" ");

    // Parse the target's total health and name
    int targetHealth = Integer.parseInt(targetData[0]);

    String targetName;
    if (targetData.length == 2) {
      targetName = targetData[1];
    } else {
      //if the name contains more than one word
      String[] restOfTargetData = new String[targetData.length - 1];
      for (int j = 1; j < targetData.length; j++) {
        restOfTargetData[j - 1] = targetData[j];
      }
      targetName = String.join(" ", restOfTargetData);
    }

    // Initialize the starting location, which is the trophy room, number 16
    RoomInfo currentLocation = mansion.getRoomInfoByRoomNumber(startingRoom);

    // Create the target character.
    target = new Target(targetName, targetHealth, currentLocation);
  }


  /**
   * Initializes the list of players in the game.
   */
  private void initializePlayers(List<String> listOfPlayerNames) {
    int totalPlayers = listOfPlayerNames.size();

    // Initialize the list of players.
    players = new ArrayList<>();
    RoomInfo currentLocation = mansion.getRoomInfoByRoomNumber(startingRoom);

    for (int i = 0; i < totalPlayers; i++) {
      Player player;
      player = new Player(i, listOfPlayerNames.get(i), currentLocation);
      // Add the created Player object to the list of players.
      players.add(player);
    }
  }


  /**
   * Gets the target.
   *
   * @return the target
   */
  public Target getTarget() {
    return target;
  }

  /**
   * Gets the players.
   *
   * @return a list of players
   */
  public List<Player> getPlayers() {
    return players;
  }

  /**
   * Gets the Mansion.
   *
   * @return the mansion
   */
  public Mansion getMansion() {
    return mansion;
  }

  /**
   * When the health of target is less or equal to zero, game is over.
   *
   * @return the true if game over
   */
  public Boolean ifGameOver() {
    return target.getHealth() <= 0;
  }

  /**
   * Gets the winner when game is over.
   *
   * @return the winner
   */
  public Player getWinner() {
    if (ifGameOver()) {
      return players.get(indexOfPlayer - 1);
    }
    System.out.println("Game is not over yet.");
    return null;
  }

  /**
   * Plays the next round, in each round, target moves and then one player moves.
   */
  public void playNextRound() {
    if (ifGameOver() == false) {
      System.out.println("Now play the next round!");
      roundOfTargetCharacter();
      roundOfPlayers();
      System.out.println("---------------");
      System.out.println("Game continues.");
    }

  }

  /**
   * Plays target move and update target information.
   */
  public void roundOfTargetCharacter() {
    Target targetAfterMove = target.move(mansion, players, listOfRooms);
    updateTarget(targetAfterMove);

  }

  /**
   * Updates the target after actions.
   *
   * @param target the target
   */
  public void updateTarget(Target target) {
    this.target = target;
  }

  /**
   * Player moves.
   */
  public void roundOfPlayers() {
    Player player;
    player = players.get(indexOfPlayer);
    System.out.println(player.getName() + "'s turn!");
    //ask which action the player choose
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter 'move' or 'stay': ");
    String action = scanner.nextLine();
    int roomNumber;
    if ("move".equals(action)) {
      System.out.println("Which room are you going to (enter a number between 0-21): ");
      roomNumber = Integer.valueOf(scanner.nextLine());
    } else {
      roomNumber = player.getCurrentLocation().getRoomNumber();
    }
    //get the new location of player
    RoomInfo newLocation = mansion.getRoomInfoByRoomNumber(roomNumber);
    //player acts.
    Target updatedTarget = player.action(action, newLocation, target, players, listOfRooms);
    updatePlayer(newLocation);
    System.out.println(player.getName() + " is now in room " + newLocation.getRoomNumber() + ".");
    updateTarget(updatedTarget);
    System.out.println("Target is in room " + updatedTarget.getCurrentLocation().getRoomNumber()
        + " with health " + updatedTarget.getHealth() + ".");

    //finds out which player's turn
    if (indexOfPlayer == players.size() - 1) {
      indexOfPlayer = 0;
    } else {
      indexOfPlayer++;
    }
  }

  /**
   * Updates the room information for a specific player after actions.
   *
   * @param newLocation the new location of player
   */
  public void updatePlayer(RoomInfo newLocation) {
    players.get(indexOfPlayer).updateRoomInfo(newLocation);
  }


}
