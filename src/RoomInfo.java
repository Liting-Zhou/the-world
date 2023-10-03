import java.util.Collections;
import java.util.List;

public class RoomInfo {

    private final int roomNumber;
    private final int x1; //left up corner
    private final int y1; //left up corner
    private final int x2; //right bottom corner
    private final int y2; //right bottom corner
    private final String roomName;
    private List<Weapon> weapons;
    private List<Integer> neighbours;

    public RoomInfo(int roomNumber, int x1, int y1, int x2, int y2, String roomName, List<Weapon> weapons) {
        this.roomNumber = roomNumber;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.roomName = roomName;
        this.weapons = weapons;
        //TODO this.neighbours = getNeighbors();
    }

    public String getRoomName() {
        return roomName;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * returns neighbors given a room number (Spaces that share a "wall" are neighbors).
     *
     * @param roomNumber the room number
     * @return a list of integers which represent rooms
     */
    public List<Integer> getNeighbors(int roomNumber) {
        return Collections.emptyList();
    }
}
