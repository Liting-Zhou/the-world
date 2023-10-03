import java.util.Objects;

public class Weapon {
    private final int power;
    private final String name;
    private final int belongRoomNumber;

    public Weapon(int power, String name, int belongRoomNumber) {
        this.power = power;
        this.name = name;
        this.belongRoomNumber=belongRoomNumber;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public int getBelongRoomNumber() {
        return belongRoomNumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weapon)) return false;
        Weapon weapon = (Weapon) o;
        return getPower() == weapon.getPower() && Objects.equals(getName(), weapon.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPower(), getName());
    }

}
