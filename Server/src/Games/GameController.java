package Games;


import GameLogic.GameEngine;
import GameLogic.PlayerInfo;
import GameLogic.movesInfo;
import users.User;

import java.util.ArrayList;
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
    private int SmallBlind;
    private int BigBlind;
    private boolean fixedBlind;
    private List<User> users;

    public GameController(String gameName, String nameOfUserOwner){
        this.gameName = gameName;
        this.nameOfUserOwner = nameOfUserOwner;
        this.numOfSubscribers = 0;
        this.isActive = false;
        this.game = new GameEngine();
        users = new ArrayList<>();
    }

    public String getName() {
        return gameName;
    }


    public String loadXmlFile(String xmlDescription) throws Exception{
        String gameTitle = game.loadGameFile(xmlDescription);
        updateGameDetails();
        return gameTitle;
    }

    public void updateGameDetails(){
        this.numOfHands = game.getNumberOfHands();
        this.buy = game.getNumOfChipsPerBuy();
        this.numOfPlayers = game.getNumberOfMaxPlayersInGame();
        this.gameName = game.getNameOfGame();
        this.SmallBlind = game.getNumOfChipsForsmall();
        this.BigBlind = game.getNumOfChipsForBig();
        this.fixedBlind = game.getFixedState();
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
        numOfSubscribers++;
    }

    public List<User> getUsers() {
        return users;
    }
    public GameEngine getGameEngine()
    {
        return game;
    }

    public void startGame()
    {
        this.isActive = true;
        game.stratGame(users);

    }

    public List<PlayerInfo> getAllPlayerInfo()
    {
        return game.getAllPlayerInfo();
    }



    public movesInfo getMovesInfo()
    {
        return game.getMoveInfo();
    }

}
