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
import model.World;

public final class GameControllerCommands implements Controller {
  private final Appendable out;
  private final Scanner scan;
  private final World world;

  /**
   * Constructor for the controller.
   *
   * @param in    the source to read from
   * @param out   the output to print
   * @param world the world model to use
   * @throws IllegalArgumentException for invalid arguments.
   */
  public GameControllerCommands(Readable in, Appendable out, World world)
      throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Either Readable or Appendable is null");
    }
    this.world = world;
    this.scan = new Scanner(in);
    this.out = out;
  }

  @Override
  public void playGame(World w) throws IllegalArgumentException, IOException {
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
    System.out.println("Game started!\nIn each round, target moves first, "
        + "and then one player can act.\nEveryone starts from room 16.\n***************");

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
    System.out.println("You have the following options:");
    System.out.println("1. Get information about a specified room.");
    System.out.println("2. Generate the mansion_map.png.");
    System.out.println("3. Add a human-controlled player to the game.");
    System.out.println("4. Add a computer-controlled player to the game.");
    System.out.println("5. Play next round.");
    System.out.println("6. Get information about a specified player."); // Move a player.
    System.out.println("7. Get information about the target.");

    System.out.println("Please choose an option (enter the corresponding number): ");
  }
}
