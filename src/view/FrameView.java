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
import java.util.Enumeration;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Character;
import model.Player;
import model.Room;
import model.Target;
import model.Weapon;
import model.WeaponImp;

public class FrameView extends JFrame implements View {

  private final JLabel display;
  private final JButton addPlayerButton;
  private final JButton finishSetUpButton;
  private final JButton playNextTurnButton;
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

    display = new JLabel("");
    this.add(display, BorderLayout.LINE_START);

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

    playNextTurnButton = new JButton("Play Next Turn");
    add(playNextTurnButton, BorderLayout.PAGE_START);
    playNextTurnButton.setVisible(false);

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
      new AddPlayerDialog(this, "Add Player", true, f).setVisible(true);
    });
    finishSetUpButton.addActionListener(l -> {
      buttonPanel.setVisible(false);
      f.enterGame();
    });
    playNextTurnButton.addActionListener(l -> {
      f.playNextTurn();
    });
    gameBoard.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // convert the mouse coordinates to game board coordinates
        int x = e.getX();
        int y = e.getY();
        if (f.getDisplayMode()) {
          //check if the click is on target
          int[] targetCoordinates = gameBoard.getCharacterCoordinates(f.getTarget());
          int targetX = targetCoordinates[0];
          int targetY = targetCoordinates[1];
          if (x <= targetX + 20 && x >= targetX && y <= targetY + 20 && y >= targetY) {
            f.displayTargetInfo();
            return;
          }
          //check if the click is on player
          for (Player player : f.getPlayers()) {
            int[] playerCoordinates = gameBoard.getCharacterCoordinates(player);
            int playerX = playerCoordinates[0];
            int playerY = playerCoordinates[1];
            if (Math.sqrt(Math.pow(x - playerX, 2) + Math.pow(y - playerY, 2)) <= 10) {
              f.displayPlayerInfo(player);
              return;
            }
          }
          //otherwise, the click is on the room
          f.displayRoomInfo(x / 40, y / 40);
        }
        if (f.getPlayerMoveMode()) {
          f.moveToRoom(x / 40, y / 40);
        }
        f.setPlayerMoveMode(false);
      }
    });

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        //do nothing
      }

      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_M:
            f.setPlayerMoveMode(true);
            setDisplay("Now click a neighboring room to move to.");
            break;
          case KeyEvent.VK_A:
            f.attack();
            break;
          case KeyEvent.VK_P:
            f.pickUpWeapon();
            break;
          case KeyEvent.VK_L:
            f.lookAround();
            break;
          case KeyEvent.VK_T:
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
  }

  @Override
  public void showSetUpPanel() {
    buttonPanel.setVisible(true);
//    System.out.println("After: " + buttonPanel.isVisible());
//    System.out.println("On EDT: " + SwingUtilities.isEventDispatchThread());
  }

  @Override
  public void displayGamePanel() {
    scrollPane.setVisible(true);
    playNextTurnButton.setVisible(true);
  }

  @Override
  public void setDisplay(String s) {
    display.setText(s);
    //displayScrollPane.setVisible(true);
  }

  @Override
  public void refresh(BufferedImage image, List<Player> players, Target target) {
    gameBoard.updateMap(image, players, target);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void showWeaponDialogForAttack(List<WeaponImp> weapons, Features f) {
    String[] weaponNames = weapons.stream().map(Weapon::getName).toArray(String[]::new);

    //add "your fist"
    String[] options = new String[weaponNames.length + 1];
    System.arraycopy(weaponNames, 0, options, 0, weaponNames.length);
    options[options.length - 1] = "Your Fist";

    JPanel panel = new JPanel();
    ButtonGroup buttonGroup = new ButtonGroup();

    for (String weapon : options) {
      JRadioButton radioButton = new JRadioButton(weapon);
      buttonGroup.add(radioButton);
      panel.add(radioButton);
    }

    // display the dialog
    int result = JOptionPane.showOptionDialog(
        this,
        panel,
        "Weapon Selection",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        null,
        null);

    for (Enumeration<AbstractButton> buttons = buttonGroup.getElements();
         buttons.hasMoreElements(); ) {
      AbstractButton button = buttons.nextElement();
      if (button.isSelected()) {
        String selectedWeapon = button.getText();
        f.attackAfterWeaponSelected(selectedWeapon);
        break;
      }
    }
  }

  @Override
  public void showWeaponDialogForPickUp(List<WeaponImp> weapons, Features f) {
    String[] weaponNames = weapons.stream().map(Weapon::getName).toArray(String[]::new);

    //add "your fist"
    String[] options = new String[weaponNames.length];
    System.arraycopy(weaponNames, 0, options, 0, weaponNames.length - 1);

    JPanel panel = new JPanel();
    ButtonGroup buttonGroup = new ButtonGroup();

    for (String weapon : options) {
      JRadioButton radioButton = new JRadioButton(weapon);
      buttonGroup.add(radioButton);
      panel.add(radioButton);
    }

    // display the dialog
    int result = JOptionPane.showOptionDialog(
        this,
        panel,
        "Weapon Selection",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        null,
        null);

    for (Enumeration<AbstractButton> buttons = buttonGroup.getElements();
         buttons.hasMoreElements(); ) {
      AbstractButton button = buttons.nextElement();
      if (button.isSelected()) {
        String selectedWeapon = button.getText();
        f.pickUpAfterWeaponSelected(selectedWeapon);
        break;
      }
    }
  }

  @Override
  public void showMessageDialog(String title, String information) {
    JTextArea textArea = new JTextArea(information);
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(300, 200));

    JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Inner class for handling player input dialog.
   */
  private class AddPlayerDialog extends JDialog {
    private final Features f;
    private JTextField playerNameField;
    private JTextField startingRoomField;
    private JTextField weaponLimitsField;
    private JRadioButton humanRadioButton;
    private JRadioButton computerRadioButton;
    private int playerType;

    public AddPlayerDialog(JFrame parent, String title, boolean modal, Features feature) {
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
          int[] coordinates = getCharacterCoordinates(player);
          int x = coordinates[0];
          int y = coordinates[1];
          if (player.getTypeOfPlayer() == 0) {
            g.setColor(Color.BLUE);
          } else {
            g.setColor(Color.RED);
          }
          g.fillOval(x - 10, y - 10, 20, 20);
        }
      }

      if (target != null) {
        int[] coordinates = getCharacterCoordinates(target);
        int x = coordinates[0];
        int y = coordinates[1];
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 20, 20);
      }
    }

    public int[] getCharacterCoordinates(Character c) {
      Room room = c.getCurrentLocation();
      int x1 = room.getX1() * scale;
      int y1 = room.getY1() * scale;
      int x2 = room.getX2() * scale;
      int y2 = room.getY2() * scale;
      int x = (x1 + x2) / 2;
      int y = (y1 + y2) / 2;
      return new int[] {x, y};
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(27 * 40, 20 * 40);
    }

  }
}
