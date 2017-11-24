public interface Player {

    void getPlayerHandCards(Card[] cards);
    //void gameMove(Round.GameMoves move, Round roundRef);
    //boolean gameMove(Round roundRef);

    void fold();
    boolean Bet(int amount);
    boolean call();
    boolean check();
    boolean Raise(int amount);
    void updateState(String state);
    String getState();
    boolean isQuit();

    void initActionService(Round round);
}
