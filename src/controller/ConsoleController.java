package controller;

import controller.commands.AddComputerPlayer;
import controller.commands.AddHumanPlayer;
import controller.commands.DisplayMap;
import controller.commands.DisplayPlayerInfo;
import controller.commands.DisplayRoomInfo;
import controller.commands.DisplayTargetInfo;
import controller.commands.PlayNextTurn;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.MyWorld;
import model.World;

/**
 * Represents the controller for the game "MyWorld", which employs the command design pattern.
 */
public final class ConsoleController implements Controller {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the output to print
   * @throws IllegalArgumentException for invalid arguments.
   */
  public ConsoleController(Readable in, Appendable out)
      throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Either Readable or Appendable is null");
    }
    this.scan = new Scanner(in);
    this.out = out;
  }

  @Override
  public void playGame(World w) throws IllegalArgumentException, IOException {
    int maxNumOfTurns = scan.nextInt();
    if (maxNumOfTurns <= 0) {
      throw new IllegalArgumentException("Invalid arguments provided.");
    }
    w.setMaxNumOfTurns(maxNumOfTurns);

    Map<Integer, Function<Scanner, Command>> knownCommands = new HashMap<>();
    knownCommands.put(1, s -> new DisplayRoomInfo());
    knownCommands.put(2, s -> new DisplayMap());
    knownCommands.put(3, AddHumanPlayer::new);
    knownCommands.put(4, AddComputerPlayer::new);
    knownCommands.put(5, s -> new PlayNextTurn());
    knownCommands.put(6, s -> new DisplayPlayerInfo());
    knownCommands.put(7, s -> new DisplayTargetInfo());

    Scanner s = new Scanner(System.in);
    System.out.println("Game started!\n***************");

    while (!w.isGameOver() && w.getNumOfTurnsPlayed() <= maxNumOfTurns) {
      printOptions();

      //check valid input
      int option;
      while (true) {
        while (!s.hasNextInt()) {
          System.out.println("Invalid input. Please enter a valid number:");
          s.next(); // consume the invalid token
        }
        option = s.nextInt();
        if (option == 99) {
          break;
        }
        if (option < 1 || option > 7) {
          System.out.println("Invalid option. Please enter again:");
        } else {
          break;
        }
      }

      //user chose to end the game
      if (option == 99) {
        System.out.println("You chose to end the game. Bye!");
        break;
      }

      //otherwise, execute the corresponding command
      Function<Scanner, Command> cmd = knownCommands.getOrDefault(option, null);

      if (cmd != null) {
        Command c = cmd.apply(s);
        c.execute(w);
      }
      System.out.println();
      System.out.println("***************");
      if ((w.getNumOfTurnsPlayed() <= maxNumOfTurns) && (!w.isGameOver())) {
        System.out.println("Game continues.");
      }
    }
    if (!w.isGameOver() && w.getNumOfTurnsPlayed() > maxNumOfTurns) {
      out.append(String.format("Oops! You have run out of the maximum number of turns (%d)! "
          + "GAME OVER!\n", maxNumOfTurns));
    }
  }

  /**
   * Display options for players.
   */
  private void printOptions() {
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
}
