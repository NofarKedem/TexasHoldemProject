package GameLogic;

import XMLobject.DynamicPlayers;
import XMLobject.GameDescriptor;
import XMLobject.Player;
import XMLobject.Players;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class GameEngine {
    private Deck deck;
    private List<PokerPlayer> players;
    private Hand currHand;
    private long timeOfStartGame;
    private int totalnumOfHands;
    private int numOfPlayHands;
    private static int dilerIndex = 0;
    private int ancorDindex;
    private int addition;
    private int dilerRoundsCounter;
    private int max_total_rounds;
    private int handTohandCashBox;
    private int numOfChipsForsmall;
    private int numOfChipsForBig;
    private int originalBig;
    private int originalSmall;
    private int numOfChipsPerBuy;
    private int maxBig;
    private boolean fixed;
    private int numberOfMaxPlayersInGame;
    private String nameOfGame; //temp for one game, not use it!
    private Map<Integer,String> WinnerMap;

    private List<StatusSnapShot> handReplay = new ArrayList<>();

    public GameEngine(){
        timeOfStartGame = 0;
        totalnumOfHands = 0;
        players = new ArrayList(Utils.numOfPlayers);
        deck = new Deck();
        handTohandCashBox = 0;

    }

    public void stratGame(List<users.User> userList)
    {
        initializePlayers(userList);
        startHand();
    }
    public void startHand()
    {
        initHandReplay();
        initializeHand();
        cardDistribusionToPlayer();
        initRound();
        blindBetSmall();
        blindBetBig();
    }
/*
    public void initializePlayers(List<Player> listOfPlayerFromXML) {
        Utils.numOfPlayers = listOfPlayerFromXML.size();
        int i = 1;
        players.clear();
        char type;
        for (Player player : listOfPlayerFromXML) {
            if (player.getType().equals("Human"))
                type = 'H';
            else
                type = 'C';
            players.add(new PokerPlayer(type, " ", numOfChipsPerBuy, 1,i, player.getName(), player.getId().intValue()));
        }
        dilerIndex = calculateDilerIndex(dilerIndex);
        ancorDindex = dilerIndex;
        initPlayersState();
    }
    */
    public void initializePlayers(List<users.User> userList)
    {
        Utils.numOfPlayers = userList.size();
        int i = 1;
        int id=0;
        players.clear();
        char type;
        for (users.User user : userList) {
            user.setId(id);
            if (user.isComputer())
                type = 'C';
            else
                type = 'H';
            players.add(new PokerPlayer(type, " ", numOfChipsPerBuy, 1,i, user.getName(), id));
            id++;
        }
        dilerIndex = calculateDilerIndex(dilerIndex);
        ancorDindex = dilerIndex;
        initPlayersState();
    }

    public void closeTheHand(){
        for(PokerPlayer P : players){
            P.resetState();
        }
        dilerIndex = calculateDilerIndex(dilerIndex);
        if (!fixed) {
            if(dilerIndex == ancorDindex){
                dilerRoundsCounter++;
                if(dilerRoundsCounter < max_total_rounds) {
                    if (numOfChipsForBig + addition <= maxBig) {
                        numOfChipsForsmall = numOfChipsForsmall + addition;
                        numOfChipsForBig = numOfChipsForBig + addition;
                    }
                }
            }
        }
        initPlayersState();

    }

    public void initializeHand(){
        numOfPlayHands++;
        deck.InitCardInDeck();
        currHand = new Hand(deck, handTohandCashBox);

        initPlayersQuitState();
        currHand.setHandPlayers(players);
    }
    public void initRound()
    {
        currHand.initRound();
        for(PokerPlayer player : players)
        {
            player.initBet();
        }
        currHand.setFirstPlayer();
        //addSnapShotToReplay();
    }

    private void initPlayersState(){
        players.get(dilerIndex).updateState("D");
        int small = calcSmallIndex(dilerIndex);
        players.get(small).updateState("S");
        players.get(calcBigIndex(small)).updateState("B");
    }

    private void initPlayersQuitState(){
        for(PokerPlayer player : players){
            if(player.getChips() > 0 && player.getFinalQuit() == false)
                player.setQuit(false);
        }
    }

    private int calculateDilerIndex(int lastDilerIndex){
        return  (lastDilerIndex + 1)%(players.size());
    }
    private int calcBigIndex(int small){
        return(small + 1)%(players.size());
    }
    private int calcSmallIndex(int diler){
        return (diler+1)%(players.size());
    }

    public void cardDistribusionToPlayer(){ //per GameLogic.Hand
        this.currHand.cardDistribusion();
    }

    //using this method in order to test the replay logic with the previous UI logic
    public boolean blindBet(){
        boolean retForSmall = currHand.blindSmall(numOfChipsForsmall);
        addSnapShotToReplay();
        boolean retForBig = currHand.blindBig(numOfChipsForBig);
        addSnapShotToReplay();
        return (retForSmall || retForBig);
    }
    //To Be used in the NEW UI logic (exc. 2)
    public boolean blindBetSmall(){
        boolean retForSmall = currHand.blindSmall(numOfChipsForsmall);
        addSnapShotToReplay();
        return retForSmall;
    }
    //To Be used in the NEW UI logic (exc. 2)
    public boolean blindBetBig(){
        boolean retForBig = currHand.blindBig(numOfChipsForBig);
        addSnapShotToReplay();
        return retForBig;
    }

    public Utils.RoundResult gameMove(String LastGameMove, int amount){
        return currHand.gameMove(convertIntToMove(LastGameMove), amount);
    }

    public Utils.RoundResult playWithComputer(){
        return currHand.playWithComputer();
    }

    public boolean validateMove(String LastGameMove){
        Round.GameMoves gameMove;
        if((gameMove = convertIntToMove(LastGameMove)) != null)
            return currHand.isValidGameMove(gameMove);
        return false;
    }
    public Boolean isPlayerHasEnoughChips()
    {
        return currHand.isPlayerHasEnoughChips();
    }

    public void loadFile(String filePath)throws Exception
    {
        SimpleJAXBMain Xml = new SimpleJAXBMain(filePath);
        GameDescriptor gameDescriptor = Xml.fromXmlFileToObject();
        checkFileValidation(gameDescriptor);
    }

    //this function is For the Web-App application xml file load
    public String loadGameFile(String xmlDescription) throws Exception{

            GameDescriptor gameDescriptor = SimpleJAXBMain.fromXmlFileToObject(xmlDescription);
            return checkFileValidation(gameDescriptor);

    }

    private String checkFileValidation(GameDescriptor gameDescriptor) throws Exception{
        int tempSmall = (gameDescriptor.getStructure().getBlindes().getSmall()).intValue();
        int tempBig = (gameDescriptor.getStructure().getBlindes().getBig()).intValue();
        if (tempSmall >= tempBig)
            throw new Exception("Invalid file, small is bigger or equal to big");

        numOfChipsPerBuy = gameDescriptor.getStructure().getBuy().intValue();

        int tempNumOfPlayer = gameDescriptor.getDynamicPlayers().getTotalPlayers();

        if (tempNumOfPlayer < 3 || tempNumOfPlayer > 6)
            throw new Exception("Invalid player number, supposed to be between 3-6");
             //just for test

        int tempHandsCount = (gameDescriptor.getStructure().getHandsCount()).intValue();
        if (tempHandsCount % tempNumOfPlayer != 0)
            throw new Exception("Invalid file, GameLogic.Hand count is not divided to the number of player");

        fixed = gameDescriptor.getStructure().getBlindes().isFixed();
        if (!fixed) {
            max_total_rounds = gameDescriptor.getStructure().getBlindes().getMaxTotalRounds().intValue();
            addition = gameDescriptor.getStructure().getBlindes().getAdditions().intValue();
            maxBig = BlindsHelper.calcMaxBig(BlindsHelper.calcTotalRounds(tempHandsCount),
                    addition, max_total_rounds, tempBig);
            if (maxBig > numOfChipsPerBuy / 2) {
                throw new Exception("Invalid file,the max possible big is higher then half of buy value");
            }
        }
        nameOfGame = gameDescriptor.getDynamicPlayers().getGameTitle();
        numOfChipsForsmall = tempSmall;
        numOfChipsForBig = tempBig;
        totalnumOfHands = tempHandsCount;
        originalBig = tempBig;
        originalSmall = tempSmall;
        numberOfMaxPlayersInGame = tempNumOfPlayer;
        return nameOfGame; //return the game title to check if it already exists (by the servlet)
    }

    private void checkThereIsHumanPlayerAtXml(List<Player> listOfPlayerFromXML) throws Exception
    {
        int counter =0;
        for (Player player : listOfPlayerFromXML) {
            if(player.getType().equals("Human"))
                counter++;
        }
        if(counter==0)
            throw new Exception("There is no human player at the XML");
    }

    private void checkThereIsNoPlayerWithSameId(List<Player> listOfPlayerFromXML) throws Exception
    {
        List<BigInteger> listOfId = new ArrayList<>();
        for (Player player : listOfPlayerFromXML) {
            if(listOfId.contains(player.getId()))
                throw new Exception("There is players with the same id");
            else
                listOfId.add(player.getId());
        }
    }

    public List<PlayerInfo> getAllPlayerInfo()
    {
        List<PlayerInfo> listOfPlayersInfo = new ArrayList<>();
        for(int i = 0; i< Utils.numOfPlayers; i++)
            listOfPlayersInfo.add(getPlayerInfo(i));

        return listOfPlayersInfo;
    }

    public PlayerInfo getPlayerInfo(int playerIndex){
        PlayerInfo tempPlayerInfo = new PlayerInfo(getTypeOfPlayer(playerIndex),
                getStatePlayer(playerIndex),getChipsPlayer(playerIndex),
                getBuysPlayer(playerIndex),getHandWonPlayer(playerIndex),
                getNumOfPlayer(playerIndex),getPlayerName(playerIndex),getPlayerId(playerIndex),getIsPlayerQuit(playerIndex),
                getCardsPlayerobjCard(playerIndex));
        return tempPlayerInfo;
    }

    public PlayerInfo getPlayerInfoFromReplayList(int playerIndex, int listIter){
        List<PlayerInfo> currRepPlayersStatus = handReplay.get(listIter).getPlayerStatus();
        PlayerInfo currRepPlayerStatus = currRepPlayersStatus.get(playerIndex);
        return currRepPlayerStatus;
    }

    public TableInfo getTableInfo(){
        TableInfo tempTableInfo = new TableInfo(getCashBox(),getCurrBet(),getCommunityCards(),getCurrentNumberOfHand(),
                                                getCurrNumOfRound());
        return tempTableInfo;
    }

    public TableInfo getTableInfoFromReplayList(int listIter){
        TableInfo currRepStatus = handReplay.get(listIter).getTableStatus();
        TableInfo tempTableInfo = new TableInfo(currRepStatus.getCashBox(),currRepStatus.getPot(),
                currRepStatus.getCommunityCards(),currRepStatus.getCurrHand(),currRepStatus.getCurrRound());
        return tempTableInfo;
    }

    public char getTypeOfPlayer(int numOfPlayer)
    {
        if(numOfPlayer == Utils.numOfPlayers)
        {
            return players.get(currHand.getCurrPlayer()).getType();
        }
            return players.get(numOfPlayer).getType();
    }
    public String getStatePlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getState();
    }

    public int getChipsPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getChips();
    }
    public int getBuysPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getBuys();
    }
    public int getHandWonPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getHandsWon();
    }

    public int getNumberOfHands()
    {
        return this.totalnumOfHands;
    }
    public String getCardsPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getCard()[0].toString() +" " +players.get(numOfPlayer).getCard()[1].toString();
    }
    public Card[] getCardsPlayerobjCard(int numOfPlayer)
    {
        return players.get(numOfPlayer).getCard();
    }
    public int getCurrBet()
    {
        if(currHand != null)
            return currHand.getCurrBet();

        return 0;
    }

    public int getNumberOfBuys()
    {
        int sumOfBuy =0;
        for(PokerPlayer player : players)
        {
            sumOfBuy += player.getBuys();
        }
        return sumOfBuy*numOfChipsPerBuy;
    }
    public int getCurrentNumberOfHand()
    {
        return this.numOfPlayHands;
    }

    public boolean getFixedState(){return this.fixed;}

    public int getAddition(){return this.addition;}

    public void callFlop(){
        currHand.flop();
    }

    public void callTurn(){
        currHand.turn();
    }

    public void callRiver(){
        currHand.river();
    }
    public Map<Integer,String> WhoIsTheWinner() throws Exception
    {
        WinnerMap = currHand.WhoIsTheWinner();
        return WinnerMap;
    }

    public Map<Integer,String> setTechniqWinners(Utils.RoundResult roundResult) {
        WinnerMap = currHand.setTechniqWinners(roundResult);
        return WinnerMap;
    }

    /*
    * This function calculates the number of chip a player (ot players) should get
    * base on the num of players who won (map size)
    * it also calcs the reminder of the cashbox that will be moved to the next hand
    * */
    public int calcWinnersChipPrize(){
        int cashBoxReminder = 0;
        int chipPrize = 0;
        int cashBoxBeforeDivide = getCashBox();
        int numOfWinners = WinnerMap.size();
        if (cashBoxBeforeDivide % numOfWinners == 0) {
            chipPrize = cashBoxBeforeDivide / numOfWinners;
        } else {
            cashBoxReminder = cashBoxBeforeDivide % numOfWinners;
            chipPrize = (cashBoxBeforeDivide - cashBoxReminder) / numOfWinners;
        }
        handTohandCashBox = cashBoxReminder;
        return chipPrize;
    }

    public void addChipsToPlayer(List<PlayerInfo> playersForBuy)
    {
        for(PlayerInfo P : playersForBuy){
            for(PokerPlayer player : players){
                if(P.getId() == player.getId()){
                    player.setBuysAndChips(numOfChipsPerBuy);
                    break;
                }
            }
        }
    }
    public Round.GameMoves convertIntToMove(String numOfMove)
    {
        Round.GameMoves res = null;
        switch(numOfMove)
        {
            case "1": res= Round.GameMoves.FOLD;
                break;
            case "2": res= Round.GameMoves.BET;
                break;
            case "3": res= Round.GameMoves.CALL;
                break;
            case "4": res= Round.GameMoves.CHECK;
                break;
            case "5": res= Round.GameMoves.RAISE;
                break;
            default:
                break;
        }
        return res;
    }
    public boolean validAmount(int amount) throws Exception
    {
        return currHand.isValidAmount(amount);
    }
    public GameMoveStatus getGameMoveStatus(String amount)
    {

        GameMoveStatus gameMoveStatus = new GameMoveStatus();
        if(amount == "")
        {
            gameMoveStatus.setValidAmount(false);
            gameMoveStatus.setError("Empty field");
            return gameMoveStatus;
        }
        try{
            int amountInt = Integer.parseInt(amount);
            if(validAmount(amountInt))
                gameMoveStatus.setValidAmount(true);
        }
        catch (NumberFormatException e) {
            gameMoveStatus.setValidAmount(false);
            gameMoveStatus.setError("Invalid input");
        }
        catch (Exception e) {
            gameMoveStatus.setValidAmount(false);
            gameMoveStatus.setError(e.getMessage());

        }

        return gameMoveStatus;
    }
    public int getLastBetOfTheCurrPlayer()
    {
        return currHand.getLastBetOfTheCurrPlayer();
    }
    public int getLastBetOfSpecificPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getBet();
    }
    public List<Card> getCommunityCards()
    {
        return currHand.getCommunityCards();
    }

    public void setPlayHand()
    {
        numOfPlayHands++;
    }
    public int getCurrNumOfRound()
    {
        if(currHand != null)
         return currHand.getCurrNumOfRound();
        else
            return 0;
    }

    public String getLastMove()
    {
        return currHand.getLastMove().toString();
    }
    public void setTimeOfGame()
    {
        timeOfStartGame = System.currentTimeMillis();
    }

    public String calcTimeFromStartingGame()
    {
        long endTime = System.currentTimeMillis();
        Date myTime = new Date((endTime - timeOfStartGame)/1000);
        String time =  String.valueOf(myTime.getTime()/60) + ":" + String.valueOf(myTime.getTime()%60);
        return time;
    }
    public int getCashBox()
    {
        if(currHand != null)
            return currHand.getCashBox();

        return 0;
    }
    public int getNumOfPlayer(int indexOfPlayer)
    {
        return players.get(indexOfPlayer).getNumOfPlayer();
    }



    public String getPlayerName(int indexOfPlayer)
    {
        return players.get(indexOfPlayer).getName();
    }


    public int getPlayerId(int indexOfPlayer)
    {
        return players.get(indexOfPlayer).getId();
    }

    public boolean getIsPlayerQuit(int indexOfPlayer)
    {
        return players.get(indexOfPlayer).getQuit();
    }

    public Boolean getIfPlayerQuit(int indexOfPlayer)
    {
        return players.get(indexOfPlayer).getQuit();
    }

    public int getNumOfChipsForsmall() {
        return numOfChipsForsmall;
    }

    public int getNumOfChipsForBig() {
        return numOfChipsForBig;
    }
    public Boolean humanPlayerHasNoChips()
    {
        for(PokerPlayer player : players)
        {
            if(player.getType() == 'H')
            {
                if(player.getChips() == 0)
                    return false;

                break;
            }

        }
        return true;
    }

    public void restartGameForNewGame()
    {
        deck.InitCardInDeck();
        players.clear();
        currHand= null;
        timeOfStartGame=0;
        numOfPlayHands=0;
        dilerIndex = 0;
        handTohandCashBox=0;
        numOfChipsForsmall=0;
        numOfChipsForBig=0;
        numOfChipsPerBuy=0;
        dilerRoundsCounter = 0;
    }

    public int getLastGenerateAmount()
    {
        return currHand.getLastGenerateAmount();
    }

    public void restartCurrentGame()
    {
        deck.InitCardInDeck();
        for(PokerPlayer player : players)
        {
            player.resetPlayer(numOfChipsPerBuy);
        }
        currHand= null;
        numOfPlayHands=0;
        dilerIndex = 0;
        handTohandCashBox=0;
        numOfChipsForsmall=originalSmall;
        numOfChipsForBig=originalBig;

    }

    public List<StatusSnapShot> getHandReplayList(){
        return handReplay;
    }

    public void initHandReplay(){
        if(!handReplay.isEmpty())
            handReplay.clear();
    }

    public void addSnapShotToReplay(){
        handReplay.add(saveStatusSnapShot(currHand.getIndexOfLastTurn()));
    }
    private StatusSnapShot saveStatusSnapShot(int playerIndex){
        List<PlayerInfo> tempList = new ArrayList<>();
        for(int i = 0; i < Utils.numOfPlayers; i++){
            PlayerInfo currPinfo = getPlayerInfo(i);
            tempList.add(i,currPinfo);
        }
        StatusSnapShot result = new StatusSnapShot(tempList,getTableInfo(),
                getLastMove(),getLastBetOfSpecificPlayer(playerIndex),playerIndex,
                getCurrentNumberOfHand(),getCurrNumOfRound());
        return result;
    }

    public int getNumOfChipsPerBuy()
    {
        return numOfChipsPerBuy;
    }

    public Map<Integer,String> getWinnerMap() {
        return WinnerMap;
    }

    public int getCurrPlayer() {
        return currHand.getCurrPlayer();
    }

    public int getReplayListSize() {
        return handReplay.size();
    }

    public int getCurrTurnFromReplayList(int listIter){
        return handReplay.get(listIter).getCurrPlayer();
    }

    public String  getLastMoveFromRelayList(int listIter){
        return handReplay.get(listIter).getLastGameMove();
    }


    public void setMoveForAmountValidatoin(Round.GameMoves gameMoves) {
        currHand.setMoveForAmountValidatoin(gameMoves);
    }

    public int getCurrHandFromReplay(int listIter) {
        return handReplay.get(listIter).getCurrHand();
    }

    public int getCurrRoundFromReplay(int listIter) {
        return handReplay.get(listIter).getCurrRound();

    }

    public void setQuitFromTheGameToCurrentPlayer()
    {
        players.get(getCurrPlayer()).setQuit(true);
        players.get(getCurrPlayer()).setFinalQuit(true);
    }

    public boolean isThereAreHumanPlayer()
    {
        for(PokerPlayer player : players)
        {
            if(player.getFinalQuit()==false && player.getType() == 'H')
                return true;
        }
        return false;
    }

    public boolean isThereAreMoreThenOnePlayer()
    {
        int counter=0;
        for(PokerPlayer player : players)
        {
            if(player.getFinalQuit()==false)
                counter++;
        }

        if(counter > 1)
            return true;
        else
            return false;
    }

    public int getNumberOfMaxPlayersInGame() {
        return numberOfMaxPlayersInGame;
    }

    public GameDetailsInfo getGameDetailsInfo(){
        GameDetailsInfo tempGameDetailsInfo = new GameDetailsInfo(getCurrentNumberOfHand(),
                getCurrNumOfRound(),numOfChipsForsmall,numOfChipsForBig,getCurrBet(),getCashBox());
        return tempGameDetailsInfo;
    }

    public String getNameOfGame()
    {return nameOfGame;}

    public int getPlayerIndexByName(String name)
    {
        for(int i=0;i<players.size();i++)
        {
            if(players.get(i).getName() == name)
                return i;
        }
        return -1;
    }

   public movesInfo getMoveInfo()
   {
       return new movesInfo (validateMove("1"),validateMove("2"),validateMove("3")
               ,validateMove("4"),validateMove("5"));
   }

   public void setQuitToPlayerByName(String name)
   {
       PokerPlayer player = players.get(getPlayerIndexByName(name));
       player.setQuit(true);
       player.setFinalQuit(true);
   }

}
