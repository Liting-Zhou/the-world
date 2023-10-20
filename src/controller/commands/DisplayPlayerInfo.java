package controller.commands;

import controller.Command;
import model.MyWorld;

public final class DisplayPlayerInfo implements Command {

  @Override
  public void execute(MyWorld w) {
    w.displayPlayerInformation();
  }
}
