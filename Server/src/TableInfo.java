import java.util.List;

public class TableInfo {
    private int cashBox;
    private int pot;
    private List<Card> communityCrads;
    private int currHand;
    private int currRound;


    TableInfo(int cashBox,int pot,List<Card> communityCrads,int currHand,int currRound){
        this.cashBox = cashBox;
        this.pot = pot;
        this.communityCrads = communityCrads;
        this.currHand = currHand;
        this.currRound = currRound;
    }

    public int getCashBox() {
        return cashBox;
    }

    public int getPot() {
        return pot;
    }

    public List<Card> getCommunityCards() {
        return communityCrads;
    }

    public int getCurrHand() {
        return currHand;
    }

    public int getCurrRound() {
        return currRound;
    }

    public void updateData(int cashBox, int pot){
        this.cashBox = cashBox;
        this.pot = pot;
    }
}
