package GameLogic;


public class GameDetailsInfo {
    private int currHand;
    private int currRound;
    private int numOfChipsForsmall;
    private int numOfChipsForBig;

    GameDetailsInfo(int currHand, int currRound, int numOfChipsForSmall, int numOfChipsForBig){

        this.currHand = currHand;
        this.currRound = currRound;
        this.numOfChipsForsmall = numOfChipsForSmall;
        this.numOfChipsForBig = numOfChipsForBig;
    }
}
