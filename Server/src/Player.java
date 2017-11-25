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


    boolean Bet(int amount) {
        if (amount <= chips && amount <= actionService.getCashBox()) {
            actionService.turnBetOn();
            chips = chips - amount;
            actionService.updateCashBox(amount);
            actionService.updateCurrBet(amount);
            bet = amount;
            return true;
        }
        return false;
    }
    boolean call()
    {
        int currBet = actionService.getCurrBet();
        //int remainder = currBet - bet;
        if(actionService.isBetOn() && currBet <= chips && currBet <= actionService.getCashBox()){
            chips = chips - currBet;
            actionService.updateCashBox(currBet);
            return true;
        }
        return false;
    }
    boolean check()
    {
        if(actionService.isBetOn()){
            return false;
        }
        return true;
    }
    boolean Raise(int amount)
    {
        int RaiceAmount = actionService.getCurrBet() + amount;
        if(actionService.isBetOn() && RaiceAmount <= chips && RaiceAmount <= actionService.getCashBox()){
            chips = chips - RaiceAmount;
            actionService.updateCashBox(RaiceAmount);
            actionService.updateCurrBet(RaiceAmount);
            bet = RaiceAmount;
            return true;
        }
        return false;
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
}
