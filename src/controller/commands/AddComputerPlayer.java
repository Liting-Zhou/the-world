package controller.commands;

import controller.Command;
import java.util.Scanner;
import model.MyWorld;

/**
 * Represents the command to add a computer player to the game.
 */
public final class AddComputerPlayer implements Command {

  private Scanner scan;

  /**
   * Adds a computer player to the game.
   *
   * @param s The scanner to read input from
   */
  public AddComputerPlayer(Scanner s) {
    this.scan = s;
  }

  @Override
  public void execute(MyWorld w) {
    System.out.println("Please enter the name: ");

    //make sure the name only contains letters
    String name;
    while (true) {
      name = scan.next();
      if (name.matches("^[a-zA-Z]+$")) {
        break;
      } else {
        System.out.println("Invalid input. Please enter a name containing only letters.");
      }
    }

    System.out.println("Choose a room to start from. Enter the room number (0-21): ");
    //make sure input is valid
    int roomNumber;
    while (true) {
      while (!scan.hasNextInt()) {
        System.out.println("Invalid input. Please enter a valid number.");
        scan.next(); // consume the invalid token
      }
      roomNumber = scan.nextInt();
      if (roomNumber < 0 || roomNumber > 21) {
        System.out.println("Invalid number. Please enter again.");
      } else {
        break;
      }
    }

    System.out.println("The maximum number of weapons this player can carry is: ");
    //make sure input is valid
    int maxNumOfWeapons;
    while (true) {
      while (!scan.hasNextInt()) {
        System.out.println("Invalid input. Please enter a valid number:");
        scan.next(); // consume the invalid token
      }
      maxNumOfWeapons = scan.nextInt();
      // for now, assume the limit is 5
      if (maxNumOfWeapons < 0 || maxNumOfWeapons > 5) {
        System.out.println("Invalid number. Please enter again:");
      } else {
        break;
      }
    }

    w.addComputerPlayer(name, roomNumber, maxNumOfWeapons);
    //System.out.println("***************");
  }
}
