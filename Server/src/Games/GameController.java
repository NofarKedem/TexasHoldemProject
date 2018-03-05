package Games;


import GameLogic.*;
import users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private boolean isNewHand;
    private List<WinnersDetails> winResults = new ArrayList();

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

    public void gameMove(String LastGameMove, int amount, GameMoveStatus gameMoveStatus)
    {
        Utils.RoundResult result = game.gameMove(LastGameMove,amount);
        if(result == Utils.RoundResult.CLOSEROUND)
        {
            int numOfCurrRound = game.getCurrNumOfRound();
            if(numOfCurrRound == 4)
            {
                //end hand
                isEndHand = true;
                game.closeTheHand();
               // gameMoveStatus.setEndHand(true);
            }
            else
            {
                switch (numOfCurrRound)
                {
                    case 1: game.callFlop();
                        break;
                    case 2: game.callTurn();
                        break;
                    case 3: game.callRiver();
                        break;
                    default: break;
                }
                game.initRound();
               // gameMoveStatus.setRoundEnd(true);
                gameMoveStatus.setNumRound(game.getCurrNumOfRound());
            }
        }
        if(result == Utils.RoundResult.ENDGAME){
            isEndHand = true;
            Map<Integer, String> winnersMapRef = null;
            //Map<Integer, String> winnersMapRef = game.getWinnerMap();
            try {
                winnersMapRef = game.WhoIsTheWinner();
            }catch (Exception e){
                e.printStackTrace();
            }
            for (Integer numOfPlayer : winnersMapRef.keySet()) {
                //need to find the player index
                String WinnerName = game.getPlayerName(numOfPlayer - 1);
                String cardsComb =   winnersMapRef.get(numOfPlayer);
                String prize = String.valueOf(game.calcWinnersChipPrize());
                addWinResults(WinnerName,cardsComb,prize);
            }
        }
        if(result == Utils.RoundResult.HUMANFOLD){
            isEndHand = true;
            Map<Integer, String> winnersMapRef = null;
            //Map<Integer, String> winnersMapRef = game.getWinnerMap();
            try {
                winnersMapRef = game.setTechniqWinners(result);
            }catch (Exception e){
                e.printStackTrace();
            }
            for (Integer numOfPlayer : winnersMapRef.keySet()) {
                //need to find the player index
                String WinnerName = game.getPlayerName(numOfPlayer - 1);
                String cardsComb =   "All the Human players folded,this is a Technical victory";
                String prize = String.valueOf(game.calcWinnersChipPrize());
                addWinResults(WinnerName,cardsComb,prize);
            }
        }
        if(result == Utils.RoundResult.ALLFOLDED){
            isEndHand = true;
            Map<Integer, String> winnersMapRef = null;
            //Map<Integer, String> winnersMapRef = game.getWinnerMap();
            try {
                winnersMapRef = game.setTechniqWinners(result);
            }catch (Exception e){
                e.printStackTrace();
            }
            for (Integer numOfPlayer : winnersMapRef.keySet()) {
                //need to find the player index
                String WinnerName = game.getPlayerName(numOfPlayer - 1);
                String cardsComb =   "All the other players folded,this is a Technical victory";
                String prize = String.valueOf(game.calcWinnersChipPrize());
                addWinResults(WinnerName,cardsComb,prize);
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
                isNewHand = true;
            }
        }
    }

    public boolean isNewHand() {
        return isNewHand;
    }

    public boolean isEndHand() {
        return isEndHand;
    }

    public void setNewHand(boolean newHand) {
        isNewHand = newHand;
    }

    public void addWinResults(String name, String cardComb, String prize){
        winResults.clear();
        winResults.add(new GameLogic.WinnersDetails(name,cardComb,prize));
    }

    public List<WinnersDetails> getWinResults() {
        return winResults;
    }
}
