package model;

public class Cat implements Pet {
  private String name;
  private Room currentLocation;

  public Cat(String name, Room currentLocation) {
    this.name = name;
    this.currentLocation = currentLocation;
  }

  @Override
  public void move() {

  }

  @Override
  public String getName() {
    return null;
  }
}
