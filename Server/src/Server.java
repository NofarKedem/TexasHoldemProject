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
    public static int small = 5; //to be updated from the XML
    public static int big = 10;//to be updated from the XML

    public Server(){
        //currHand = 0;
        timeOfGame = 0;
        totalnumOfHands = 0;
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
        currHand.setFirstPlayer();

    }

    public void initializeHand(){
        deck.allCardsInDeck();

        Hand currHand = new Hand(deck);
        totalnumOfHands++;
        currHand = new Hand(deck);
        totalnumOfHands++;
        currHand.getHandPlayers(players);
        initRound();
        //hands.add(currHand);

    }
    private void initPlayersState(){
        players.get(dilerIndex).updateState("D");
        int small = calcSmallIndex(dilerIndex);
        players.get(small).updateState("S");
        players.get(calcBigIndex(small)).updateState("B");
    }

    public void initRound(){
        currHand.initRound();
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


    public boolean gameMove(Round.GameMoves gameMove, int amount){
        return currHand.gameMove(gameMove, amount);
    }

    public boolean playWithCumputer(){
        return currHand.playWithComputer();
    }

    public boolean validateMove(){
        return currHand.isValidGameMove();
    }

    //hadar changes
    public String loadFile(String filePath)
    {
        return "a";
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
        return players.get(numOfPlayer).getCard().toString();
    }
    int getNumberOfBets(int numOfPlayer)
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
}
