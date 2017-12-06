import XMLobject.GameDescriptor;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Server {
    private Deck deck;
    //private List<Hand> hands;
    private List<Player> players;
    private Hand currHand;
    private long timeOfStartGame;
    private int totalnumOfHands;
    private int numOfPlayHands;
    private static int dilerIndex = 0;
    private int maxCashBox;
    private int numOfChipsForsmall; //to be updated from the XML
    private int numOfChipsForBig;//to be updated from the XML
    private int numOfChipsPerBuy; //to be updated from the XML



    public Server(){
        //currHand = 0;
        timeOfStartGame = 0;
        totalnumOfHands = 0;
        maxCashBox = 0;
        players = new ArrayList(Utils.numOfPlayer);
        //hands = new ArrayList<>();
        deck = new Deck();
    }

    public void initializePlayers(int numOfHPlayers, int numOfCPlayers){
        for(int i=0;i < numOfHPlayers; i++){
            //need to add methods update and updates chips from XML parameter
            players.add(new Player('H', " ",numOfChipsPerBuy,1,1));
        }
        for(int i=0;i < numOfCPlayers; i++){
            //need to add methods update and updates chips from XML parameter
            players.add(new Player('C', " ",numOfChipsPerBuy,1,numOfHPlayers+1+i));
        }
        dilerIndex = calculateDilerIndex(dilerIndex);
        initPlayersState();

    }

    public void closeTheHand(){
        for(Player P : players){
            P.resetState();
        }
        dilerIndex = calculateDilerIndex(dilerIndex);
        initPlayersState();
    }

    public void initializeHand(){
        deck.allCardsInDeck();
        currHand = new Hand(deck);
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

    public void blindBet(){
        currHand.blindBet(numOfChipsForBig,numOfChipsForsmall);
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

    //hadar changes
    public void loadFile(String filePath)throws Exception
    {
        SimpleJAXBMain Xml = new SimpleJAXBMain(filePath);
        GameDescriptor gameDescriptor = Xml.fromXmlFileToObject();

        int tempSmall = gameDescriptor.getStructure().getBlindes().getSmall();
        int tempBig = gameDescriptor.getStructure().getBlindes().getBig();
        if(tempSmall >= tempBig)
            throw new Exception("Invalid file, small is bigger or equal to big");
        int tempHandsCount = gameDescriptor.getStructure().getHandsCount();
        if(tempHandsCount%Utils.numOfPlayer != 0)
            throw new Exception("Invalid file, Hand count is not divided to the number of player");

        numOfChipsForsmall = tempSmall;
        numOfChipsForBig = tempBig;
        totalnumOfHands = tempHandsCount;
        numOfChipsPerBuy = gameDescriptor.getStructure().getBuy();

    }

    char getTypeOfPlayer(int numOfPlayer)
    {
        if(numOfPlayer == Utils.numOfPlayer )
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
}
