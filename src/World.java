import java.util.List;

/**
 * This project is based on the classical board game <i>Kill Doctor Lucky<i/>.
 */
public class World {
    private TargetCharacter target;
    private List<Player> players;
    private List<RoomInfo> listOfRooms;
    private Mansion mansion;
    private int indexOfLastPlayer = 0;


    public World(String conFilePath) {
        //target=new TargetCharacter();
        //create list of rooms;
        //mansion=new Mansion();
        //players=new List<>();
    }

    public TargetCharacter getTarget() {
        return target;
    }

    public void updateTarget(TargetCharacter target) {
        this.target = target;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void updatePlayer() {
    }


    public Mansion getMansion() {
        return mansion;
    }


    public int getIndexOfLastPlayer() {
        return indexOfLastPlayer;
    }

    public void setIndexOfLastPlayer(int indexOfLastPlayer) {
        this.indexOfLastPlayer = indexOfLastPlayer;
    }

    //return true if game over
    public Boolean ifGameOver() {
        return false;
    }

    public void playNextRound() {
        if (ifGameOver() == false) {
            roundOfTargetCharacter();
            roundOfPlayers();
        }

    }

    public void roundOfTargetCharacter() {

    }

    public void roundOfPlayers() {

    }

    public void getWinner() {
        //print the name of winner
    }
}
