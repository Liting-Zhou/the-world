import controller.Controller;
import controller.GameController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import model.World;

/**
 * The Driver class handles the command-line arguments and gives control to the controller.
 */
public class Driver {
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
      World world = new World(reader);

      Readable readable = new BufferedReader(new StringReader(maxNumOfTurns));
      Appendable appendable = new StringBuilder();
      Controller gameController = new GameController(readable, appendable, world);
      gameController.playGame(world, Integer.parseInt(maxNumOfTurns));
      System.out.println(appendable.toString());

    } catch (IllegalArgumentException e) {
      System.out.println("Illegal argument exception raised" + e);
      System.exit(1);
    } catch (FileNotFoundException e) {
      System.out.println("File not found exception raised");
      System.exit(1);
    }
  }
}


