import controller.ConsoleController;
import controller.Controller;
import controller.VisualController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import model.MyWorld;
import model.World;
import view.FrameView;
import view.View;

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
      int maxNumOfTurns = Integer.parseInt(args[1]);
      if (maxNumOfTurns <= 0) {
        throw new IllegalArgumentException("Maximum number of turns should be positive.");
      }
      int mode = Integer.parseInt(args[2]);
      if (mode != 1 && mode != 2) {
        throw new IllegalArgumentException("Mode should be either 1 (GUI) or 2 (text-based).");
      }
      Readable reader = new BufferedReader(new FileReader(configFile));
      //create model
      World world = new MyWorld(reader);
      world.setMaxNumOfTurns(maxNumOfTurns);

      if (mode == 2) {
        //play with console
        Readable readable = new StringReader("");
        Appendable appendable = new StringBuilder();

        //create a controller
        Controller gameController = new ConsoleController(readable, appendable);

        //pass control to the controller
        gameController.playGame(world);
        //print the output
        System.out.println(appendable);
      } else {
        // play with GUI
        View view = new FrameView();
        VisualController controller = new VisualController(world);
        controller.setView(view);
      }

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


