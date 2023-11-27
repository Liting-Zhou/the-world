package view;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import model.Player;
import model.Room;
import model.Target;
import javax.swing.SwingUtilities;

public class FrameView extends JFrame implements View {

  private final JLabel display;
  private final JButton addPlayerButton;
  private final JButton finishSetUpButton;
  private final JPanel buttonPanel;
  private final JMenuItem newGameNewWorldItem;
  private final JMenuItem newGameCurrentWorldItem;
  private final JMenuItem quitItem;
  private final GameBoardPanel gameBoard;
  private final JScrollPane scrollPane;

  /**
   * Constructor.
   **/
  public FrameView() {
    super("Game of Kill Doctor Happy");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

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

    display = new JLabel("To be displayed");
    this.add(display, BorderLayout.PAGE_START);
    display.setVisible(false);

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    addPlayerButton = new JButton("Add Player");
    buttonPanel.add(addPlayerButton);
    finishSetUpButton = new JButton("Start Game");
    buttonPanel.add(finishSetUpButton);
    add(buttonPanel, BorderLayout.PAGE_END);
    buttonPanel.setVisible(false);

    //map display
    gameBoard = new GameBoardPanel();
    scrollPane = new JScrollPane(gameBoard);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    add(scrollPane, BorderLayout.CENTER);
    scrollPane.setVisible(false);

    setMinimumSize(new Dimension(300, 300));
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  @Override
  public void setFeatures(Features f) {
    //newGameNewWorldItem.addActionListener(); //TODO: what is the logic here?
    newGameCurrentWorldItem.addActionListener(l -> {
      f.gameSetUp();
    });
    quitItem.addActionListener(l -> {
      f.exitGame();
    });
    addPlayerButton.addActionListener(l -> {
      new PlayerInputDialog(this, "Add Player", true, f).setVisible(true);
    });
    finishSetUpButton.addActionListener(l -> {
      buttonPanel.setVisible(false);
      f.enterGame();
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
  public void showSetUpPanel() {
    buttonPanel.setVisible(true);
  }

  @Override
  public void displayGamePanel() {
    scrollPane.setVisible(true);
  }

  @Override
  public void setDisplay(String s) {
    display.setText(s);
    display.setVisible(true);
  }

  @Override
  public void refresh(BufferedImage image, List<Player> players, Target target) {
    gameBoard.updateMap(image, players, target);
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
   * Inner class for handling clicks on the game board.
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
   * Inner class for handling player input dialog.
   */
  private class PlayerInputDialog extends JDialog {
    private final Features f;
    private JTextField playerNameField;
    private JTextField startingRoomField;
    private JTextField weaponLimitsField;
    private JRadioButton humanRadioButton;
    private JRadioButton computerRadioButton;
    private int playerType;

    public PlayerInputDialog(JFrame parent, String title, boolean modal, Features feature) {
      super(parent, title, modal);
      setSize(500, 500);
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

  /**
   * Inner class for the map panel.
   */
  private class GameBoardPanel extends JPanel {
    private final int scale = 40;
    private BufferedImage image;
    private List<Player> players;
    private Target target;

    public void updateMap(BufferedImage newImage, List<Player> players, Target target) {
      this.image = newImage;
      this.players = players;
      this.target = target;
      repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (image != null) {
        g.drawImage(image, 0, 0, this);
      }

      if (players != null) {
        for (Player player : players) {
          Room room = player.getCurrentLocation();
          int x1 = room.getX1() * scale;
          int y1 = room.getY1() * scale;
          int x2 = room.getX2() * scale;
          int y2 = room.getY2() * scale;
          int x = (x1 + x2) / 2;
          int y = (y1 + y2) / 2;
          if (player.getTypeOfPlayer() == 0) {
            g.setColor(Color.BLUE);
          } else {
            g.setColor(Color.RED);
          }
          g.fillOval(x - 10, y - 10, 20, 20);
        }
      }

      if (target != null) {
        Room room = target.getCurrentLocation();
        int x1 = room.getX1() * scale;
        int y1 = room.getY1() * scale;
        int x2 = room.getX2() * scale;
        int y2 = room.getY2() * scale;
        int x = (x1 + x2) / 2;
        int y = (y1 + y2) / 2;
        g.setColor(Color.WHITE);
        g.fillRect(x - 10, y - 10, 20, 20);
      }
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(27 * 40, 20 * 40);
    }
  }
}
