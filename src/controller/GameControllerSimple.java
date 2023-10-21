package controller;

import java.io.IOException;
import java.util.Scanner;
import model.MyWorld;

public final class GameControllerSimple implements Controller {

  private final Appendable out;
  private final Scanner scan;
  private final MyWorld world;

  /**
   * Constructor for the controller.
   *
   * @param in    the source to read from
   * @param out   the output to print
   * @param world the world model to use
   * @throws IllegalArgumentException for invalid arguments.
   */
  public GameControllerSimple(Readable in, Appendable out, MyWorld world)
      throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Either Readable or Appendable is null");
    }
    this.world = world;
    scan = new Scanner(in);
    this.out = out;
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
    System.out.println("5. Play next round."); //Move a player.
    System.out.println("6. Get information about a specified player."); //Move a player.
    System.out.println("7. Get information about the target.");
    System.out.println();
    System.out.println("Please choose an option (enter the corresponding number): ");
  }

  @Override
  public void playGame(MyWorld w) throws IllegalArgumentException, IOException {
    int maxNumOfTurns = scan.nextInt();
    if (w == null || maxNumOfTurns <= 0) {
      throw new IllegalArgumentException("Invalid arguments provided.");
    }

    Scanner s = new Scanner(System.in);
    System.out.println("Game started!\nIn each turn, target moves first, "
        + "and then one player can act.\nEveryone starts from room 16.\n***************");

    int numOfTurns = 1;
    while (!world.ifGameOver() && numOfTurns <= maxNumOfTurns) {
      printOptions();
      int option = s.nextInt();
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
          String humanPlayerName = s.next();
          System.out.println(
              "Which room do you want to start from? Enter the room number (0-21): ");
          int roomNumber1 = s.nextInt();
          world.addHumanPlayer(humanPlayerName, roomNumber1);
          System.out.println("***************");
          break;
        case 4:
          // Add a computer-controlled player to the game
          System.out.println("Please enter the name: ");
          String computerPlayerName = s.next();
          System.out.println(
              "Which room do you want to start from? Enter the room number (0-21): ");
          int roomNumber2 = s.nextInt();
          world.addComputerPlayer(computerPlayerName, roomNumber2);
          System.out.println("***************");
          break;
        case 5:
          world.playNextRound();
          if (world.ifGameOver()) {
            out.append("Game over!");
            out.append(String.format("The winner is %s", w.getWinner().getName()));
          }
          break;
        case 6:
          world.displayPlayerInformation();
          break;
        case 7:
          world.displayTargetInformation();
          break;

        default:
          System.out.println("Invalid option.");
      }
      numOfTurns += 1;
    }
    if (!world.ifGameOver() && numOfTurns > maxNumOfTurns) {
      out.append("You run out of the maximum number of turns! Game over!");
    }
  }
}
