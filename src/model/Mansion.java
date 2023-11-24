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
  //private static List<Room> rooms;
  //private static List<Player> players;
  //private static Target target;
  //private static Pet pet;
  //private static int flag = 0; //if flag = 1, pet should move to the specified room.


  /**
   * Constructs a new model.Mansion object.
   *
   * @param mansionName   The name of the mansion.
   * @param mansionHeight The length of the mansion map.
   * @param mansionWidth  The width of the mansion map.
   * @param rooms         The list of rooms in the mansion.
   */
  public Mansion(String mansionName, int mansionHeight, int mansionWidth, List<Room> rooms) {
    //this.rooms = rooms;

    //this.players = new ArrayList<>();


    //initialize the neighbors of each room
    for (Room room : rooms) {
      room.setNeighbors(room.getNeighbors());
    }
  }









//  /**
//   * sets the target in the mansion.
//   *
//   * @param target The target to be set.
//   */
//  public void setTarget(Target target) {
//    this.target = target;
//  }



//  /**
//   * Sets the pet in the mansion.
//   *
//   * @param pet The pet to be set.
//   */
//  public void setPet(Pet pet) {
//    this.pet = pet;
//  }





//
//  /**
//   * Adds a player to the mansion.
//   *
//   * @param player The player to be added.
//   */
//  public void addPlayer(Player player) {
//    players.add(player);
//  }




}
