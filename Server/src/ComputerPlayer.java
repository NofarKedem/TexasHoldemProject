public class ComputerPlayer implements Player{
    private char type;
    private char state;
    private int chips;
    private int buys;
    private int handsWon;
    private Card[] cards;
    private boolean isQuit;


    @Override
    public void setPlayerHandCards(Card[] cards) {

    }

    @Override
    public void fold() {

    }

    @Override
    public boolean Bet(int amount) {
        return true;
    }

    @Override
    public boolean call() {
        return true;
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public boolean Raise(int amount) {
        return true;
    }

    @Override
    public void updateState(String state) {

    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public boolean isQuit() {
        return false;
    }

    @Override
    public void initActionService(Round round) {

    }

    public char getType() {return this.type; }

    public int getChips()
    {
        return this.chips;
    }
    public int getBuys()
    {
        return this.buys;
    }
    public int getHandsWon()
    {
        return this.handsWon;
    }
    public Card[] getCard()
    {
        return this.cards;
    }

    public Round.GameMoves generateMove(){
        return Round.GameMoves.CALL; //just for test
    }

    public int generateAmount(){
        return 0; //just for test
    }
}
