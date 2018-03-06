package GameLogic;


public class GameDetailsInfo {
    private int currHand;
    private int currRound;
    private int numOfChipsForsmall;
    private int numOfChipsForBig;
    private int currentBet;
    private int cashBox;

    GameDetailsInfo(int currHand, int currRound, int numOfChipsForSmall, int numOfChipsForBig,
                    int currentBet,int cashBox){

        this.currHand = currHand;
        this.currRound = currRound;
        this.numOfChipsForsmall = numOfChipsForSmall;
        this.numOfChipsForBig = numOfChipsForBig;
        this.currentBet = currentBet;
        this.cashBox = cashBox;
    }
}
