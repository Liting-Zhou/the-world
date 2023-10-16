package controller;

import controller.commands.AddHumanPlayer;
import controller.commands.DisplayMap;
import controller.commands.DisplayRoomInfo;
import java.util.function.Function;
import model.World;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class GameControllerCommands implements Controller{
  private final Appendable out;
  private final Scanner scan;
  private final World world;

  public GameControllerCommands(Readable in, Appendable out,World world) {
    this.world = world;
    this.scan = new Scanner(in);
    this.out = out;
    //setupCommands();
  }

  //private void setupCommands() {

//    knownCommands.put(4, w -> {
//      System.out.println("Please enter the name: ");
//      String computerPlayerName = scan.next();
//      world.addComputerPlayer(computerPlayerName);
//      System.out.println("***************");
//    });
//    knownCommands.put(5, w -> {
//      world.playNextRound();
//      if (world.ifGameOver()) {
//        System.out.println("Game over!");
//        System.out.println("The winner is " + world.getWinner().getName());
//      }
//    });
//    knownCommands.put(6, w -> world.displayPlayerInformation());
//    knownCommands.put(7, w -> world.displayTargetInformation());
 // }

  public void playGame(World w) throws IllegalArgumentException, IOException {
    int maxNumOfTurns= scan.nextInt();
    if (maxNumOfTurns <= 0) {
      throw new IllegalArgumentException("Invalid arguments provided.");
    }

    Stack<Command> commands = new Stack<>();
    Map<Integer, Function<Scanner, Command>> knownCommands = new HashMap<>();
    knownCommands.put(1, s -> new DisplayRoomInfo());
    knownCommands.put(2, s -> new DisplayMap());
    knownCommands.put(3, (Scanner s) -> {return new AddHumanPlayer(s.next());});

    System.out.println("Game started!\nIn each round, target moves first, "
        + "and then one player can act.\nEveryone starts from room 16.\n***************");

    int numOfTurns = 1;
    while (!world.ifGameOver() && numOfTurns <= maxNumOfTurns) {
      printOptions();
      int option = scan.nextInt();
      Function<Scanner, Command> cmd = knownCommands.getOrDefault(option, null);
      
        if (cmd != null) {
          Command c = cmd.apply(scan);
          commands.add(c);
          c.execute(w);
        } else {
            System.out.println("Invalid option.");
        }
      numOfTurns += 1;
    }
    if (!world.ifGameOver() && numOfTurns > maxNumOfTurns) {
      System.out.println("You have run out of the maximum number of turns! Game over!");
    }
  }

  private void printOptions() {
    System.out.println("You have the following options:");
    System.out.println("1. Get information about a specified room.");
    System.out.println("2. Generate the mansion_map.png.");
    System.out.println("3. Add a human-controlled player to the game.");
    System.out.println("4. Add a computer-controlled player to the game.");
    System.out.println("5. Continue the game."); // Move a player.
    System.out.println("6. Get information about a specified player."); // Move a player.
    System.out.println("7. Get information about the target.");

    System.out.println("Please choose an option (enter the corresponding number): ");
  }
}
