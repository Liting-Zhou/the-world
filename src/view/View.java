package view;

import controller.Features;
import java.awt.image.BufferedImage;
import java.util.List;
import model.Player;
import model.Target;
import model.WeaponImp;

/**
 * This interface represents a view for the game.
 */
public interface View {

  /**
   * Shows the game set up panel.
   */
  void showSetUpPanel();

  /**
   * Shows the game panel.
   *
   * @param b true if the game panel is to be visible, false otherwise
   */
  void displayGamePanel(boolean b);

  /**
   * Sets the display of the View to the given String.
   *
   * @param s the String to be displayed
   */
  void setDisplay(String s);

//  /**
//   * Refresh the view to reflect any changes in the game state.
//   *
//   * @param image   the image to be displayed
//   * @param players the list of players
//   * @param target  the target
//   */
//  void refresh(BufferedImage image, List<Player> players, Target target);
  void refresh();

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard
   * listener attached to it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Get the set of feature callbacks that the view can use.
   *
   * @param f the set of feature callbacks as a Features object
   */
  void setFeatures(Features f);


  /**
   * Shows the weapon dialog for attack.
   *
   * @param weapons the list of weapons
   * @param f       the set of feature callbacks as a Features object
   */
  void showWeaponDialogForAttack(List<WeaponImp> weapons, Features f);

  /**
   * Shows the weapon dialog for pick up.
   *
   * @param weapons the list of weapons
   * @param f       the set of feature callbacks as a Features object
   */
  void showWeaponDialogForPickUp(List<WeaponImp> weapons, Features f);

  /**
   * Shows a message dialog.
   *
   * @param title       the title of the dialog
   * @param information the information to be displayed
   */
  void showMessageDialog(String title, String information);

  /**
   * Shows an input dialog.
   *
   * @param label the label of the input
   * @return the input string
   */
  String showInputDialog(String label);
}
