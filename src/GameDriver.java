import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The main class that drives and controls the execution of the game "The World".
 * This class initializes the game world, runs the game loop, and determines the winner.
 */
public class GameDriver {
  /**
   * The main method that starts the game.
   *
   * @param args Command-line arguments containing player names.
   */
  public static void main(String[] args) {
//    List<String> listOfPlayerNames = new ArrayList<>();
//    for (String arg : args) {
//      listOfPlayerNames.add(arg);
//    }

    // Create and initialize the game world
    //World world = initializeGameWorld("./res/mansion.txt", listOfPlayerNames);
    World world = initializeGameWorld("./res/mansion.txt");
    System.out.println("Game started!\nIn each round, target moves first, "
        + "and then one player can act.\nEveryone starts from room 16.\n***************");

    Scanner scanner = new Scanner(System.in);
    while (!world.ifGameOver()) {
      printOptions();
      int option = scanner.nextInt();
      switch (option) {
        case 1:
          world.displayRoomInformation();
          break;
        case 2:
          world.displayMap();
          break;
        case 3:
          // Add a human-controlled player to the game
          System.out.println("Please enter the name: ");
          String humanPlayerName = scanner.next();
          world.addHumanPlayer(humanPlayerName);
          System.out.println("***************");
          break;
        case 4:
          // Add a computer-controlled player to the game
          System.out.println("Please enter the name: ");
          String computerPlayerName = scanner.next();
          world.addComputerPlayer(computerPlayerName);
          System.out.println("***************");
          break;


        case 10:
          world.displayTargetInformation();
          break;
        case 11:
          world.playNextRound();
          if (world.ifGameOver()) {
            System.out.println("Game over!");
            System.out.println("The winner is " + world.getWinner().getName());
          }
          break;
        default:
          System.out.println("Invalid option.");
      }
    }
  }

  /**
   * Display options for players.
   */
  private static void printOptions() {
    System.out.println("You have the following options:");
    System.out.println("1. Get information about a specified room.");
    System.out.println("2. Generate the mansion_map.png.");
    System.out.println("3. Add a human-controlled player to the game.");
    System.out.println("4. Add a computer-controlled player to the game.");


    System.out.println("10. Get information about the target.");
    System.out.println("11. Continue the game.");

    System.out.println("Please choose an option (enter the corresponding number): ");
  }

  /**
   * Initializes the game world based on a configuration file and a list of player names.
   *
   * @param configFile The path to the configuration file.
   * @return The initialized game world.
   */
  private static World initializeGameWorld(String configFile) {
    try {
      // Create a BufferedReader to read the configuration file
      Readable conFile = new BufferedReader(new FileReader(configFile));

      // Initialize the game world using the configuration file
      return new World(conFile);
    } catch (IOException e) {
      System.err.println("Error reading configuration file: " + e.getMessage());
      return null;
    }
  }
}
