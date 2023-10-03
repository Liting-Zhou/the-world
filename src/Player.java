import java.util.Objects;

public class Player {
    private int indexOfPlayer;
    private String name;
    private RoomInfo currentLocation;

    public Player(int indexOfPlayer, String name, RoomInfo currentLocation) {
        this.indexOfPlayer = indexOfPlayer;
        this.name = name;
        this.currentLocation = currentLocation;
    }

    public int getIndexOfPlayer() {
        return indexOfPlayer;
    }

    public void setIndexOfPlayer(int indexOfPlayer) {
        this.indexOfPlayer = indexOfPlayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomInfo getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(RoomInfo currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void action() {
        //move, stay

    }

    public void attack() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getIndexOfPlayer() == player.getIndexOfPlayer() && Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndexOfPlayer(), getName());
    }
}
