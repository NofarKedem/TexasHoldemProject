import java.util.ArrayList;
import java.util.List;

public class Server {
    private Deck deck;
    private List<Hand> hands;
    private List<Player> players;
    private int currHand;
    private int timeOfGame;
    private static int dilerIndex = 0;
    public static int small = 5; //to be updated from the XML
    public static int big = 10;//to be updated from the XML

    public Server(){
        currHand = 0;
        timeOfGame = 0;
        players = new ArrayList(4);
        hands = new ArrayList<>();
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
    }

    public void initializeHand(){
        deck.allCardsInDeck();
        Hand currHand = new Hand(deck);
        dilerIndex = calculateDilerIndex(dilerIndex);
        initPlayersState();
        currHand.getHandPlayers(players);

        currHand.play();

        hands.add(currHand);

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

}
