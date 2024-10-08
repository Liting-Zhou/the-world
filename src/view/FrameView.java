package view;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
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
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.Character;
import model.Player;
import model.ReadOnlyWorld;
import model.Room;
import model.Target;
import model.Weapon;
import model.WeaponImp;

/**
 * This class represents the view of the game.
 */
public class FrameView extends JFrame implements View {

  private final JTextArea display;
  private final JButton addPlayerButton;
  private final JButton startGameButton;
  private final JButton playNextTurnButton;
  private final JPanel buttonPanel;
  private final JMenuItem newGameNewWorldItem;
  private final JMenuItem newGameCurrentWorldItem;
  private final JMenuItem quitItem;
  private final GameBoardPanel gameBoard;
  private final JScrollPane scrollPane; //for game board
  private final JScrollPane displayScrollPane; //for text display
  private ReadOnlyWorld readOnlyModel;


  /**
   * Constructor of the view. It takes a read-only model as parameter and thus
   * it can read some information directly from the model.
   *
   * @param model the read-only model
   **/
  public FrameView(ReadOnlyWorld model) {
    super("Game of Kill Doctor Happy");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    newGameNewWorldItem = new JMenuItem("Restart with a new world specification");
    newGameCurrentWorldItem = new JMenuItem("Restart with the current world specification");
    quitItem = new JMenuItem("Quit the game");
    // create a menu
    JMenu fileMenu = new JMenu("Menu");
    // add menu items to the menu
    fileMenu.add(newGameCurrentWorldItem);
    fileMenu.add(newGameNewWorldItem);
    fileMenu.add(quitItem);
    // create a menu bar
    JMenuBar menuBar = new JMenuBar();
    menuBar.add(fileMenu);
    //add the menu bar to the frame
    setJMenuBar(menuBar);

    //display panel
    display = new JTextArea();
    display.setEditable(false);
    display.setLineWrap(true);
    display.setWrapStyleWord(true);
    display.setPreferredSize(new Dimension(200, 800));
    displayScrollPane = new JScrollPane(display);
    displayScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    displayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(displayScrollPane, BorderLayout.LINE_START);
    displayScrollPane.setVisible(false);

    //add player
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    addPlayerButton = new JButton("Add Player");
    buttonPanel.add(addPlayerButton);
    startGameButton = new JButton("Start Game");
    buttonPanel.add(startGameButton);
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

    showMessageDialog("", "Welcome to the Game of Kill Doctor Happy!\n\n"
        + "Creator: Liting Zhou");
    showInitialDialog("Game of Kill Doctor Happy", "Choose an option:",
        "New game with the current world specification", "New game with a new world specification");
    this.readOnlyModel = model;
  }

  @Override
  public void setFeatures(Features f) {
    newGameNewWorldItem.addActionListener(l -> {
      displayGamePanel(false);
      setDisplay("Add some players before starting the game.");
      displayScrollPane.setVisible(true);
      try {
        this.readOnlyModel = f.newGameWithNewConfig();
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    });
    newGameCurrentWorldItem.addActionListener(l -> {
      displayGamePanel(false);
      f.resetState();
      setDisplay("Add some players before starting the game.");
      displayScrollPane.setVisible(true);
      f.gameSetUp();
    });
    quitItem.addActionListener(l -> {
      f.exitGame();
    });
    addPlayerButton.addActionListener(l -> {
      if (readOnlyModel.getListOfPlayers().size() < 11) {
        new AddPlayerDialog(this, "Add Player", true, f).setVisible(true);
      } else {
        showMessageDialog("", "You can only add 10 players at most.");
      }
    });
    startGameButton.addActionListener(l -> {
      buttonPanel.setVisible(false);
      f.enterGame();
    });
    playNextTurnButton.addActionListener(l -> {
      if (f.getPlayMode()) {
        setDisplay("You have not finished your turn yet.");
        resetFocus();
      } else {
        f.playNextTurn();
      }
    });
    gameBoard.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // convert the mouse coordinates to game board coordinates
        int x = e.getX();
        int y = e.getY();
        if (f.getDisplayMode()) {
          //check if the click is on target
          int[] targetCoordinates = gameBoard.getCharacterCoordinates(readOnlyModel.getTarget());
          int targetX = targetCoordinates[0];
          int targetY = targetCoordinates[1];
          if (x <= targetX + 20 && x >= targetX && y <= targetY + 20 && y >= targetY) {
            showMessageDialog("Target Information", readOnlyModel.displayTargetInformation());
            return;
          }
          //check if the click is on player
          int numOfPlayers = readOnlyModel.getListOfPlayers().size();
          for (Player player : readOnlyModel.getListOfPlayers()) {
            int[] playerCoordinates = gameBoard.getCharacterCoordinates(player);
            int playerX = playerCoordinates[0];
            int playerY = playerCoordinates[1];
            int index = player.getIndexOfPlayer();
            int baseX = playerX - numOfPlayers / 2 * 20 + index * 20 + 10;
            if (Math.sqrt(Math.pow(x - baseX, 2) + Math.pow(y - playerY, 2)) <= 10) {
              showMessageDialog("Player Information",
                  readOnlyModel.displayPlayerInformation(player));
              return;
            }
          }
          //otherwise, the click is on the room
          String display = readOnlyModel.displayRoomInformation(
              readOnlyModel.findRoomByCoordinates(x / 40, y / 40));
          showMessageDialog("Room Information", display);
        }
        if (f.getPlayerMoveMode()) {
          f.moveToRoom(x / 40, y / 40);
          f.setPlayerMoveMode(false);
          f.setPlayMode(false);
        }
        if (f.getMovePetMode()) {
          f.movePetToRoom(x / 40, y / 40);
          f.setMovePetMode(false);
          f.setPlayMode(false);
        }
      }
    });

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        //do nothing
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (f.getPlayMode()) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_M:
              f.setPlayerMoveMode(true);
              setDisplay("Now click a neighboring room to move to.");
              break;
            case KeyEvent.VK_A:
              f.attemptToAttack();
              break;
            case KeyEvent.VK_P:
              f.attemptToPickUpWeapon();
              break;
            case KeyEvent.VK_L:
              f.lookAround();
              break;
            case KeyEvent.VK_T:
              f.attemptToMoveThePet();
              break;
            default:
              break;
          }
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        //do nothing
      }
    });
  }

  /**
   * Show the initial dialog when program starts.
   *
   * @param title   the name of the game
   * @param message the message to be displayed
   * @param option1 option 1 for the user to choose
   * @param option2 option 2 for the user to choose
   */
  public void showInitialDialog(String title, String message, String option1, String option2) {
    SwingUtilities.invokeLater(() -> {
      Object[] options = {option1, option2};
      int result = JOptionPane.showOptionDialog(
          null,
          message,
          title,
          JOptionPane.DEFAULT_OPTION,
          JOptionPane.INFORMATION_MESSAGE,
          null,
          options,
          options[0]);
      if (result == 0) {
        //perform same action as clicking the newGameCurrentWorldItem
        newGameCurrentWorldItem.doClick();
      } else if (result == 1) {
        newGameNewWorldItem.doClick();
      } else { //do nothing
      }
    });
  }

  @Override
  public void showSetUpPanel() {
    buttonPanel.setVisible(true);
  }

  @Override
  public void displayGamePanel(boolean b) {
    scrollPane.setVisible(b);
    playNextTurnButton.setVisible(b);
  }

  @Override
  public void setDisplay(String s) {
    display.setText(s);
  }

  @Override
  public void refresh() {
    gameBoard.updateMap();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void showWeaponDialogForAttack(Features f) {
    List<WeaponImp> weapons = readOnlyModel.getCurrentPlayer().getWeaponsCarried();
    String[] weaponNames = weapons.stream().map(Weapon::getName).toArray(String[]::new);

    //add "your fingers"
    String[] options = new String[weaponNames.length + 1];
    System.arraycopy(weaponNames, 0, options, 0, weaponNames.length);
    options[options.length - 1] = "Your Fingers";

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
  public void showWeaponDialogForPickUp(Features f) {
    List<WeaponImp> weapons = readOnlyModel.getCurrentPlayer().getCurrentLocation().getWeapons();
    String[] weaponNames = weapons.stream().map(Weapon::getName).toArray(String[]::new);

    JPanel panel = new JPanel();
    ButtonGroup buttonGroup = new ButtonGroup();

    for (String weapon : weaponNames) {
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
    scrollPane.setPreferredSize(new Dimension(500, 200));

    JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public String showInputDialog(String label) {
    String userInput = JOptionPane.showInputDialog(label);
    return userInput;
  }

  /**
   * Inner class for handling player input dialog.
   */
  private class AddPlayerDialog extends JDialog {
    private final Features fea;
    private JTextField playerNameField;
    private JTextField startingRoomField;
    private JTextField weaponLimitsField;
    private JRadioButton humanRadioButton;
    private JRadioButton computerRadioButton;
    private int playerType;

    public AddPlayerDialog(JFrame parent, String title, boolean modal, Features feature) {
      super(parent, title, modal);
      setSize(500, 300);
      setLocationRelativeTo(parent);
      fea = feature;
      initDialog();
    }

    private void initDialog() {
      setLayout(new BorderLayout());

      JPanel inputPanel = new JPanel(new GridLayout(3, 2));

      inputPanel.add(new JLabel(" Player Name:"));
      playerNameField = controlTextField(20, "[a-zA-Z ]");
      inputPanel.add(playerNameField);

      inputPanel.add(new JLabel(" Starting Room (0-21):"));
      startingRoomField = controlTextField(20, "[0-9]|1[0-9]|2[0-1]");
      inputPanel.add(startingRoomField);

      inputPanel.add(new JLabel(" Weapon Limit (0-5):"));
      weaponLimitsField = controlTextField(20, "[0-5]");
      inputPanel.add(weaponLimitsField);

      JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      radioPanel.add(new JLabel("Player Type:"));
      ButtonGroup playerTypeGroup = new ButtonGroup();

      humanRadioButton = new JRadioButton("Human-controlled");
      computerRadioButton = new JRadioButton("Computer-controlled");
      playerTypeGroup.add(humanRadioButton);
      playerTypeGroup.add(computerRadioButton);
      radioPanel.add(humanRadioButton);
      radioPanel.add(computerRadioButton);

      humanRadioButton.setSelected(true);

      humanRadioButton.addActionListener(l -> {
        playerType = 0; // human
      });
      computerRadioButton.addActionListener(l -> {
        playerType = 1; // computer
      });

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JButton addButton = new JButton("Add");
      addButton.addActionListener(l -> {
        if (checkInput()) {
          fea.addPlayer(getPlayerName(), getStartingRoom(), getWeaponLimits(), getPlayerType());
          dispose();
        } else {
          JOptionPane.showMessageDialog(this, "Invalid input. Please check the numbers.", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      });
      buttonPanel.add(addButton);

      add(radioPanel, BorderLayout.NORTH);
      add(inputPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextField controlTextField(int columns, String regex) {
      JTextField textField = new JTextField(columns);
      ((AbstractDocument) textField.getDocument()).setDocumentFilter(
          new RegexDocumentFilter(regex));
      return textField;
    }

    private boolean checkInput() {
      if (playerNameField.getText().isEmpty()) {
        return false;
      }

      int startingRoom = Integer.parseInt(startingRoomField.getText());
      int weaponLimits = Integer.parseInt(weaponLimitsField.getText());

      if (startingRoom < 0 || startingRoom > 21) {
        return false;
      }

      if (weaponLimits < 0 || weaponLimits > 5) {
        return false;
      }
      return true;
    }

    public String getPlayerName() {
      return playerNameField.getText();
    }

    public int getStartingRoom() {
      return Integer.parseInt(startingRoomField.getText());
    }

    public int getWeaponLimits() {
      return Integer.parseInt(weaponLimitsField.getText());
    }

    public int getPlayerType() {
      return playerType;
    }

    private class RegexDocumentFilter extends DocumentFilter {
      private final String regex;

      public RegexDocumentFilter(String regex) {
        this.regex = regex;
      }

      @Override
      public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
          throws BadLocationException {
        if (string.matches(regex)) {
          super.insertString(fb, offset, string, attr);
        }
      }

      @Override
      public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
          throws BadLocationException {
        if (text.matches(regex)) {
          super.replace(fb, offset, length, text, attrs);
        }
      }
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


    public void updateMap() {
      this.image = readOnlyModel.getMap();
      this.players = readOnlyModel.getListOfPlayers();
      this.target = readOnlyModel.getTarget();

      repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (image != null) {
        g.drawImage(image, 0, 0, this);
      }

      if (players != null) {
        int numOfPlayers = players.size();
        for (Player player : players) {
          int[] coordinates = getCharacterCoordinates(player);
          int x = coordinates[0];
          int y = coordinates[1];
          if (player.getTypeOfPlayer() == 0) {
            g.setColor(Color.BLUE);
          } else {
            g.setColor(Color.RED);
          }
          int index = player.getIndexOfPlayer();
          int baseX = x - numOfPlayers / 2 * 20; // in order to let players not overlap
          g.fillOval(baseX + index * 20, y - 10, 20, 20);
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
