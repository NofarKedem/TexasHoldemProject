import XMLobject.GameDescriptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Server {
    private Deck deck;
    private List<Player> players;
    private Hand currHand;
    private long timeOfStartGame;
    private int totalnumOfHands;
    private int numOfPlayHands;
    private static int dilerIndex = 0;
    private int handTohandCashBox;
    private int numOfChipsForsmall;
    private int numOfChipsForBig;
    private int numOfChipsPerBuy;

    private List<StatusSnapShot> handReplay = new ArrayList<>();





    public Server(){
        timeOfStartGame = 0;
        totalnumOfHands = 0;
        players = new ArrayList(Utils.numOfPlayers);
        deck = new Deck();
        handTohandCashBox = 0;
    }

    public void initializePlayers(int numOfHPlayers, int numOfCPlayers){
        for(int i=0;i < numOfHPlayers; i++){
            players.add(new Player('H', " ",numOfChipsPerBuy,1,1));
        }
        for(int i=0;i < numOfCPlayers; i++){
            players.add(new Player('C', " ",numOfChipsPerBuy,1,numOfHPlayers+1+i));
        }
        dilerIndex = calculateDilerIndex(dilerIndex);
        initPlayersState();

    }

    public void closeTheHand(int cashBoxReminder){
        for(Player P : players){
            P.resetState();
        }
        dilerIndex = calculateDilerIndex(dilerIndex);
        initPlayersState();
        handTohandCashBox = cashBoxReminder;
    }

    public void initializeHand(){
        deck.InitCardInDeck();
        currHand = new Hand(deck, handTohandCashBox);

        initPlayersQuitState();
        currHand.setHandPlayers(players);
    }
    public void initRound()
    {
        currHand.initRound();
        for(Player player : players)
        {
            player.initBet();
        }
        currHand.setFirstPlayer();
    }

    private void initPlayersState(){
        players.get(dilerIndex).updateState("D");
        int small = calcSmallIndex(dilerIndex);
        players.get(small).updateState("S");
        players.get(calcBigIndex(small)).updateState("B");
    }
//will might be needed for the 2nd exercise
//    private void initPlayersState(){
//        int small = calcSmallIndex(dilerIndex);
//        int big = calcBigIndex(small);
//        while(players.get(small).getChips() < numOfChipsForsmall || players.get(big).getChips() < numOfChipsForBig){
//            dilerIndex = calculateDilerIndex(dilerIndex);
//            small = calcSmallIndex(dilerIndex);
//            big = calcBigIndex(small);
//        }
//        players.get(dilerIndex).updateState("D");
//        players.get(small).updateState("S");
//        players.get(calcBigIndex(small)).updateState("B");
//    }

    private void initPlayersQuitState(){
        for(Player player : players){
            if(player.getChips() > 0)
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

    public void cardDistribusionToPlayer(){ //per Hand
        this.currHand.cardDistribusion();
    }

    public boolean blindBet(){
        boolean ret = currHand.blindBet(numOfChipsForBig,numOfChipsForsmall);
        addBlindBetSnapShots();
        return ret;
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

        int tempSmall = gameDescriptor.getStructure().getBlindes().getSmall();
        int tempBig = gameDescriptor.getStructure().getBlindes().getBig();
        if(tempSmall >= tempBig)
            throw new Exception("Invalid file, small is bigger or equal to big");
        int tempHandsCount = gameDescriptor.getStructure().getHandsCount();
        if(tempHandsCount%Utils.numOfPlayers != 0)
            throw new Exception("Invalid file, Hand count is not divided to the number of player");

        numOfChipsForsmall = tempSmall;
        numOfChipsForBig = tempBig;
        totalnumOfHands = tempHandsCount;
        numOfChipsPerBuy = gameDescriptor.getStructure().getBuy();

    }

    PlayerInfo getPlayerInfo(int playerIndex){
        PlayerInfo tempPlayerInfo = new PlayerInfo(getTypeOfPlayer(playerIndex),
                getStatePlayer(playerIndex),getChipsPlayer(playerIndex),
                getBuysPlayer(playerIndex),getHandWonPlayer(playerIndex),
                getNumOfPlayer(playerIndex));
        return tempPlayerInfo;
    }

    TableInfo getTableInfo(){
        TableInfo tempTableInfo = new TableInfo(getCashBox(),getCurrBet(),getCommunityCards());
        return tempTableInfo;
    }

    char getTypeOfPlayer(int numOfPlayer)
    {
        if(numOfPlayer == Utils.numOfPlayers)
        {
            return players.get(currHand.getCurrPlayer()).getType();
        }
            return players.get(numOfPlayer).getType();
    }
    String getStatePlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getState();
    }

    int getChipsPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getChips();
    }
    int getBuysPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getBuys();
    }
    int getHandWonPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getHandsWon();
    }

    int getNumberOfHands()
    {
        return this.totalnumOfHands;
    }
    String getCardsPlayer(int numOfPlayer)
    {
        return players.get(numOfPlayer).getCard()[0].toString() +" " +players.get(numOfPlayer).getCard()[1].toString();
    }
    int getCurrBet()
    {
        return currHand.getCurrBet();
    }

    int getNumberOfBuys()
    {
        int sumOfBuy =0;
        for(Player player : players)
        {
            sumOfBuy += player.getBuys();
        }
        return sumOfBuy*numOfChipsPerBuy;
    }
    int getCurrentNumberOfHand()
    {
        return this.numOfPlayHands;
    }


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
        return currHand.WhoIsTheWinner();
    }

    public Map<Integer,String> setTechniqWinners(Utils.RoundResult roundResult) {
        return currHand.setTechniqWinners(roundResult);
    }

    public void addChipsToPlayer()
    {
        for (Player p : players)
        {
            if(p.getType()== 'H') {
                p.setBuysAndChips(numOfChipsPerBuy);
                break;
            }
        }
    }
    private Round.GameMoves convertIntToMove(String numOfMove)
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
    public boolean validAmount(int amount)
    {
        return currHand.isValidAmount(amount);
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
        return currHand.getCurrNumOfRound();
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
        return currHand.getCashBox();
    }
    public int getNumOfPlayer(int indexOfPlayer)
    {
        return players.get(indexOfPlayer).getNumOfPlayer();
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
        for(Player player : players)
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
    }

    public int getLastGenerateAmount()
    {
        return currHand.getLastGenerateAmount();
    }

    public void restartCurrentGame()
    {
        deck.InitCardInDeck();
        players.clear();
        currHand= null;
        numOfPlayHands=0;
        dilerIndex = 0;
        handTohandCashBox=0;

    }

    public void initHandReplay(){
        if(!handReplay.isEmpty())
            handReplay.clear();
    }
    public void addBlindBetSnapShots(){
        int smallIndex = calcSmallIndex(dilerIndex);
        StatusSnapShot temp = saveStatusSnapShot(smallIndex);
        temp.getTableStatus().updateData(getCashBox() - getCurrBet(),getCurrBet() - getCurrBet());
        handReplay.add(saveStatusSnapShot(smallIndex));
        handReplay.add(saveStatusSnapShot(calcBigIndex(smallIndex)));
    }
    public void addSnapShotToReplay(){
        //the curr player is the curr turn but we need to record the turn that just have been ended
        int indexOfLastTurn;
        int indexOfNextTurn = currHand.getCurrPlayer();
        if(indexOfNextTurn > 0)
            indexOfLastTurn = indexOfNextTurn - 1;
        else
            indexOfLastTurn = Utils.numOfPlayers - 1;
        handReplay.add(saveStatusSnapShot(indexOfLastTurn));

    }
    private StatusSnapShot saveStatusSnapShot(int playerIndex){
        StatusSnapShot result = new StatusSnapShot(getPlayerInfo(playerIndex),getTableInfo(),
                getLastMove(),getLastBetOfSpecificPlayer(playerIndex));
        return result;
    }
}
