package controller;

import controller.commands.AddComputerPlayer;
import controller.commands.AddHumanPlayer;
import controller.commands.DisplayMap;
import controller.commands.DisplayPlayerInfo;
import controller.commands.DisplayRoomInfo;
import controller.commands.DisplayTargetInfo;
import controller.commands.PlayNextRound;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.MyWorld;

/**
 * Represents the controller for the game "MyWorld", which employs the command design pattern.
 */
public final class GameControllerCommands implements Controller {
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
  public GameControllerCommands(Readable in, Appendable out, MyWorld world)
      throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Either Readable or Appendable is null");
    }
    this.world = world;
    this.scan = new Scanner(in);
    this.out = out;
  }

  @Override
  public void playGame(MyWorld w) throws IllegalArgumentException, IOException {
    int maxNumOfTurns = scan.nextInt();
    if (maxNumOfTurns <= 0) {
      throw new IllegalArgumentException("Invalid arguments provided.");
    }

    Map<Integer, Function<Scanner, Command>> knownCommands = new HashMap<>();
    knownCommands.put(1, s -> new DisplayRoomInfo());
    knownCommands.put(2, s -> new DisplayMap());
    knownCommands.put(3, AddHumanPlayer::new);
    knownCommands.put(4, AddComputerPlayer::new);
    knownCommands.put(5, s -> new PlayNextRound(out));
    knownCommands.put(6, s -> new DisplayPlayerInfo());
    knownCommands.put(7, s -> new DisplayTargetInfo());

    Scanner s = new Scanner(System.in);
    System.out.println("Game started!\nIn each turn, target moves first, "
        + "and then one player can act.\nTarget starts from room 16.\n***************");

    int numOfTurns = 1;
    while (!world.ifGameOver() && numOfTurns <= maxNumOfTurns) {
      printOptions();
      int option = s.nextInt();
      Function<Scanner, Command> cmd = knownCommands.getOrDefault(option, null);

      if (cmd != null) {
        Command c = cmd.apply(s);
        c.execute(w);
      } else {
        System.out.println("Invalid option.");
      }
      numOfTurns += 1;
    }
    if (!world.ifGameOver() && numOfTurns > maxNumOfTurns) {
      out.append("You have run out of the maximum number of turns! Game over!");
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

    System.out.println("Please choose an option (enter the corresponding number): ");
  }
}
