package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Represents the mansion in the game, which consists of rooms and has a graphical representation.
 */
public final class Mansion {
  private static List<RoomInfo> rooms;
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
  public Mansion(String mansionName, int mansionHeight, int mansionWidth, List<RoomInfo> rooms) {
    this.rooms = rooms;
    this.mansionHeight = mansionHeight;
    this.mansionWidth = mansionWidth;
    this.mansionName = mansionName;

    //initialize the neighbors of each room
    for (RoomInfo room : rooms) {
      room.setNeighbors(room.getNeighbors(rooms));
    }
  }

  // TODO add Player info
  private static void drawRoom(Graphics2D g2d, RoomInfo room, int scaleFactor) {
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
  public static RoomInfo getRoomInfoByRoomNumber(int roomNumber) {
    // Find the right model.RoomInfo based on the roomNumber and return it
    for (RoomInfo room : rooms) {
      if (room.getRoomNumber() == roomNumber) {
        return room;
      }
    }
    return null; // Return null if no matching room is found.
  }

  /**
   * Gets the list of rooms in the mansion.
   *
   * @return The list of rooms.
   */
  public List<RoomInfo> getListOfRooms() {
    return rooms;
  }

  /**
   * Creates a graphical representation of the world map and saves it as an image file.
   */
  public void getBufferedImage() {
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
    for (RoomInfo room : rooms) {
      drawRoom(g2d, room, scaleFactor);
    }

    // Save the generated map as an image file
    try {
      File outputImageFile = new File("./res/mansion_map.png");
      ImageIO.write(mapImage, "png", outputImageFile);
      System.out.println("mansion_map.png saved successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Dislays the list of rooms in the mansion.
   */
  public void displayListOfRooms() {
    System.out.println("The mansion has the following rooms: ");
    for (RoomInfo room : rooms) {
      System.out.println(room.getRoomNumber() + ": " + room.getRoomName());
    }
  }


}
