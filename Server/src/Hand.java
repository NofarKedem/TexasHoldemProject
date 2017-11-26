import java.util.ArrayList;
import java.util.List;

public class Hand {
    private Round currRound;
    private List<Round> handRounds; //Is this redundant?
    private Deck deck;
    private List<Card> communityCards;
    private List<Player> handPlayers;
    private int cashBox;
    private ComputerPlayerService CPlayerService;

    public Hand(Deck deck){
        this.deck = deck;
        handRounds = new ArrayList<>(4);
        communityCards = new ArrayList<>(5);
        cashBox = 200; //the value is just for testing
        CPlayerService = new ComputerPlayerService();
    }

    public void initRound(){
        currRound = new Round(handPlayers,cashBox);
        handRounds.add(currRound); //Is this redundant?
    }

    public void cardDistribusion (){
        //2 cards to each player
        for(Player player : handPlayers){
            player.setPlayerHandCards(deck.drawFromDeck(2));
        }
    }

    public Utils.RoundResult gameMove(Round.GameMoves gameMove, int amount){
        Utils.RoundResult result = currRound.gameMove(gameMove,amount);
        if(result == Utils.RoundResult.CLOSEROUND || result == Utils.RoundResult.ENDGAME){
            this.cashBoxAfterRound();
        }
        return result;
    }

    public Utils.RoundResult playWithComputer(){
        Round.GameMoves gameMove = CPlayerService.generateMove();
        int amount = CPlayerService.generateAmount();
        Utils.RoundResult result = currRound.gameMove(gameMove,amount);
        if(result == Utils.RoundResult.CLOSEROUND || result == Utils.RoundResult.ENDGAME){
            this.cashBoxAfterRound();
        }
        return result;
    }

    private void cashBoxAfterRound(){
        cashBox = currRound.getCashBox();//update the hand cashBox after the current round
    }

    public void flop(){
        Card[] ArrOfTempCard = deck.drawFromDeck(3);
        for(Card cardTemp : ArrOfTempCard)
            communityCards.add(cardTemp);
    }
    public void turn(){
        Card[] ArrOfTempCard = deck.drawFromDeck(1);
        for(Card cardTemp : ArrOfTempCard)
            communityCards.add(cardTemp);
    }
    public void river(){
        Card[] ArrOfTempCard = deck.drawFromDeck(1);
        for(Card cardTemp : ArrOfTempCard)
            communityCards.add(cardTemp);
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

    public int getCurrPlayer()
    {
        return currRound.getCurrPlayer();
    }
    public int getCurrBet()
    {
        return currRound.getCurrBet();
    }


    public boolean isValidGameMove(Round.GameMoves LastGameMove){
        return currRound.isValidGameMove(LastGameMove);
    }

    public int getLastBetOfTheCurrPlayer()
    {
        return currRound.getLastBetOfTheCurrPlayer();
    }
    public List<Card> getCommunityCards()
    {
        return this.communityCards;
    }
}
