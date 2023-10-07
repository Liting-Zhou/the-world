import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    List<String> listOfPlayerNames = new ArrayList<>();
    for (String arg : args) {
      listOfPlayerNames.add(arg);
    }

    // Create and initialize the game world
    World world = initializeGameWorld("./res/mansion.txt", listOfPlayerNames);
    System.out.println("Game started!");

    System.out.println("In each round, target moves first, and then one player can act.");
    System.out.println("The target starts from room 16.");
    // Run the game loop
    while (!world.ifGameOver()) {
      // Perform game actions for each round
      world.playNextRound();
    }

    // Determine the winner and display the result
    System.out.println(world.getWinner().getName());
  }

  /**
   * Initializes the game world based on a configuration file and a list of player names.
   *
   * @param configFile        The path to the configuration file.
   * @param listOfPlayerNames The list of player names.
   * @return The initialized game world.
   */
  private static World initializeGameWorld(String configFile, List<String> listOfPlayerNames) {
    try {
      // Create a BufferedReader to read the configuration file
      Readable conFile = new BufferedReader(new FileReader(configFile));

      // Initialize the game world using the configuration file
      return new World(conFile, listOfPlayerNames);
    } catch (IOException e) {
      System.err.println("Error reading configuration file: " + e.getMessage());
      return null;
    }
  }
}
