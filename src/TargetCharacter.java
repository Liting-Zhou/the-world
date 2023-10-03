public class TargetCharacter {
    private final String name;
    private int health;
    private RoomInfo currentLocation;

    public TargetCharacter(String name, int health, RoomInfo currentLocation) {
        this.name = name;
        this.health = health;
        this.currentLocation = currentLocation;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public RoomInfo getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(RoomInfo currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void move() {
        // get current location
        // move to next room
    }
}
