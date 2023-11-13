package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the game "The MyWorld", based on the classical board game
 * Kill Doctor Lucky.
 */
public final class MyWorld implements World {
  private static List<Player> players = new ArrayList<>();
  private final int startingRoom = 0; //default value
  int maxNumOfTurns = 100; //default value
  private Target target;
  private Pet cat;
  private List<Room> listOfRooms;
  private Mansion mansion;
  private List<WeaponImp> weapons;
  private int indexOfCurrentPlayer = 0;
  private int numOfTurnsPlayed = 1;


  /**
   * Constructs a new "MyWorld" game, initializing the world map from
   * a configuration file.
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
    initializePet(lines);
    // describe the world
    worldDescription();
  }

  /**
   * Gets the players.
   *
   * @return a list of players
   */
  public static List<Player> getPlayers() {
    return players;
  }

  /**
   * Initializes the list of weapons in the game.
   */
  private void initializeWeapons(List<String> lines) {
    int weaponsCount =
        Integer.parseInt(lines.get(26)); // This line contains the total number of weapons.
    int lineIndex = 27;

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

      // Create a WeaponImp object with the parsed data and add it to the list of weapons.
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
    int roomCount = Integer.parseInt(lines.get(3)); // Line 3 contains the room count.
    int lineIndex = 4; // Starting from line 4 for room data.

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
        if (roomNumber == lineIndex - 4) {
          listOfWeaponsSpecificRoom.add(item);
        }
      }

      // Create a Room object with the parsed data.
      Room room =
          new RoomInfo(lineIndex - 4, topLeftX, topLeftY, bottomRightX, bottomRightY, roomName,
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
    String[] restOfMansionData = new String[mansionData.length - 2];
    System.arraycopy(mansionData, 2, restOfMansionData, 0, mansionData.length - 2);
    String mansionName = String.join(" ", restOfMansionData);

    // Create the Mansion object with the parsed data.
    mansion = new Mansion(mansionName, mansionHeight, mansionWidth, listOfRooms);

  }

  /**
   * Initializes the game's target character,
   * who has health 20 and starting location room number 0.
   */
  private void initializeTarget(List<String> lines) {
    String targetInfo = lines.get(1); // Line 1 contains target information.
    String[] targetData = targetInfo.split(" ");

    // Parse the target's total health and name
    int targetHealth = Integer.parseInt(targetData[0]);
    String[] restOfTargetData = new String[targetData.length - 1];
    System.arraycopy(targetData, 1, restOfTargetData, 0, targetData.length - 1);
    String targetName = String.join(" ", restOfTargetData);

    // Initialize the starting location
    Room currentLocation = mansion.getRoomInfoByRoomNumber(startingRoom);

    // Create the target character.
    target = new Target(targetName, targetHealth, currentLocation);
    mansion.setTarget(target);
  }

  /**
   * Initializes the target's pet, whose starting location is the same as the target. The space
   * occupied by the pet is not visible to neighbors.
   */
  private void initializePet(List<String> lines) {
    String info = lines.get(2); // Line 2 contains pet information.
    String[] infoData = info.split(" ");
    String petName = infoData[0];

    // Initialize the starting location
    Room currentLocation = mansion.getRoomInfoByRoomNumber(startingRoom);

    // Create the target character.
    cat = new Cat(petName, currentLocation);
    mansion.setPet(cat);
  }

  /**
   * Initializes player.
   */
  private void initializePlayer(int indexOfNewPlayer, int typeOfPlayer, String playerName,
                                int startingRoomNumber, int maxNumOfWeapons) {
    Room currentLocation = mansion.getRoomInfoByRoomNumber(startingRoomNumber);
    Player player;
    if (typeOfPlayer == 0) {
      player = new HumanPlayer(indexOfNewPlayer, typeOfPlayer, playerName, currentLocation,
          maxNumOfWeapons);
    } else {
      player = new ComputerPlayer(indexOfNewPlayer, typeOfPlayer, playerName,
          currentLocation, maxNumOfWeapons);
    }

    // Add the created Player object to the list of players.
    players.add(player);
    mansion.addPlayer(player);
  }

  /**
   * Displays a world description.
   */
  private void worldDescription() {
    //1. describe the mansion by name and number of rooms
    System.out.println("---------------------------------------------------------");
    System.out.println("             Game of The World Description");
    System.out.println("---------------------------------------------------------");
    System.out.println(String.format("Welcome to %s's mansion! There are %d rooms in the mansion, "
            + "and there might be weapons in some rooms.",
        target.getName(), listOfRooms.size()));
    //2. describe the target by name, health, starting location and rules of moving
    System.out.println(String.format("%s is the target, who has health %d, starts from room %d "
            + "and moves around the mansion.",
        target.getName(), target.getHealth(), target.getCurrentLocation().getRoomNumber()));
    //3.describe the pet by name, starting location and rules of moving
    System.out.println(String.format("%s has a cat named %s, who starts from the same "
            + "room as the target. %s also moves around the mansion, \n"
            + "but follows a different rule "
            + "from the target. Moreover, the room occupied by %s is not visible to neighbors.",
        target.getName(), cat.getName(), cat.getName(), cat.getName()));
    //4. describe the player rules by introducing type, starting location, maximum number of
    //   weapons, and different actions.
    System.out.println("After initialization of the World, players can be added to the game, "
        + "specifying name, starting room, and maximum \nnumber of weapons could be carried. There "
        + "could be two types of players: human-controlled and computer-controlled.\n"
        + "Player can choose from these 5 actions:\n1. Move to a neighboring room.\n2. Pick up "
        + "a weapon in the room.\n3. Look around.\n4. Move the cat to a specified space."
        + "\n5. Attack the target.\nComputer player should attack the target whenever there "
        +
        "is chance, otherwise randomly choose other actions. \nWhile human player actively chooses"
        + " any possible action.");
    //5. describe the weapon rules by introducing power and name
    System.out.println("Weapon has a power and a name. Once picked up, it will be removed "
        + "from the room. Once used for attack, \nit will be removed from the world.");
    //6. describe the game rules. Given maximum number of turns, in each round target moves first
    //   and then one player acts. Player acts in the order of being added to the game. Game over if
    //   target's health reaches zero or running out of turns. The winner is the player who finally
    //   kills the target.
    System.out.println("Finally, the game is initialized with a maximum number of turns, "
        + "in each turn "
        + "target moves first and then one player acts.\nPlayer acts in the order of being added "
        + "to the game. GAME IS OVER when target's health reaches zero"
        + " or the game is running out of turns.\n"
        + "The winner is the player who finally kills the target and no winner "
        + "for the latter case.");
    System.out.println("---------------------------------------------------------");
    System.out.println("---------------------------------------------------------");
    System.out.println();
  }


  /**
   * Adds human-controlled player to the game.
   *
   * @param playerName The name of the player to add.
   */
  @Override
  public void addHumanPlayer(String playerName, int startingRoomNumber, int maxNumOfWeapons) {
    int indexOfNewPlayer = players.size();
    initializePlayer(indexOfNewPlayer, 0, playerName, startingRoomNumber, maxNumOfWeapons);
    System.out.println(
        String.format("Human-controlled player %s is added to the game!", playerName));
  }

  /**
   * Adds computer-controlled player to the game.
   *
   * @param playerName The name of the player to add.
   */
  @Override
  public void addComputerPlayer(String playerName, int startingRoomNumber, int maxNumOfWeapons) {
    int indexOfNewPlayer = players.size();
    initializePlayer(indexOfNewPlayer, 1, playerName, startingRoomNumber, maxNumOfWeapons);
    System.out.println(
        String.format("Computer-controlled player %s is added to the game!", playerName));
  }

  /**
   * Sets the maximum number of turns.
   */
  @Override
  public void setMaxNumOfTurns(int maxNumOfTurns) {
    this.maxNumOfTurns = maxNumOfTurns;
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
   * Gets number of turns played.
   *
   * @return the number of turns played
   */
  @Override
  public int getNumOfTurnsPlayed() {
    return numOfTurnsPlayed;
  }

  private Player getCurrentPlayer() {
    return players.get(indexOfCurrentPlayer);
  }

  /**
   * Gets the Mansion.
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
  public Boolean isGameOver() {
    return target.getHealth() <= 0;
  }

  /**
   * Gets the winner when game is over.
   *
   * @return the winner
   */
  @Override
  public Player getWinner() {
    if (isGameOver()) {
      return players.get(indexOfCurrentPlayer - 1);
    }
    System.out.println("Game is not over yet.");
    return null;
  }

  /**
   * Plays the next turn, in each turn, target moves and then one player moves.
   */
  @Override
  public void playNextTurn() {
    //check if players are added
    if (players.isEmpty()) {
      System.out.println("No player is added in the game yet.");
      return;
    }
    if (!isGameOver()) {
      System.out.println("---------------");
      System.out.println("Now play the next turn!");
      cat.wander();
      roundOfTargetCharacter();
      roundOfPlayer();

      //check health of target, if less than zero, game over and display the winner
      if (target.getHealth() <= 0) {
        System.out.println("GAME OVER!");
        System.out.println(String.format("Player %s wins!", getCurrentPlayer().getName()));
        return;
      }
      System.out.println();
      System.out.println(String.format("Turn %d has ended (%d turns left). And Target "
              + "is now in room %d with health %d.", numOfTurnsPlayed,
          maxNumOfTurns - numOfTurnsPlayed, target.getCurrentLocation().getRoomNumber(),
          target.getHealth()));
      numOfTurnsPlayed += 1;
      System.out.println("--------------");
    }
  }

  /**
   * Plays target move and update target information.
   */
  private void roundOfTargetCharacter() {
    Target targetAfterMove = target.move(listOfRooms);
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
   * Player's turn. Human player can choose from these three actions:
   * 1. move to a neighboring space.
   * 2. pick up an item.
   * 3. look around by displaying information about where a specific player is in the world
   * including what spaces that can be seen from where they are.
   * Computer player randomly choose an action.
   */
  private void roundOfPlayer() {
    Player player;
    player = players.get(indexOfCurrentPlayer);
    System.out.println();
    System.out.println(String.format("It's %s's turn!", player.getName()));
    System.out.println();

    System.out.println("USEFUL INFORMATION:");
    System.out.print("-> You");
    player.displayWeaponInformation();
    System.out.println(
        String.format("-> You are now in room %d, the %s",
            player.getCurrentLocation().getRoomNumber(),
            player.getCurrentLocation().getRoomName()));

    //check if target is in this room
    if (player.getCurrentLocation().isTargetHere()) {
      System.out.println("-> Target is in this room!");
    }

    //check if the pet is in this room
    if (player.getCurrentLocation().isPetHere()) {
      System.out.println("-> The cat is in this room! This room is invisible!!!");
    }

    if (player.getCurrentLocation().isAnyOtherPlayerHere(player)) {
      System.out.println("-> There is other player in this room!");
    }

    //display weapons in the current room
    //player.getCurrentLocation().displayWeapons();

    //display neighbor rooms
    //System.out.println("-> You can move to the following rooms: ");
    //player.getCurrentLocation().displayNeighborsSimple();

    //ask which action the player choose
    //if target is here, player can attack
    if (player.getCurrentLocation().isTargetHere()) {
      System.out.println();
      System.out.println(
          String.format(
              "Now you have 5 options:%n1. Move to a neighboring space.%n2. "
                  + "Pick up a weapon if there is any.%n3. "
                  + "Look around.%n4. Moves the cat to a specified space.%n5.Attack the target."
                  + "%nWhat do you want to do, %s?",
              player.getName()));
      System.out.println();

      //check if the player is human or computer
      if (player.getTypeOfPlayer() == 0) {
        HumanPlayer p = (HumanPlayer) player;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the corresponding number: ");
        // make sure the input can only be 1, 2, 3,4,5
        int action;
        while (true) {
          while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number:");
            scanner.next(); // consume the invalid token
          }
          action = scanner.nextInt();
          if (action < 1 || action > 5) {
            System.out.println("Invalid action. Please enter again:");
          } else {
            break;
          }
        }

        if (action == 1) {
          //move to a neighboring space
          p.move();
        } else if (action == 2) {
          //pick up a weapon if there is any
          p.pickUpWeapon();
        } else if (action == 3) {
          //look around
          p.lookAround();
        } else if (action == 4) {
          p.moveThePet();
        } else {
          p.attack();
        }
      } else {
        ComputerPlayer p = (ComputerPlayer) player;
        //if can be seen, no attack, randomly choose other 4 actions
        if (p.canBeSeen()) {
          if (p.getCurrentLocation().getWeapons().isEmpty()
              || p.weaponsCarried.size() == p.getMaxNumberOfWeapons()) {
            p.randomActionNoWeapon();
          } else {
            p.randomAction();
          }
        } else { //if can not be seen, attack
          p.attack();
        }
      }
    } else { // if target is not here.
      System.out.println();
      System.out.println(
          String.format(
              "Now you have 4 options:%n1. Move to a neighboring space.%n2. "
                  + "Pick up a weapon if there is any.%n3. "
                  + "Look around.%n4. Moves the cat to a specified space."
                  + "%nWhat do you want to do, %s?",
              player.getName()));
      System.out.println();

      //check if the player is human or computer
      if (player.getTypeOfPlayer() == 0) {
        HumanPlayer p = (HumanPlayer) player;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the corresponding number: ");

        // make sure the input can only be 1, 2, 3,4
        int action;
        while (true) {
          while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number:");
            scanner.next(); // consume the invalid token
          }
          action = scanner.nextInt();
          if (action < 1 || action > 4) {
            System.out.println("Invalid action. Please enter again:");
          } else {
            break;
          }
        }

        if (action == 1) {
          //move to a neighboring space
          p.move();
        } else if (action == 2) {
          //pick up a weapon if there is any
          p.pickUpWeapon();
        } else if (action == 3) {
          //look around
          p.lookAround();
        } else {
          p.moveThePet();
        }
      } else {
        ComputerPlayer p = (ComputerPlayer) player;
        if (p.getCurrentLocation().getWeapons().isEmpty()
            || p.weaponsCarried.size() == p.getMaxNumberOfWeapons()) {
          p.randomActionNoWeapon();
        } else {
          p.randomAction();
        }
      }
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
  public void saveMansionMap() {
    mansion.saveMansionMap();
  }

  /**
   * Displays information about the target.
   */
  @Override
  public void displayTargetInformation() {
    // Display information about the target
    System.out.println("Target Information:");
    System.out.println(String.format("Name: %s", target.getName()));
    System.out.println(
        String.format("Current Location: Room %d, %s.", target.getCurrentLocation().getRoomNumber(),
            target.getCurrentLocation().getRoomName()));
    System.out.println(String.format("Health: %d", target.getHealth()));
    //System.out.println("--------------");
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
    Scanner scan = new Scanner(System.in);

    // check input
    String playerName;
    Player playerDiaplayed = null;
    while (playerDiaplayed == null) {
      playerName = scan.next();
      if (playerName.matches("^[a-zA-Z]+$")) {
        for (Player player : players) {
          if (player.getName().equalsIgnoreCase(playerName)) {
            playerDiaplayed = player;
            break;
          }
        }
        System.out.println("Invalid input. Please enter a valid name:");
      } else {
        System.out.println("Invalid input. Please enter a valid name:");
      }
    }

    //3.Display the player information
    System.out.println();
    System.out.println(String.format("Information of player %s: ", playerDiaplayed.getName()));
    System.out.print(playerDiaplayed.getName());
    playerDiaplayed.displayWeaponInformation();
    System.out.println(String.format("Maximum number of weapons can carry: %d",
        playerDiaplayed.getMaxNumberOfWeapons()));
    if (playerDiaplayed.getTypeOfPlayer() == 0) {
      System.out.println("This is a human player.");
    } else {
      System.out.println("This is a computer player.");
    }
    System.out.println(String.format("Current Location: Room %d, the %s.",
        playerDiaplayed.getCurrentLocation().getRoomNumber(),
        playerDiaplayed.getCurrentLocation().getRoomName()));
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
    int roomNumber;
    while (true) {
      while (!scanner.hasNextInt()) {
        System.out.println("Invalid input. Please enter a valid number:");
        scanner.next(); // consume the invalid token
      }
      roomNumber = scanner.nextInt();
      if (roomNumber < 0 || roomNumber > 21) {
        System.out.println("Invalid number. Please enter again:");
      } else {
        break;
      }
    }

    Room room = mansion.getRoomInfoByRoomNumber(roomNumber);
    System.out.println();

    // 3.Display the room information
    System.out.println(String.format("Room %d information:", roomNumber));
    room.displayWeapons();
    room.displayTarget();
    room.displayPet();
    room.displayPlayers();
  }
}
