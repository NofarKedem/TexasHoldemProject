
public class GameController {
    private GameEngine game;
    private String gameName;
    private String nameOfUserOwner;
    private int numOfSubscribers;
    private boolean isActive;

    GameController(String gameName,String nameOfUserOwner){
        this.gameName = gameName;
        this.nameOfUserOwner = nameOfUserOwner;
        numOfSubscribers = 0;
    }

    public String getName() {
        return gameName;
    }
}
