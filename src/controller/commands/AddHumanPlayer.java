package controller.commands;

import controller.Command;
import java.util.Scanner;
import model.World;

public final class AddHumanPlayer implements Command {
  private Scanner scan;

  public AddHumanPlayer(Scanner s) {
    this.scan = s;
  }

  @Override
  public void execute(World w) {
    System.out.println("Please enter the name: ");
    String name = scan.next();
    w.addHumanPlayer(name);
    System.out.println("***************");
  }
}
