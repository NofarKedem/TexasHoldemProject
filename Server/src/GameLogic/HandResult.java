package GameLogic;

import java.util.List;

public class HandResult {
    private boolean isHandEnd;
    private boolean isAllHandsEnd;
    List<WinnersDetails> winnersDetails;
    private boolean isNewHand;
    private boolean isEnoughPlayer;


    public HandResult(boolean isHandEnd,boolean isAllHandsEnd, List winnersDetails){
        this.isHandEnd = isHandEnd;
        this.winnersDetails = winnersDetails;
        this.isAllHandsEnd = isAllHandsEnd;
        this.isNewHand = false;
        this.isEnoughPlayer = true;
    }
    public HandResult()
    {
        this.isHandEnd = false;
        this.winnersDetails = null;
        this.isAllHandsEnd = false;
        this.isNewHand = false;
        this.isEnoughPlayer = true;
    }

    public boolean isHandEnd() {
        return isHandEnd;
    }

    public List<WinnersDetails> getWinnersDetails() {
        return winnersDetails;
    }

    public void setNewHand(boolean newHand) {
        isNewHand = newHand;
    }

    public void setEnoughPlayer(boolean enoughPlayer) {
        isEnoughPlayer = enoughPlayer;
    }
}
