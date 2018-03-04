package GameLogic;

public class GameMoveStatus {
    private boolean isValidAmount;
    private String error;

    public void setValidAmount(boolean isValidAmount)
    {
        this.isValidAmount = isValidAmount;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}
