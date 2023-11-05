package model;

public class Cat implements Pet {
  private String name;
  private Room currentLocation;

  public Cat(String name, Room currentLocation) {
    this.name = name;
    this.currentLocation = currentLocation;
  }

  @Override
  public void wander() {
    //TODO: depth-first traversal
    // for now, move randomly
    RandomNumber randomNumber = new RandomNumber();
    int random = randomNumber.nextRandomInt(22);
    updateLocation(Mansion.getRoomInfoByRoomNumber(random));
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Room getCurrentLocation() {
    return currentLocation;
  }

  @Override
  public void updateLocation(Room room) {
    this.currentLocation = room;
  }
}
