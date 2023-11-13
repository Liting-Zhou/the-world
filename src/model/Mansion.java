package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Represents the mansion in the game, which consists of rooms and has a graphical representation.
 */
public final class Mansion {
  private static List<Room> rooms;
  private static List<Player> players;
  private static Target target;
  private static Pet pet;
  private static int flag = 0; //if flag = 1, pet should move to the specified room.
  private String mansionName;
  private int mansionHeight;
  private int mansionWidth;

  /**
   * Constructs a new model.Mansion object.
   *
   * @param mansionName   The name of the mansion.
   * @param mansionHeight The length of the mansion map.
   * @param mansionWidth  The width of the mansion map.
   * @param rooms         The list of rooms in the mansion.
   */
  public Mansion(String mansionName, int mansionHeight, int mansionWidth, List<Room> rooms) {
    this.rooms = rooms;
    this.mansionHeight = mansionHeight;
    this.mansionWidth = mansionWidth;
    this.mansionName = mansionName;
    this.players = new ArrayList<>();


    //initialize the neighbors of each room
    for (Room room : rooms) {
      room.setNeighbors(room.getNeighbors());
    }
  }

  private static void drawRoom(Graphics2D g2d, Room room, int scaleFactor) {
    int roomX = room.getX1() * scaleFactor;
    int roomY = room.getY1() * scaleFactor;
    int roomWidth = (room.getX2() - room.getX1()) * scaleFactor;
    int roomHeight = (room.getY2() - room.getY1()) * scaleFactor;

    // Draw the room as a rectangle on the map
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fillRect(roomX, roomY, roomWidth, roomHeight);

    // Draw walls around the room
    g2d.setColor(Color.BLACK);
    g2d.drawRect(roomX, roomY, roomWidth, roomHeight);

    Font font = new Font("Arial", Font.PLAIN, 12); // Adjust font and size as needed
    g2d.setFont(font);
    g2d.setColor(Color.BLACK);

    // Draw the room number and name as text
    g2d.setColor(Color.BLACK);
    g2d.drawString("Room " + room.getRoomNumber(), roomX + 10, roomY + 20);
    g2d.drawString(room.getRoomName(), roomX + 10, roomY + 30);
  }

  /**
   * Returns room information given a room number.
   *
   * @param roomNumber The room number.
   * @return An object containing all information of the specified room.
   */
  public static Room getRoomInfoByRoomNumber(int roomNumber) {
    // Find the right model.RoomInfo based on the roomNumber and return it
    for (Room room : rooms) {
      if (room.getRoomNumber() == roomNumber) {
        return room;
      }
    }
    return null; // Return null if no matching room is found.
  }

  /**
   * Gets the list of players in the mansion.
   *
   * @return The list of players.
   */
  public static List<Player> getListOfPlayers() {
    return players;
  }

  /**
   * Gets the target in the mansion.
   *
   * @return The target.
   */
  public static Target getTarget() {
    return target;
  }

  /**
   * sets the target in the mansion.
   *
   * @param target The target to be set.
   */
  public void setTarget(Target target) {
    this.target = target;
  }

  /**
   * Gets the pet in the mansion.
   *
   * @return The pet.
   */
  public static Pet getPet() {
    return pet;
  }

  /**
   * Sets the pet in the mansion.
   *
   * @param pet The pet to be set.
   */
  public void setPet(Pet pet) {
    this.pet = pet;
  }

  /**
   * Gets the list of rooms in the mansion.
   *
   * @return The list of rooms.
   */
  public static List<Room> getListOfRooms() {
    return rooms;
  }

  /**
   * Gets the flag.
   *
   * @return The flag.
   */
  public static int getFlag() {
    return flag;
  }

  /**
   * Sets the flag.
   *
   * @param num The value of flag to be set.
   */
  public static void setFlag(int num) {
    flag = num;
  }

  /**
   * Adds a player to the mansion.
   *
   * @param player The player to be added.
   */
  public void addPlayer(Player player) {
    players.add(player);
  }

  /**
   * Creates a graphical representation of the world map and saves it as an image file.
   */
  BufferedImage getBufferedImage() {
    int scaleFactor = 40;
    int buffer = 0;
    // Create a BufferedImage to represent the map
    BufferedImage mapImage =
        new BufferedImage(mansionWidth * scaleFactor + buffer, mansionHeight * scaleFactor + buffer,
            BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = mapImage.createGraphics();

    // Set background color
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, mansionWidth * scaleFactor + buffer, mansionHeight * scaleFactor + buffer);

    // Draw rooms on the map
    for (Room room : rooms) {
      drawRoom(g2d, room, scaleFactor);
    }
    return mapImage;
  }

  /**
   * Saves the mansion map as an image file.
   */
  public void saveMansionMap() {
    BufferedImage mapImage = getBufferedImage();
    // Save the generated map as an image file
    try {
      File outputImageFile = new File("./res/mansion_map.png");
      ImageIO.write(mapImage, "png", outputImageFile);
      System.out.println("mansion_map.png saved successfully.");
      //System.out.println("***************");
      //System.out.println("Game continues.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Dislays the list of rooms in the mansion.
   */
  public void displayListOfRooms() {
    System.out.println("The mansion has the following rooms: ");
    for (Room room : rooms) {
      System.out.println(String.format("%d: %s", room.getRoomNumber(), room.getRoomName()));
    }
  }
}
