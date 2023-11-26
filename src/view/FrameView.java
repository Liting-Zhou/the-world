package view;

import controller.Features;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FrameView extends JFrame implements View {

  private final JLabel display;
  private final JTextField input;
  private final JButton enterButton;
  private final JButton addPlayerButton;
  private final JMenuItem newGameNewWorldItem;
  private final JMenuItem newGameCurrentWorldItem;
  private final JMenuItem quitItem;

  /**
   * Constructor.
   **/
  public FrameView() {
    super("Game of Kill Doctor Happy");

    setSize(500, 500);
    setLocation(20, 20);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // create a menu bar
    JMenuBar menuBar = new JMenuBar();
    // create a menu
    JMenu fileMenu = new JMenu("Menu");

    newGameNewWorldItem = new JMenuItem("Start with a new world specification");
    newGameCurrentWorldItem = new JMenuItem("Start with the current world specification");
    quitItem = new JMenuItem("Quit the game");

    // add menu items to the menu
    fileMenu.add(newGameNewWorldItem);
    fileMenu.add(newGameCurrentWorldItem);
    fileMenu.add(quitItem);

    menuBar.add(fileMenu);
    //add the menu bar to the frame
    setJMenuBar(menuBar);

    this.setLayout(new FlowLayout());

    display = new JLabel("To be displayed");
    this.add(display);

    // the text field
    input = new JTextField(10);
    this.add(input);

    // enter button
    enterButton = new JButton("Enter");
    //enterButton.setActionCommand("Enter Button");
    this.add(enterButton);

    addPlayerButton = new JButton("Add Player");
    this.add(addPlayerButton);

    pack();
    setVisible(true);
  }

  @Override
  public void setFeatures(Features f) {
    //newGameNewWorldItem.addActionListener(); //TODO: what is the logic here?
    newGameCurrentWorldItem.addActionListener(l -> {
      f.playGame();
    });
    quitItem.addActionListener(l -> {
      f.exitGame();
    });
    enterButton.addActionListener(l -> {
      f.processInput(input.getText());
    });
    addPlayerButton.addActionListener(l -> {
      new PlayerInputDialog(this, "Add Player", true, f).setVisible(true);
    });

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        //do nothing
      }

      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_K:
            f.attack();
            break;
          case KeyEvent.VK_P:
            f.pickUpItem();
            break;
          case KeyEvent.VK_L:
            f.lookAround();
            break;
          case KeyEvent.VK_M:
            f.moveThePet();
            break;
          case KeyEvent.VK_ENTER:
            f.processInput(input.getText());
            break;
          default:
            break;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        //do nothing
      }
    });

    this.addMouseListener(new ClickListener(f));
  }

  @Override
  public void setDisplay(String s) {
    display.setText(s);
  }

  @Override
  public void refresh() {

  }

  /*
   * In order to make this frame respond to keyboard events, it must be within
   * strong focus. Since there could be multiple components on the screen that
   * listen to keyboard events, we must set one as the "currently focussed" one so
   * that all keyboard events are passed to that component. This component is said
   * to have "strong focus".
   *
   * We do this by first making the component focusable and then requesting focus
   * to it. Requesting focus makes the component have focus AND removes focus from
   * whoever had it before.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * Inner class for handling clicks on the game board
   */
  private class ClickListener extends MouseAdapter {

    private final Features controller;

    public ClickListener(Features c) {
      this.controller = c;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      // convert the mouse coordinates to game board coordinates
      int x = e.getX();
      int y = e.getY();

      //TODO: implement the logic to capture a click
      //1. player gets clicked, display player's info
      //2. target gets clicked, display target's info
      //3. A specific room gets clicked
      //3.1 in the case of player choosing move, move the player to the room
      //3.2 otherwise, display the room's info
    }
  }

  /**
   * Inner class for handling player input dialog
   */
  private class PlayerInputDialog extends JDialog {
    private JTextField playerNameField;
    private JTextField startingRoomField;
    private JTextField weaponLimitsField;
    private JRadioButton humanRadioButton;
    private JRadioButton computerRadioButton;
    private int playerType;
    private Features f;

    public PlayerInputDialog(JFrame parent, String title, boolean modal, Features feature) {
      super(parent, title, modal);
      setSize(300, 200);
      setLocationRelativeTo(parent);
      f = feature;
      initUI();
    }

    private void initUI() {
      setLayout(new FlowLayout());

      playerNameField = new JTextField(15);
      startingRoomField = new JTextField(15);
      weaponLimitsField = new JTextField(15);

      humanRadioButton = new JRadioButton("human-controlled");
      computerRadioButton = new JRadioButton("computer-controlled");
      ButtonGroup playerTypeGroup = new ButtonGroup(); //make them a group to be exclusive
      playerTypeGroup.add(humanRadioButton);
      playerTypeGroup.add(computerRadioButton);

      humanRadioButton.addActionListener(k -> {
        playerType = 0; // human
      });
      computerRadioButton.addActionListener(l -> {
        playerType = 1; // computer
      });

      JButton addButton = new JButton("Add");
      addButton.addActionListener(l -> {
        f.addPlayer(getPlayerName(), getStartingRoom(), getWeaponLimits(), getPlayerType());
        dispose();
      });

      add(new JLabel("Player Name:"));
      add(playerNameField);
      add(new JLabel("Starting Room (0-21):"));
      add(startingRoomField);
      add(new JLabel("Weapon Limits(0-5):"));
      add(weaponLimitsField);
      add(new JLabel("Player Type:"));
      add(humanRadioButton);
      add(computerRadioButton);
      add(addButton);
    }

    public String getPlayerName() {
      return playerNameField.getText();
    }

    public int getStartingRoom() {
      return Integer.parseInt(weaponLimitsField.getText());
    }

    public int getWeaponLimits() {
      return Integer.parseInt(weaponLimitsField.getText());
    }

    public int getPlayerType() {
      return playerType;
    }
  }
}
