public interface Player {

    void setPlayerHandCards(Card[] cards);
    //void gameMove(Round.GameMoves move, Round roundRef);
    //boolean gameMove(Round roundRef);

    void fold();
    boolean Bet(int amount);
    boolean call();
    boolean check();
    boolean Raise(int amount);
    void updateState(String state);
    String getState();
    char getType();
    int getChips();
    int getBuys();
    int getHandsWon();
    Card[] getCard();
    boolean isQuit();

    void initActionService(Round round);
}
