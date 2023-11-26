package view;

import controller.Features;

public interface View {

  /**
   * Sets the display of the View to the given String.
   *
   * @param s the String to be displayed
   */
  void setDisplay(String s);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
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
}
