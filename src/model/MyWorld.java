package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * This class represents the game "The MyWorld", based on the classical board game
 * Kill Doctor Lucky.
 */
public final class MyWorld implements World {
  private final int startingRoom = 0; //default value
  private final List<Room> initialRoomsState = new ArrayList<>();
  private final int targetInitialHealth;
  int maxNumOfTurns = 100; //default value
  private List<Player> players = new ArrayList<>();
  private List<Room> listOfRooms;
  private String mansionName;
  private int mansionHeight;
  private int mansionWidth;
  private Target target;
  private Pet pet;
  private List<WeaponImp> weapons;
  private int indexOfCurrentPlayer = 0;
  private int numOfTurnsPlayed = 1;
  private BufferedImage map;


  /**
   * Constructs a new "MyWorld" game, initializing the world map from
   * a configuration file.
   *
   * @param conFile The configuration file containing game setup information.
   * @throws IllegalArgumentException if the provided configuration file is invalid.
   */
  public MyWorld(Readable conFile) {
    // read configuration file
    if (conFile == null) {
      throw new IllegalArgumentException("Invalid configuration file.");
    }
    // read and process the configuration file to set up the game world.
    List<String> lines = new ArrayList<>();
    Scanner scan = new Scanner(conFile);
    while (scan.hasNextLine()) {
      lines.add(scan.nextLine());
    }
    scan.close();

    // parse and set up the game components, including target, rooms, and players.
    initializeWeapons(lines);
    initializeRooms(lines);
    initializeMansion(lines);
    initializeTarget(lines);
    initializePet(lines);

    for (Room room : listOfRooms) {
      initialRoomsState.add(new RoomImp(room));
    }
    targetInitialHealth = target.getHealth();

    // describe the world
    worldDescription();
  }

  /**
   * Initializes the list of weapons in the game.
   */
  private void initializeWeapons(List<String> lines) {
    int weaponsCount =
        Integer.parseInt(lines.get(26)); // this line contains the total number of weapons
    int lineIndex = 27;

    weapons = new ArrayList<>();

    // iterate through weapon information lines
    for (int i = 0; i < weaponsCount; i++) {
      String weaponInfo = lines.get(lineIndex);
      String[] weaponData = weaponInfo.split(" ");

      // parse room number, weapon power, and weapon name.
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

      // create a WeaponImp object and add it to the list of weapons.
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
    int roomCount = Integer.parseInt(lines.get(3)); // line 3 contains the room number
    int lineIndex = 4; // starting from line 4 for room data

    listOfRooms = new ArrayList<>();
    for (int i = 0; i < roomCount; i++) {
      String roomInfo = lines.get(lineIndex);
      String[] roomData = roomInfo.split(" ");

      // parse room coordinates and name
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

      Room room =
          new RoomImp(lineIndex - 4, topLeftX, topLeftY, bottomRightX, bottomRightY, roomName,
              listOfWeaponsSpecificRoom);

      // add the room to the list of rooms
      listOfRooms.add(room);

      lineIndex++;
    }
    //initialize the neighbors of the room
    initializeNeighbors(listOfRooms);
  }

  private void initializeNeighbors(List<Room> rooms) {
    for (Room room : rooms) {
      List<Room> neighbors = room.findNeighbors(rooms);
      room.setNeighbors(neighbors);
    }
  }

  private void initializeMansion(List<String> lines) {
    String mansionInfo = lines.get(0); // line 0 contains mansion dimensions and name.
    String[] mansionData = mansionInfo.split(" ");

    // parse mansion dimensions and name.
    mansionWidth = Integer.parseInt(mansionData[0]);
    mansionHeight = Integer.parseInt(mansionData[1]);
    String[] restOfMansionData = new String[mansionData.length - 2];
    System.arraycopy(mansionData, 2, restOfMansionData, 0, mansionData.length - 2);
    mansionName = String.join(" ", restOfMansionData);
    map = getBufferedImage();
  }

  /**
   * Initializes the game's target character,
   * who has initial health and starting location room number.
   */
  private void initializeTarget(List<String> lines) {
    String targetInfo = lines.get(1); // line 1 contains target information
    String[] targetData = targetInfo.split(" ");

    // parse the target's initial health and name
    int targetHealth = Integer.parseInt(targetData[0]);
    String[] restOfTargetData = new String[targetData.length - 1];
    System.arraycopy(targetData, 1, restOfTargetData, 0, targetData.length - 1);
    String targetName = String.join(" ", restOfTargetData);

    // initialize the starting location
    Room currentLocation = listOfRooms.get(startingRoom);

    target = new Target(targetName, targetHealth, currentLocation);
  }

  /**
   * Initializes the target's pet, whose starting location is the same as the target. The space
   * occupied by the pet is not visible to neighbors.
   */
  private void initializePet(List<String> lines) {
    String info = lines.get(2); // line 2 contains pet information.
    String[] infoData = info.split(" ");
    String petName = infoData[0];

    // initialize the starting location
    Room currentLocation = listOfRooms.get(startingRoom);

    pet = new Pet(petName, currentLocation);
  }

  /**
   * Initializes player.
   */
  private void initializePlayer(int indexOfNewPlayer, int typeOfPlayer, String playerName,
                                int startingRoomNumber, int maxNumOfWeapons) {
    Room currentLocation = listOfRooms.get(startingRoomNumber);
    Player player;
    if (typeOfPlayer == 0) {
      player = new HumanPlayer(indexOfNewPlayer, typeOfPlayer, playerName, currentLocation,
          maxNumOfWeapons);
    } else {
      player = new ComputerPlayer(indexOfNewPlayer, typeOfPlayer, playerName,
          currentLocation, maxNumOfWeapons);
    }

    // add the created Player object to the list of players
    players.add(player);
  }

  /**
   * Resets the state of the world when the game is restarted.
   */
  @Override
  public void resetState() {
    players.clear();

    listOfRooms.clear();
    for (Room room : initialRoomsState) {
      listOfRooms.add(new RoomImp(room));
    }

    target.updateLocation(listOfRooms.get(startingRoom));
    target.resetHealth(targetInitialHealth);

    pet.updateLocation(listOfRooms.get(startingRoom));

    numOfTurnsPlayed = 1;
    indexOfCurrentPlayer = 0;
  }

  /**
   * Displays a world description.
   */
  private void worldDescription() {
    //1. describe the mansion by name and number of rooms
    System.out.println("---------------------------------------------------------");
    System.out.println("             Game of The World Description");
    System.out.println("---------------------------------------------------------");
    System.out.printf("Welcome to %s's mansion! There are %d rooms in the mansion, "
            + "and there might be weapons in some rooms.%n",
        target.getName(), listOfRooms.size());
    //2. describe the target by name, health, starting location and rules of moving
    System.out.printf("%s is the target, who has health %d, starts from room %d "
            + "and moves around the mansion.%n",
        target.getName(), target.getHealth(), target.getCurrentLocation().getRoomNumber());
    //3.describe the pet by name, starting location and rules of moving
    System.out.printf("%s has a pet named %s, who starts from the same "
            + "room as the target. %s also moves around the mansion, \n"
            + "but follows a different rule "
            + "from the target. Moreover, the room occupied by %s is not visible to neighbors.%n",
        target.getName(), pet.getName(), pet.getName(), pet.getName());
    //4. describe the player rules by introducing type, starting location, maximum number of
    //   weapons, and different actions.
    System.out.println("After initialization of the World, players can be added to the game, "
        + "specifying name, starting room, and maximum \nnumber of weapons could be carried. There "
        + "could be two types of players: human-controlled and computer-controlled.\n"
        + "Player can choose from these 5 actions:\n1. Move to a neighboring room.\n2. Pick up "
        + "a weapon in the room.\n3. Look around.\n4. Move the pet to a specified space."
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
   * Saves the mansion map as an image file.
   */
  @Override
  public void saveMansionMap() {
    BufferedImage mapImage = getBufferedImage();
    // save the generated map as an image file
    try {
      File outputImageFile = new File("./res/mansion_map.png");
      ImageIO.write(mapImage, "png", outputImageFile);
      System.out.println("mansion_map.png saved successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public BufferedImage getMap() {
    return map;
  }

  /**
   * Creates a graphical representation of the world map.
   */
  private BufferedImage getBufferedImage() {
    int scaleFactor = 40;
    int buffer = 0;
    // create a BufferedImage to represent the map
    BufferedImage mapImage =
        new BufferedImage(mansionWidth * scaleFactor + buffer, mansionHeight * scaleFactor + buffer,
            BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = mapImage.createGraphics();

    // set background color
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, mansionWidth * scaleFactor + buffer, mansionHeight * scaleFactor + buffer);

    // draw rooms on the map
    for (Room room : listOfRooms) {
      drawRoom(g2d, room, scaleFactor);
    }
    return mapImage;
  }

  private void drawRoom(Graphics2D g2d, Room room, int scaleFactor) {
    int roomX = room.getX1() * scaleFactor;
    int roomY = room.getY1() * scaleFactor;
    int roomWidth = (room.getX2() - room.getX1()) * scaleFactor;
    int roomHeight = (room.getY2() - room.getY1()) * scaleFactor;

    // draw the room as a rectangle on the map
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fillRect(roomX, roomY, roomWidth, roomHeight);

    // draw walls around the room
    g2d.setColor(Color.BLACK);
    g2d.drawRect(roomX, roomY, roomWidth, roomHeight);

    Font font = new Font("Arial", Font.PLAIN, 12);
    g2d.setFont(font);
    g2d.setColor(Color.BLACK);

    // draw the room number and name as text
    g2d.setColor(Color.BLACK);
    g2d.drawString("Room " + room.getRoomNumber(), roomX + 10, roomY + 20);
    g2d.drawString(room.getRoomName(), roomX + 10, roomY + 30);
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
    System.out.printf("Human-controlled player %s is added to the game!%n", playerName);
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
    System.out.printf("Computer-controlled player %s is added to the game!%n", playerName);
  }

  @Override
  public int getMaxNumOfTurns() {
    return maxNumOfTurns;
  }

  /**
   * Sets the maximum number of turns.
   */
  @Override
  public void setMaxNumOfTurns(int maxNumOfTurns) {
    this.maxNumOfTurns = maxNumOfTurns;
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

  /**
   * Gets the target.
   *
   * @return the target
   */
  @Override
  public Target getTarget() {
    return target;
  }

  @Override
  public Pet getPet() {
    return pet;
  }


  @Override
  public Player getCurrentPlayer() {
    return players.get(indexOfCurrentPlayer);
  }

  /**
   * Gets the list of players in the world.
   *
   * @return The list of players.
   */
  @Override
  public List<Player> getListOfPlayers() {
    return players;
  }

  /**
   * Gets the list of rooms in the world.
   *
   * @return The list of rooms.
   */
  @Override
  public List<Room> getListOfRooms() {
    return listOfRooms;
  }

  /**
   * Gets the winner when game is over.
   *
   * @return the winner
   */
  @Override
  public Player getWinner() {
    if (isGameOver()) {
      if (indexOfCurrentPlayer == 0) {
        return players.get(players.size() - 1);
      } else {
        return players.get(indexOfCurrentPlayer - 1);
      }
    }
    System.out.println("Game is not over yet.");
    return null;
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
      petWander();
      roundOfTarget();
      String result = roundOfPlayer();
      System.out.printf(result);

      //check health of target, if less than zero, game over and display the winner
      if (target.getHealth() <= 0) {
        System.out.println("GAME OVER!");
        Player winner = getWinner();
        System.out.printf("Player %s wins!%n", winner.getName());
        return;
      }
      System.out.println();
      System.out.printf("Turn %d has ended (%d turns left). And Target "
              + "is now in room %d with health %d.%n", numOfTurnsPlayed,
          maxNumOfTurns - numOfTurnsPlayed, target.getCurrentLocation().getRoomNumber(),
          target.getHealth());
      updateTurnsPlayed();
      System.out.println("--------------");
    }
  }

  @Override
  public void updateTurnsPlayed() {
    numOfTurnsPlayed++;
  }

  /**
   * Plays target move and update target information.
   */
  @Override
  public void roundOfTarget() {
    target.move(listOfRooms);
  }

  @Override
  public void petWander() {
    pet.wander();
  }

  /**
   * Player's turn. Human player can choose from these three actions:
   * 1. move to a neighboring space
   * 2. pick up an item
   * 3. look around
   * 4. move the pet
   * 5. attack the target
   * Computer player randomly chooses an action.
   */
  @Override
  public String roundOfPlayer() {
    Player player;
    player = players.get(indexOfCurrentPlayer);
    System.out.println();
    System.out.printf("It's %s's turn!%n", player.getName());
    System.out.println();

    System.out.println("USEFUL INFORMATION:");
    System.out.print("-> You");
    player.displayWeaponInformation();
    System.out.printf("-> You are now in room %d, the %s%n",
        player.getCurrentLocation().getRoomNumber(),
        player.getCurrentLocation().getRoomName());

    //check if target is in this room
    if (player.getCurrentLocation().isTargetHere()) {
      System.out.println("-> Target is in this room!");
    }

    //check if the pet is in this room
    if (player.getCurrentLocation().isPetHere()) {
      System.out.println("-> The pet is in this room! This room is invisible!!!");
    }

    if (player.getCurrentLocation().isAnyOtherPlayerHere(player)) {
      System.out.println("-> There is other player in this room!");
    }

    //display weapons in the current room
    //player.getCurrentLocation().displayWeapons();

    //display neighbor rooms
    //System.out.println("-> You can move to the following rooms: ");
    //player.getCurrentLocation().displayNeighborsSimple();

    StringBuilder sb = new StringBuilder();
    //ask which action the player choose
    //if target is here, player can attack
    if (player.getCurrentLocation().isTargetHere()) {
      System.out.println();
      System.out.printf(
          "Now you have 5 options:%n1. Move to a neighboring space.%n2. "
              + "Pick up a weapon if there is any.%n3. "
              + "Look around.%n4. Moves the pet to a specified space.%n5. Attack the target."
              + "%nWhat do you want to do, %s?%n",
          player.getName());
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
          p.move(listOfRooms);
        } else if (action == 2) {
          //pick up a weapon if there is any
          p.pickUpWeapon();
        } else if (action == 3) {
          //look around
          System.out.printf(p.lookAround());
        } else if (action == 4) {
          p.moveThePet(pet, listOfRooms);
        } else {
          p.attack(target);
        }
      } else {
        ComputerPlayer p = (ComputerPlayer) player;
        //if can be seen, no attack, randomly choose the other 4 actions
        if (p.canBeSeen()) {
          if (p.getCurrentLocation().getWeapons().isEmpty()
              || p.weaponsCarried.size() == p.getMaxNumberOfWeapons()) {
            sb.append(p.randomActionNoWeapon(pet, listOfRooms));
          } else {
            sb.append(p.randomAction(pet, listOfRooms));
          }
        } else { //if can not be seen, attack
          sb.append(p.attack(target));
        }
      }
    } else { // if target is not here.
      System.out.println();
      System.out.printf(
          "Now you have 4 options:%n1. Move to a neighboring space.%n2. "
              + "Pick up a weapon if there is any.%n3. "
              + "Look around.%n4. Moves the pet to a specified space."
              + "%nWhat do you want to do, %s?%n",
          player.getName());
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
          p.move(listOfRooms);
        } else if (action == 2) {
          //pick up a weapon if there is any
          p.pickUpWeapon();
        } else if (action == 3) {
          //look around
          System.out.printf(p.lookAround());
        } else {
          p.moveThePet(pet, listOfRooms);
        }
      } else {
        ComputerPlayer p = (ComputerPlayer) player;
        if (p.getCurrentLocation().getWeapons().isEmpty()
            || p.weaponsCarried.size() == p.getMaxNumberOfWeapons()) {
          sb.append(p.randomActionNoWeapon(pet, listOfRooms));
        } else {
          sb.append(p.randomAction(pet, listOfRooms));
        }
      }
    }
    updatePlayerTurn();
    return sb.toString();
  }

  @Override
  public void updatePlayerTurn() {
    if (indexOfCurrentPlayer == players.size() - 1) {
      indexOfCurrentPlayer = 0;
    } else {
      indexOfCurrentPlayer++;
    }
  }

  @Override
  public void movePetToRoom(int x, int y) {
    Room room = findRoomByCoordinates(x, y);
    pet.updateLocation(room);
    pet.setMoved();
  }

  @Override
  public void moveToRoom(int x, int y) {
    Room room = findRoomByCoordinates(x, y);
    HumanPlayer player = (HumanPlayer) players.get(indexOfCurrentPlayer);
    if (room.isNeighbor(player.getCurrentLocation())) {
      player.updateLocation(room);
    }
  }

  @Override
  public Room findRoomByCoordinates(int x, int y) {
    for (Room room : listOfRooms) {
      if (room.getX1() <= x && x < room.getX2() && room.getY1() <= y && y < room.getY2()) {
        return room;
      }
    }
    return null;
  }

  /**
   * Displays information about the target.
   */
  @Override
  public String displayTargetInformation() {
    StringBuilder sb = new StringBuilder();

    sb.append("Target Information:\n");
    sb.append(String.format("Name: %s%n", target.getName()));
    sb.append(String.format("Current Location: Room %d, %s.%n",
        target.getCurrentLocation().getRoomNumber(),
        target.getCurrentLocation().getRoomName()));
    sb.append(String.format("Health: %d%n", target.getHealth()));
    return sb.toString();
  }

  /**
   * Displays information about the specified player.
   */
  @Override
  public String displayPlayerInformation(Player playerDiaplayed) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Information of player %s: %n", playerDiaplayed.getName()));
    sb.append(String.format("Maximum number of weapons can carry: %d%n",
        playerDiaplayed.getMaxNumberOfWeapons()));
    if (playerDiaplayed.getTypeOfPlayer() == 0) {
      sb.append("This is a human player.\n");
    } else {
      sb.append("This is a computer player.\n");
    }
    sb.append(String.format("Current Location: Room %d, the %s.%n",
        playerDiaplayed.getCurrentLocation().getRoomNumber(),
        playerDiaplayed.getCurrentLocation().getRoomName()));
    return sb.toString();
  }

  @Override
  public void getPlayerAndDisplay() {
    System.out.println();
    if (players.isEmpty()) {
      System.out.println("No player is added in the game yet.");
      return;
    }

    // 1.display list of players
    System.out.println("List of players: ");
    for (Player player : players) {
      System.out.println(player.getName());
    }
    //2.ask which player to display
    System.out.println();
    System.out.println("Which player do you want to display? Please enter the name: ");
    Scanner scan = new Scanner(System.in);

    // check input
    String playerName;
    Player playerDiaplayed = null;
    while (playerDiaplayed == null) {
      playerName = scan.next();
      if (playerName.matches("^[a-zA-Z]+$")) {
        boolean isPlayerInGame = false;
        for (Player player : players) {
          if (player.getName().equalsIgnoreCase(playerName)) {
            isPlayerInGame = true;
            playerDiaplayed = player;
            break;
          }
        }
        if (!isPlayerInGame) {
          System.out.println("Wrong player name. Please enter a valid name:");
        }
      } else {
        System.out.println("Invalid input. Please enter a valid name:");
      }
    }

    //3.display the player information
    System.out.println();
    System.out.printf(displayPlayerInformation(playerDiaplayed));
  }

  /**
   * Displays the list of rooms in the world.
   */
  @Override
  public void displayListOfRooms() {
    System.out.println("The mansion has the following rooms: ");
    for (Room room : listOfRooms) {
      System.out.printf("%d: %s%n", room.getRoomNumber(), room.getRoomName());
    }
  }

  /**
   * Displays information about a specific room.
   */
  @Override
  public String displayRoomInformation(Room room) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Room %d information:%n", room.getRoomNumber()));
    //sb.append(room.displayWeapons());
    sb.append(room.displayTarget());
    sb.append(room.displayPet());
    sb.append(room.displayPlayers());
    return sb.toString();
  }

  @Override
  public void getRoomAndDisplay() {
    // 1.display list of rooms
    System.out.println();
    displayListOfRooms();
    System.out.println();

    // 2.ask which room to display
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

    Room room = listOfRooms.get(roomNumber);
    System.out.println();

    // 3.display the room information
    System.out.printf(displayRoomInformation(room));
  }
}
