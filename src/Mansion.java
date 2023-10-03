import java.awt.image.BufferedImage;
import java.util.List;

public class Mansion {
    private List<RoomInfo> rooms;

    public Mansion(List<RoomInfo> rooms) {
        this.rooms = rooms;
    }

    public List<RoomInfo> getListOfRooms() {
        return rooms;
    }


    /**
     * returns room information given a room number.
     *
     * @param roomNumber the room number
     * @return an object which contains all information of the specified room
     */
    public RoomInfo getRoomInfoByRoomNumber(int roomNumber) {
        //    find the right roomInfo
        //    return it
        return null;
    }

    // Create a graphical representation of the world map
    public BufferedImage getBufferedImage() {
        return null;
    }

}
