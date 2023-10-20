package controller.commands;

import controller.Command;
import java.util.Scanner;
import model.MyWorld;

public final class AddHumanPlayer implements Command {
  private Scanner scan;

  public AddHumanPlayer(Scanner s) {
    this.scan = s;
  }

  @Override
  public void execute(MyWorld w) {
    System.out.println("Please enter the name: ");
    String name = scan.next();
    System.out.println("Choose a room to start from. Enter the room number (0-21): ");
    int roomNumber = scan.nextInt();
    System.out.println("The maximum number of weapons this player can carry is: ");
    int maxNumOfWeapons = scan.nextInt();
    w.addHumanPlayer(name, roomNumber,maxNumOfWeapons);
    System.out.println("***************");
  }
}
