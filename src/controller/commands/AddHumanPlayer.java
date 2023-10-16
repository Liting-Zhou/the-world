package controller.commands;

import controller.Command;
import model.World;

public class AddHumanPlayer implements Command {
  private String name;

  public AddHumanPlayer(String name) {
    this.name = name;
  }

  @Override
  public void execute(World w) {
    System.out.println("Please enter the name: ");
    w.addHumanPlayer(name);
    System.out.println("***************");
  }
}
