import java.util.List;

public class TableInfo {
    private int cashBox;
    private int pot;
    private List<Card> communityCrads;

    TableInfo(int cashBox,int pot,List<Card> communityCrads){
        this.cashBox = cashBox;
        this.pot = pot;
        this.communityCrads = communityCrads;
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

    public void updateData(int cashBox,int pot){
        this.cashBox = cashBox;
        this.pot = pot;
    }
}
