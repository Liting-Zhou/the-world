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
    w.addComputerPlayer(name);
    System.out.println("***************");
  }
}
