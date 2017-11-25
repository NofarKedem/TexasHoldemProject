import java.util.ArrayList;
import java.util.List;

public class Hand {
    private Round currRound;
    private List<Round> handRounds; //Is this redundant?
    private Deck deck;
    private Card[] communityCards;
    private List<Player> handPlayers;
    private int cashBox;

    public Hand(Deck deck){
        this.deck = deck;
        handRounds = new ArrayList<>(4);
        communityCards = new Card[5];
        cashBox = 200; //the value is just for testing
    }
    public void play(){

        int index = 1;
        initAndStartRound(index);
        flop();
        initAndStartRound(++index);
        turn();
        initAndStartRound(++index);
        river();
        initAndStartRound(++index);
    }

    private void initAndStartRound(int index){
        currRound = new Round(handPlayers,index,cashBox);
        currRound.startRound();
        cashBox = currRound.getCashBox();//update the hand cashBox after the current round
        handRounds.add(currRound); //Is this redundant?
    }

    public void cardDistribusion (){
        //2 cards to each player
        for(Player player : handPlayers){
            player.getPlayerHandCards(deck.drawFromDeck(2));
        }
    }

    public boolean gameMove(Round.GameMoves gameMove, int amount){
       return currRound.gameMove(gameMove,amount);
    }



    private void flop(){
        communityCards = deck.drawFromDeck(3);
    }
    private void turn(){
        Card[] temp = deck.drawFromDeck(1);
        communityCards[3] = temp[0];
    }
    private void river(){
        Card[] temp = deck.drawFromDeck(1);
        communityCards[4] = temp[0];
    }

    public void getHandPlayers(List<Player> handPlayers){
        this.handPlayers = handPlayers;
    }
    /*
    public Card[] drawCardsForPlayer(int numOfCards){
        return deck.drawFromDeck(numOfCards);
    }

    public void drawCommunityCard(int communityCardIndex){
        //drawFromDeck returns an array of cards that contain only one card in index 0
        communityCards[communityCardIndex] = (deck.drawFromDeck(1))[0];
    }
    */

    public void blindBet(){
        currRound.blindBet();
    }

    public void setFirstPlayer(){
        currRound.setCurrPlayer();
    }
}
