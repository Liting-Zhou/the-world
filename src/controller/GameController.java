package controller;

import model.World;

import java.io.IOException;
import java.util.Scanner;

public class GameController implements Controller {

  private final Appendable out;
  private final Scanner scan;
  private final World w;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the output to print
   * @throws IllegalArgumentException for invalid arguments.
   */
  public GameController(Readable in, Appendable out,World world) throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Either Readable or Appendable is null");
    }
    this.out = out;
    scan = new Scanner(in);
    this.w=world;
  }


  @Override
  public void playGame(World w, int maxNumOfTurns) throws IllegalArgumentException {
    if (w == null || maxNumOfTurns <= 0) {
      throw new IllegalArgumentException("Invalid arguments provided.");
    }
    //TODO implement the controller
  }
}
