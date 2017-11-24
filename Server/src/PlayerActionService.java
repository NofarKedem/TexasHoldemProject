public interface PlayerActionService {

    int updateCashBox (int amount);

    void updateCurrBet(int amount);

    int getCurrBet();

    int getCashBox();

    boolean isBetOn();

    void turnBetOn();

}
