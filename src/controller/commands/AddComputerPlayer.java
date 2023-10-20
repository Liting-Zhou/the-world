package controller.commands;

import controller.Command;
import java.util.Scanner;
import model.MyWorld;

public final class AddComputerPlayer implements Command {

  private Scanner scan;

  public AddComputerPlayer(Scanner s) {
    this.scan = s;
  }

  @Override
  public void execute(MyWorld w) {
    System.out.println("Please enter the name: ");
    String name = scan.next();
    System.out.println("Which room do you want to start from? Enter the room number (0-21): ");
    int roomNumber = scan.nextInt();
    w.addComputerPlayer(name,roomNumber);
    System.out.println("***************");
  }
}
