package Games;


import GameLogic.*;
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
    private int numOfPlayerClickedready;
    private boolean isEndHand;

    public GameController(String gameName, String nameOfUserOwner){
        this.gameName = gameName;
        this.nameOfUserOwner = nameOfUserOwner;
        this.numOfSubscribers = 0;
        this.isActive = false;
        this.game = new GameEngine();
        users = new ArrayList<>();
        numOfPlayerClickedready=0;
        isEndHand= false;
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
        if(numOfPlayers == numOfSubscribers)
        {
            startGame();
        }
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

    public boolean getIsActive()
    {
        return isActive;
    }

    public void gameMoveComputer()
    {
        Utils.RoundResult result = game.playWithComputer();
        status(result);
    }
    public void gameMoveHuman(String LastGameMove, int amount)
    {
        Utils.RoundResult result = game.gameMove(LastGameMove,amount);
        status(result);
    }

    private void status(Utils.RoundResult result)
    {
        if(result == Utils.RoundResult.CLOSEROUND)
        {
            if(game.getCurrNumOfRound() == 4)
            {
                isEndHand = true;
                game.closeTheHand();
            }
            else
            {
                game.initRound();
            }
        }
    }

    public void userClickedReady()
    {
        numOfPlayerClickedready++;
        if(numOfPlayerClickedready == numOfSubscribers)
        {
            if(numOfHands == game.getCurrentNumberOfHand())
            {
                //endGame and return to lobby
            }
            else {
                numOfPlayerClickedready = 0;
                isEndHand = false;
                game.startHand();

            }
        }
    }


    public boolean isEndHand() {
        return isEndHand;
    }

    public void setMove(String numOfMove)
    {
        game.setMoveForAmountValidatoin(game.convertIntToMove(numOfMove));
    }

}
