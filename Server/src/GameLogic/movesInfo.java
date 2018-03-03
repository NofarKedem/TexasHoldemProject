package GameLogic;

public class movesInfo {
    private boolean ifMyTurn;
    private boolean calll; //3 L
    private boolean fold;
    private boolean bet;
    private boolean check;
    private boolean raise;

    public movesInfo( boolean fold, boolean bet, boolean call, boolean check, boolean raise)
    {
        this.calll = call;
        this.fold = fold;
        this.bet = bet;
        this.check = check;
        this.raise = raise;
    }

    public void setIfMyTurn(boolean ifMyTurn) {
        this.ifMyTurn = ifMyTurn;
    }
    public boolean getIfMyTurn()
    {
        return ifMyTurn;
    }
    public void setAllFalse()
    {
        this.calll = false;
        this.fold = false;
        this.bet = false;
        this.check = false;
        this.raise = false;
    }
}
