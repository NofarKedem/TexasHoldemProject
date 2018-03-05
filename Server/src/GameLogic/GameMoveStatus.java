package GameLogic;

public class GameMoveStatus {
    private boolean isValidAmount;
    private String error;
   // private boolean isHandEnd;
    private boolean isRoundEnd;
    private int numRound;

    public void setValidAmount(boolean isValidAmount)
    {
        this.isValidAmount = isValidAmount;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public boolean getIsValidAmount()
    {
        return isValidAmount;
    }

    public int getNumRound() {
        return numRound;
    }
/*
    public void setEndHand(boolean endHand) {
        isHandEnd = endHand;
    }
    public boolean isEndHand() {
        return isHandEnd;
    }
    */

    public void setNumRound(int numRound) {
        this.numRound = numRound;
    }

    public void setRoundEnd(boolean roundEnd) {
        isRoundEnd = roundEnd;
    }
}
