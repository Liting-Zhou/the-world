import controller.Controller;
import controller.ConsoleController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import model.MyWorld;

/**
 * The Driver class handles the command-line arguments and gives control to the controller.
 */
public final class Driver {
  /**
   * Runs the driver class.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
      String configFile = args[0];
      String maxNumOfTurns = args[1];
      Readable reader = new BufferedReader(new FileReader(configFile));
      MyWorld world = new MyWorld(reader);

      Readable readable = new BufferedReader(new StringReader(maxNumOfTurns));
      Appendable appendable = new StringBuilder();

      //create a controller
      Controller gameController = new ConsoleController(readable, appendable);

      //pass control to the controller
      gameController.playGame(world);
      //print the output
      System.out.println(appendable.toString());

    } catch (IllegalArgumentException e) {
      System.out.println("Illegal argument exception raised" + e);
      System.exit(1);
    } catch (FileNotFoundException e) {
      System.out.println("File not found exception raised");
      System.exit(1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}


