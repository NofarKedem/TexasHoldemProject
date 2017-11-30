import XMLobject.GameDescriptor;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private Deck deck;
    //private List<Hand> hands;
    private List<Player> players;
    private Hand currHand;
    private int timeOfGame;
    private int totalnumOfHands;
    private int numOfPlayHands;
    private static int dilerIndex = 0;
    private int maxCashBox;
    public static int small = 5; //to be updated from the XML
    public static int big = 10;//to be updated from the XML


    public Server(){
        //currHand = 0;
        timeOfGame = 0;
        totalnumOfHands = 0;
        maxCashBox = 0;
        players = new ArrayList(4);
        //hands = new ArrayList<>();
        deck = new Deck();
    }

    public void initializePlayers(int numOfHPlayers, int numOfCPlayers){
        for(int i=0;i < numOfHPlayers; i++){
            //need to add methods update and updates chips from XML parameter
            players.add(new HumanPlayer('H', " ",100,1));
        }
        for(int i=0;i < numOfCPlayers; i++){
            //need to add methods update and updates chips from XML parameter
            players.add(new HumanPlayer('C', " ",100,1));
        }
        dilerIndex = calculateDilerIndex(dilerIndex);
        initPlayersState();
        //currHand.setFirstPlayer();

    }
    public void setFirstPlayer()
    {
        currHand.setFirstPlayer();
    }

    public void initializeHand(){
        deck.allCardsInDeck();
        currHand = new Hand(deck);
        currHand.setHandPlayers(players);
    }
    public void initRound()
    {
        currHand.initRound();
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
        currHand.blindBet();
    }


    public Utils.RoundResult gameMove(int LastGameMove, int amount){
        return currHand.gameMove(convertIntToMove(LastGameMove), amount);
    }

    public Utils.RoundResult playWithComputer(){
        return currHand.playWithComputer();
    }

    public boolean validateMove(int LastGameMove){
        return currHand.isValidGameMove(convertIntToMove(LastGameMove));
    }

    //hadar changes
    public Boolean loadFile(String filePath)
    {
        SimpleJAXBMain Xml = new SimpleJAXBMain("ex1-basic.xml");
        GameDescriptor gameDescriptor = Xml.fromXmlFileToObject();

        return false;
    }

    char getTypeOfPlayer(int numOfPlayer)
    {
        if(numOfPlayer == 4 )
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
    int getTheCurrBet(int numOfPlayer)
    {
        return currHand.getCurrBet();
    }

    int getTime()
    {
        return 1;
    }
    int getNumberOfBuys()
    {
        return 2;
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
    public String WhoIsTheWinner()
    {
        return "a";
    }

    public void addChipsToPlayer()
    {
        for (Player p : players)
        {
            if(p.getType()== 'H') {
                p.setBuys(1);
                break;
            }
        }
    }
    private Round.GameMoves convertIntToMove(int numOfMove)
    {
        Round.GameMoves res = null;
        switch(numOfMove)
        {
            case 1: res= Round.GameMoves.FOLD;
                break;
            case 2: res= Round.GameMoves.BET;
                break;
            case 3: res= Round.GameMoves.CALL;
                break;
            case 4: res= Round.GameMoves.CHECK;
                break;
            case 5: res= Round.GameMoves.RAISE;
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
}
