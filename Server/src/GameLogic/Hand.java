package GameLogic;

import GameLogic.BlindsHelper;
import GameLogic.Deck;

import java.util.*;

public class Hand {
    private Round currRound;
    private List<Round> handRounds; //Is this redundant?
    private Deck deck;
    private List<Card> communityCards;
    private List<PokerPlayer> handPlayers;
    private int cashBox;


    public Hand(Deck deck, int cashBox){
        this.deck = deck;
        handRounds = new ArrayList<>(4);
        communityCards = new ArrayList<>(5);
        this.cashBox = cashBox;
    }

    public void initRound(){
        currRound = new Round(handPlayers,cashBox);
        handRounds.add(currRound); //Is this redundant?
    }

    public void cardDistribusion (){
        //2 cards to each player
        for(PokerPlayer player : handPlayers){
            player.setPlayerHandCards(deck.drawFromDeck(2));
        }
    }

    public Utils.RoundResult gameMove(Round.GameMoves gameMove, int amount){
        Utils.RoundResult result = currRound.gameMove(gameMove,amount);
        if(result != Utils.RoundResult.NOTHINGHAPPEN){
            this.cashBoxAfterRound();
        }
        return result;
    }

    public Utils.RoundResult playWithComputer(){
        Utils.RoundResult result = currRound.playWithComputer();
        if(result != Utils.RoundResult.NOTHINGHAPPEN){
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

    public void setHandPlayers(List<PokerPlayer> handPlayers) {
        this.handPlayers = handPlayers;
    }
//
//    public boolean blindBet(int numOfChipsForBig,int numOfChipsForsmall){
//
//        return currRound.blindBet(numOfChipsForBig,numOfChipsForsmall);
//    }
    public boolean blindSmall(int numOfChipsForsmall){
        return currRound.blindSmall(numOfChipsForsmall);
    }

    public boolean blindBig(int numOfChipsForBig){
        return currRound.blindBig(numOfChipsForBig);
    }

    public void setFirstPlayer(){
        currRound.setCurrPlayer();
    }

    public int getCurrPlayer()
    {
        return currRound.getCurrPlayer();
    }
    public int getIndexOfLastTurn(){
        return currRound.getIndexOfLastTurn();
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

    public boolean isValidAmount(int amount)throws Exception{
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

    public boolean checkIfAllQuit(){
        int quitCounter = 0;
        for(PokerPlayer player : handPlayers){
            if(player.getFinalQuit()){
                quitCounter++;
            }
        }
        if(handPlayers.size() - quitCounter <=1){
            return true;
        }
        else{
            return false;
        }
    }

    public Map<Integer,String> setTechniqWinners(Utils.RoundResult roundResult) {
        Map<Integer,String> WinnerMap = new HashMap<>();
        WinLogic winner = new WinLogic();
        if(roundResult == Utils.RoundResult.HUMANFOLD)
            WinnerMap =  winner.setTechniqWinnersForCompPlayer(handPlayers);
        else if(roundResult == Utils.RoundResult.ALLFOLDED)
            WinnerMap =  winner.setTechniqWinnersForHumanPlayer(handPlayers);

        updateTheWinnerWithCashBox(WinnerMap);
        return WinnerMap;

    }

    public void updateTheWinnerWithCashBox(Map<Integer,String> WinnerMap)
    {
        for (Integer numOfPlayer : WinnerMap.keySet()) {
            PokerPlayer winner = handPlayers.get(numOfPlayer-1);
            /*
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
            */

            if(cashBox % WinnerMap.size() == 0){
                winner.updateWinnerChips(cashBox/WinnerMap.size());
                winner.updateHandsWon();
            }
            else{
                int reminderInCashBox = cashBox%WinnerMap.size();
                winner.updateWinnerChips((cashBox - reminderInCashBox)/WinnerMap.size());
                winner.updateHandsWon();
            }

         }
        cashBox = cashBox%WinnerMap.size();
    }
    public int getCashBox()
    {
        return currRound.getCashBox();
    }

    public int getLastGenerateAmount()
    {
        return currRound.getLastGenerateAmount();
    }

    public void setMoveForAmountValidatoin(Round.GameMoves gameMoves)
    {
        currRound.setMoveForAmountValidatoin(gameMoves);

    }

}
