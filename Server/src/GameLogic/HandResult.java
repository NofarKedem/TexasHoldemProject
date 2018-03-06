package GameLogic;

import java.util.List;

public class HandResult {
    boolean isHandEnd;
    List<WinnersDetails> winnersDetails;

    public HandResult(boolean isHandEnd, List winnersDetails){
        this.isHandEnd = isHandEnd;
        this.winnersDetails = winnersDetails;
    }

    public boolean isHandEnd() {
        return isHandEnd;
    }

    public List<WinnersDetails> getWinnersDetails() {
        return winnersDetails;
    }
}
