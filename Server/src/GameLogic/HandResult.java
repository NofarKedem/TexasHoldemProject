package GameLogic;

import java.util.List;

public class HandResult {
    private boolean isHandEnd;
    private boolean isAllHandsEnd;
    List<WinnersDetails> winnersDetails;

    public HandResult(boolean isHandEnd,boolean isAllHandsEnd, List winnersDetails){
        this.isHandEnd = isHandEnd;
        this.winnersDetails = winnersDetails;
        this.isAllHandsEnd = isAllHandsEnd;
    }

    public boolean isHandEnd() {
        return isHandEnd;
    }

    public List<WinnersDetails> getWinnersDetails() {
        return winnersDetails;
    }
}
