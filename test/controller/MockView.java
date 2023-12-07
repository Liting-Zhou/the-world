package controller;

import java.awt.image.BufferedImage;
import java.util.List;
import model.Player;
import model.Target;
import model.WeaponImp;
import view.View;

/**
 * This class represents a mock view for testing.
 */
public class MockView implements View {
  private StringBuilder log;

  public MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void setFeatures(Features f) {
    log.append("view.setFeatures is invoked\n");
  }

  @Override
  public void showSetUpPanel() {
    log.append("view.showSetUpPanel is invoked\n");
  }

  @Override
  public void displayGamePanel(boolean b) {
    log.append("view.displayGamePanel is invoked\n");
  }

  @Override
  public void setDisplay(String s) {
    log.append("view.setDisplay is invoked\n");
  }

  @Override
  public void refresh() {
    log.append("view.refresh is invoked\n");
  }

  @Override
  public void resetFocus() {
    log.append("view.resetFocus is invoked\n");
  }

  @Override
  public void showWeaponDialogForAttack(Features f) {
    log.append("view.showWeaponDialogForAttack is invoked\n");
  }

  @Override
  public void showWeaponDialogForPickUp(List<WeaponImp> weapons, Features f) {
    log.append("view.showWeaponDialogForPickUp is invoked\n");
  }

  @Override
  public void showMessageDialog(String title, String information) {
    log.append("view.showMessageDialog is invoked\n");
  }

  @Override
  public String showInputDialog(String label) {
    log.append("view.showInputDialog is invoked\n");
    return "text return from view.showInputDialog()\n";
  }
}
