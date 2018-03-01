package Games;


import GameLogic.GameEngine;
import users.User;

import java.util.List;

public class GameController {
    private transient GameEngine game;
    private String gameName;
    private String nameOfUserOwner;
    private int numOfSubscribers;
    private boolean isActive;
    private int numOfHands;
    private int buy;
    private int numOfPlayers;
    private List<User> users;

    public GameController(String gameName, String nameOfUserOwner){
        this.gameName = gameName;
        this.nameOfUserOwner = nameOfUserOwner;
        this.numOfSubscribers = 0;
        this.isActive = false;
        this.game = new GameEngine();
    }

    public String getName() {
        return gameName;
    }


    public void loadXmlFile(String xmlDescription) throws Exception{
        game.loadGameFile(xmlDescription);
        updateGameDetails();
    }

    public void updateGameDetails(){
        this.numOfHands = game.getNumberOfHands();
        this.buy = game.getNumOfChipsPerBuy();
        this.numOfPlayers = game.getNumberOfMaxPlayersInGame();
    }

    public int getnumOfSubscribers() {
        return numOfSubscribers;
    }
    public int getnumOfPlayers() {
        return numOfPlayers;
    }

    public void addUser(User user)
    {
        users.add(user);
    }
}
