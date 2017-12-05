import javafx.util.Pair;

import java.util.*;

public class Hand {
    private Round currRound;
    private List<Round> handRounds; //Is this redundant?
    private Deck deck;
    private List<Card> communityCards;
    private List<Player> handPlayers;
    private int cashBox;


    public Hand(Deck deck){
        this.deck = deck;
        handRounds = new ArrayList<>(4);
        communityCards = new ArrayList<>(5);
        cashBox = 0; //the value is just for testing
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
        Utils.RoundResult result = currRound.playWithComputer();
        if(result == Utils.RoundResult.CLOSEROUND || result == Utils.RoundResult.ENDGAME){
            this.cashBoxAfterRound();
        }
        return result;
    }

    public Round.GameMoves getLastMove()
    {
        return currRound.getLastMove();
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

    public void setHandPlayers(List<Player> handPlayers) {
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

    public void blindBet(int numOfChipsForBig,int numOfChipsForsmall){
        currRound.blindBet(numOfChipsForBig,numOfChipsForsmall);
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
    public Boolean isPlayerHasEnoughChips()
    {
        return currRound.isPlayerHasEnoughChips();
    }

    public boolean isValidAmount(int amount){
        return currRound.isValidAmount(amount);
    }

    public int getLastBetOfTheCurrPlayer()
    {
        return currRound.getLastBetOfTheCurrPlayer();
    }
    public List<Card> getCommunityCards()
    {
        return this.communityCards;
    }

    public int getCurrNumOfRound()
    {
        return handRounds.size();
    }

    public Map<Integer,String> WhoIsTheWinner() throws Exception {
        Map<Integer,String> WinnerMap;
        WinLogic winner = new WinLogic();

        WinnerMap =  winner.findTheWinner(communityCards, handPlayers);
        updateTheWinnerWithCashBox(WinnerMap);
        return WinnerMap;
    }

    public void updateTheWinnerWithCashBox(Map<Integer,String> WinnerMap)
    {
        for (Integer numOfPlayer : WinnerMap.keySet()) {
            Player winner = handPlayers.get(numOfPlayer-1);
            if (WinnerMap.size() == 1) {
                winner.updateWinnerChips(cashBox);
                winner.updateHandsWon();
                cashBox = 0;
            }
            else if (WinnerMap.size() == 2) {
                winner.updateWinnerChips(cashBox / 2);
                winner.updateHandsWon();
                if(cashBox%2 == 0){
                    cashBox = 0;
                }
                else{
                    cashBox = 1;
                }
            }

         }
    }
    public int getCashBox()
    {
        return currRound.getCashBox();
    }
}
