package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the game "The model.MyWorld", based on the classical board game Kill Doctor Lucky.
 */
public final class MyWorld implements World {
  private static List<Player> players = new ArrayList<>();
  private final int startingRoom = 16;
  private Target target;
  private List<Room> listOfRooms;
  private Mansion mansion;
  private List<WeaponImp> weapons;
  private int indexOfCurrentPlayer = 0;

  /**
   * Constructs a new "The model.MyWorld" game, initializing the world map from a configuration file.
   *
   * @param conFile The configuration file containing game setup information.
   * @throws IllegalArgumentException if the provided configuration file is invalid.
   */
  public MyWorld(Readable conFile) {
    // Read configuration file
    if (conFile == null) {
      throw new IllegalArgumentException("Invalid configuration file.");
    }
    // Read and process the configuration file to set up the game world.
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

      //if there is only single word for the name of weapon
      if (weaponData.length == 3) {
        weaponName = weaponData[2];
      } else {
        //if more than one word for the name of weapon
        String[] restOfWeaponData = new String[weaponData.length - 2];
        System.arraycopy(weaponData, 2, restOfWeaponData, 0, weaponData.length - 2);
        weaponName = String.join(" ", restOfWeaponData);
      }

      // Create a model.WeaponImp object with the parsed data and add it to the list of weapons.
      WeaponImp weapon = new WeaponImp(weaponPower, weaponName, roomNumber);
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
        System.arraycopy(roomData, 4, restOfRoomData, 0, roomData.length - 4);
        roomName = String.join(" ", restOfRoomData);
      }


      // find the list of weapons belong to a specific room
      List<WeaponImp> listOfWeaponsSpecificRoom = new ArrayList<>();
      for (WeaponImp item : weapons) {
        int roomNumber = item.getBelongRoomNumber();
        if (roomNumber == lineIndex - 3) {
          listOfWeaponsSpecificRoom.add(item);
        }
      }

      // Create a Room object with the parsed data.
      Room room =
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

    // Create the model.Mansion object with the parsed data.
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
      System.arraycopy(targetData, 1, restOfTargetData, 0, targetData.length - 1);
      targetName = String.join(" ", restOfTargetData);
    }

    // Initialize the starting location, which is the trophy room, number 16
    Room currentLocation = mansion.getRoomInfoByRoomNumber(startingRoom);

    // Create the target character.
    target = new Target(targetName, targetHealth, currentLocation);
  }

  /**
   * Initializes player.
   */
  private void initializePlayer(int indexOfNewPlayer, int typeOfPlayer, String playerName,int startingRoomNumber,int maxNumOfWeapons) {
    Room currentLocation = mansion.getRoomInfoByRoomNumber(startingRoomNumber);
    Player player = new Player(indexOfNewPlayer, typeOfPlayer, playerName, currentLocation,maxNumOfWeapons);
    // Add the created model.Player object to the list of players.
    players.add(player);
  }


  /**
   * Adds human-controlled player to the game.
   *
   * @param playerName The name of the player to add.
   */
  @Override
  public void addHumanPlayer(String playerName,int startingRoomNumber,int maxNumOfWeapons) {
    int indexOfNewPlayer = players.size();
    initializePlayer(indexOfNewPlayer, 0, playerName,startingRoomNumber,maxNumOfWeapons);
    System.out.println(String.format("Human-controlled player %s is added to the game!", playerName));
  }

  /**
   * Adds computer-controlled player to the game.
   *
   * @param playerName The name of the player to add.
   */
  @Override
  public void addComputerPlayer(String playerName,int startingRoomNumber,int maxNumOfWeapons) {
    int indexOfNewPlayer = players.size();
    initializePlayer(indexOfNewPlayer, 1, playerName,startingRoomNumber,maxNumOfWeapons);
    System.out.println(String.format("Computer-controlled player %s is added to the game!", playerName));
  }

  /**
   * Gets the target.
   *
   * @return the target
   */
  @Override
  public Target getTarget() {
    return target;
  }

  /**
   * Gets the players.
   *
   * @return a list of players
   */
  public static List<Player> getPlayers() {
    return players;
  }

  private Player getCurrentPlayer() {
    return players.get(indexOfCurrentPlayer);
  }

  /**
   * Gets the model.Mansion.
   *
   * @return the mansion
   */
  @Override
  public Mansion getMansion() {
    return mansion;
  }

  /**
   * When the health of target is less or equal to zero, game is over.
   *
   * @return the true if game over
   */
  @Override
  public Boolean ifGameOver() {
    return target.getHealth() <= 0;
  }

  /**
   * Gets the winner when game is over.
   *
   * @return the winner
   */
  @Override
  public Player getWinner() {
    if (ifGameOver()) {
      return players.get(indexOfCurrentPlayer - 1);
    }
    System.out.println("Game is not over yet.");
    return null;
  }

  /**
   * Plays the next round, in each round, target moves and then one player moves.
   */
  @Override
  public void playNextRound() {
    //check if players are added
    if (players.isEmpty()) {
      System.out.println("No player is added in the game yet.");
      return;
    }
    if (!ifGameOver()) {
      System.out.println("***************");
      System.out.println("Now play the next round!");
      roundOfTargetCharacter();
      roundOfPlayers();
      System.out.println();
      System.out.println(String.format("This turn has finished. And the target is now in room %d with health %d.",
            target.getCurrentLocation().getRoomNumber(), target.getHealth()));
      System.out.println();
      System.out.println("***************");
      System.out.println("Game continues.");
    }

  }

  /**
   * Plays target move and update target information.
   */
  @Override
  public void roundOfTargetCharacter() {
    Target targetAfterMove = target.move(mansion, players, listOfRooms);
    updateTarget(targetAfterMove);

  }

  /**
   * Updates the target after actions.
   *
   * @param target the target
   */
  @Override
  public void updateTarget(Target target) {
    this.target = target;
  }

  /**
   * model.Player's turn. model.Player can choose three actions:
   * 1.move to a neighboring space.
   * 2.pick up an item.
   * 3.look around by displaying information about where a specific player is in the world
   * including what spaces that can be seen from where they are.
   */
  @Override
  public void roundOfPlayers() {
    Player player;
    player = players.get(indexOfCurrentPlayer);

    System.out.println(String.format("It's %s's turn!", player.getName()));
    //display the current location of player
    System.out.println(String.format("You are now in room %d.", player.getCurrentLocation().getRoomNumber()));
    System.out.print("And you");
    player.displayWeaponInformation();
    System.out.println();

    //display weapons in the current room
    player.getCurrentLocation().displayWeapons();
    System.out.println();

    //display neighbor rooms
    System.out.println("You can move to the following rooms: ");
    player.getCurrentLocation().displayNeighbors();
    //ask which action the player choose
    System.out.println();
    System.out.println(
        String.format("You have 3 options:%n1.move to a neighboring space.%n2.pick up a weapon if there is any.%n3.look around.%nWhat do you want to do, %s?", player.getName()));
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter the corresponding number: ");
    Integer action = scanner.nextInt();
    //TODO check valid action, throw exception
    if (action == 1) {
      //move to a neighboring space
      player.move();
    } else if (action == 2) {
      //pick up a weapon if there is any
      player.pickUpWeapon();
    } else if (action == 3) {
      //look around
      player.lookAround();
    } else {
      System.out.println("Invalid action.");
    }

    //finds out which player acts next turn
    if (indexOfCurrentPlayer == players.size() - 1) {
      indexOfCurrentPlayer = 0;
    } else {
      indexOfCurrentPlayer++;
    }
  }

  /**
   * Displays the map of the mansion.
   */
  @Override
  public void displayMap() {
    mansion.getBufferedImage();
  }

  /**
   * Displays information about the target.
   */
  @Override
  public void displayTargetInformation() {
    // Display information about the target
    System.out.println("Target Information:");
    System.out.println(String.format("Name: %s", target.getName()));
    System.out.println(String.format("Current Location: Room %d", target.getCurrentLocation().getRoomNumber()));
    System.out.println(String.format("Health: %d", target.getHealth()));
    System.out.println("--------------");
  }

  /**
   * Displays information about the specified player.
   */
  @Override
  public void displayPlayerInformation() {
    System.out.println();
    if (players.isEmpty()) {
      System.out.println("No player is added in the game yet");
      return;
    }

// 1.Display list of players
    System.out.println("List of players: ");
    for (Player player : players) {
      System.out.println(player.getName());
    }
    //2.Ask which player to display
    System.out.println();
    System.out.println("Which player do you want to display? Please enter the name: ");
    Scanner scanner = new Scanner(System.in);
    String playerName = scanner.nextLine();
    Player playerToBeDisplayed;
    for (Player player : players) {
      if (player.getName().equalsIgnoreCase(playerName)) {
        //3.Display the player information
        System.out.println(String.format("Information of player %s: ", player.getName()));
        System.out.print(player.getName());
        player.displayWeaponInformation();
        System.out.println(String.format("Maximum number of weapons can carry: %d", player.getMaxNumberOfWeapons()));
        if (player.getTypeOfPlayer() == 0) {
          System.out.println("This is a human player.");
        } else {
          System.out.println("This is a computer player.");
        }
        System.out.println(String.format("Current Location: Room %d", player.getCurrentLocation().getRoomNumber()));
        player.getCurrentLocation().displayWeapons();
        System.out.println("--------------");
        break;
      }
    }


  }

  /**
   * Displays information about the target and players.
   */
  @Override
  public void displayRoomInformation() {
    // 1.Display list of rooms
    System.out.println();
    mansion.displayListOfRooms();
    System.out.println();

    // 2.Ask which room to display
    System.out.println("Which room do you want to display? Please enter the room number (0-21): ");
    Scanner scanner = new Scanner(System.in);
    Integer roomNumber = scanner.nextInt();
    Room room = mansion.getRoomInfoByRoomNumber(roomNumber);
    System.out.println();

    // 3.Display the room information
    System.out.println(String.format("Room %d information:", roomNumber));
    room.displayWeapons();
    room.displayTarget(target);
    room.displayPlayers(players);

    System.out.println("--------------");
  }
}
