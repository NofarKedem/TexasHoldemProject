public class Player {
    private char type;
    private String state;
    private int chips;
    private int buys;
    private int handsWon;
    private Card[] cards;
    private boolean isQuit;
    private int bet;
    public PlayerActionService actionService;


    public Player(char type,String state,int chips,int buys){
        this.type = type;
        this.state = state;
        this.chips = chips;
        this.buys = buys;
        this.bet = 0;
        isQuit = false;
    }
    void setPlayerHandCards(Card[] cards)
    {
        this.cards = cards;
    }
    //void gameMove(Round.GameMoves move, Round roundRef);
    //boolean gameMove(Round roundRef);

    void fold()
    {
        isQuit = true;
    }


    void Bet(int amount) {
        actionService.turnBetOn();
        chips = chips - amount;
        actionService.updateCashBox(amount);
        actionService.updateCurrBet(amount);
        bet = amount;

    }
    void call()
    {
        int currBet = actionService.getCurrBet();
        int remainder = currBet - bet;
        chips = chips - remainder;
        actionService.updateCashBox(remainder);
        bet = currBet;

    }
    void check()
    {

    }
    void Raise(int amount)
    {
        int RaiceAmount = bet + amount;
        chips = chips - RaiceAmount;
        actionService.updateCashBox(RaiceAmount);
        actionService.updateCurrBet(RaiceAmount);
        bet = RaiceAmount;

    }
    void updateState(String state) {
        this.state = state;
    }
    String getState() {
        return this.state;
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
    public void setBuys(int numOfBuys) {
        this.buys = numOfBuys;
        this.chips += 100; //צריך להוסיף לפי הקובץ XML
    }
    public int getHandsWon()
    {
        return this.handsWon;
    }
    public Card[] getCard()
    {
        return this.cards;
    }

    boolean isQuit() {return this.isQuit;}

    void initActionService(Round round)
    {
        this.actionService = round;
    }

    int getBet()
    {
        return this.bet;
    }
}
