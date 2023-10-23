package controller;

import java.io.IOException;
import java.util.Scanner;
import model.MyWorld;

/**
 * Represents the controller for the game "MyWorld", without employing command design pattern.
 */
public final class GameControllerSimple implements Controller {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the output to print
   * @throws IllegalArgumentException for invalid arguments.
   */
  public GameControllerSimple(Readable in, Appendable out)
      throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Either Readable or Appendable is null");
    }
    scan = new Scanner(in);
    this.out = out;
  }

  /**
   * Display options for players.
   */
  private static void printOptions() {
    System.out.println("Options:");
    System.out.println("1. Display information about a specified room.");
    System.out.println("2. Generate the mansion_map.png.");
    System.out.println("3. Add a human-controlled player to the game.");
    System.out.println("4. Add a computer-controlled player to the game.");
    System.out.println("5. Play next turn."); //player can move, pick up weapon, look around
    System.out.println("6. Display a description of a specified player.");
    System.out.println("7. Display a description of the target.");
    System.out.println("99. Quit the game.");
    System.out.println();
    System.out.println("Please choose an option (enter the corresponding number): ");
  }

  @Override
  public void playGame(MyWorld w) throws IllegalArgumentException, IOException {
    int maxNumOfTurns = scan.nextInt();
    if (w == null || maxNumOfTurns <= 0) {
      throw new IllegalArgumentException("Invalid arguments provided.");
    }
    w.setMaxNumOfTurns(maxNumOfTurns);

    Scanner s = new Scanner(System.in);
    System.out.println("Game started!\nIn each turn, target moves first, "
        + "and then one player can act.\nTarget starts from room 0.\n***************");

    while (!w.ifGameOver() && w.getNumOfTurnsPlayed() <= maxNumOfTurns) {
      printOptions();
      int option = s.nextInt();
      if (option == 99) {
        System.out.println("Bye!");
        break;
      }
      switch (option) {
        case 1:
          w.displayRoomInformation();
          break;
        case 2:
          w.SaveMansionMap();
          break;
        case 3:
          // Add a human-controlled player to the game
          System.out.println("Please enter the name: ");
          String humanPlayerName = s.next();
          System.out.println("Choose a room to start from. Enter the room number (0-21): ");
          int roomNumber1 = s.nextInt();
          System.out.println("The maximum number of weapons this player can carry is: ");
          int maxNumOfWeapons1 = s.nextInt();
          w.addHumanPlayer(humanPlayerName, roomNumber1, maxNumOfWeapons1);
          System.out.println("***************");
          break;
        case 4:
          // Add a computer-controlled player to the game
          System.out.println("Please enter the name: ");
          String computerPlayerName = s.next();
          System.out.println("Choose a room to start from. Enter the room number (0-21): ");
          int roomNumber2 = s.nextInt();
          System.out.println("The maximum number of weapons this player can carry is: ");
          int maxNumOfWeapons2 = s.nextInt();
          w.addComputerPlayer(computerPlayerName, roomNumber2, maxNumOfWeapons2);
          System.out.println("***************");
          break;
        case 5:
          w.playNextTurn();
          if (w.ifGameOver()) {
            out.append("Game over!");
            out.append(String.format("The winner is %s", w.getWinner().getName()));
          }
          break;
        case 6:
          w.displayPlayerInformation();
          break;
        case 7:
          w.displayTargetInformation();
          break;

        default:
          System.out.println("Invalid option.");
      }
    }
    if (!w.ifGameOver() && w.getNumOfTurnsPlayed() > maxNumOfTurns) {
      out.append(String.format("You have run out of the maximum number of turns (%d)! Game over!",
          maxNumOfTurns));
    }
  }
}
