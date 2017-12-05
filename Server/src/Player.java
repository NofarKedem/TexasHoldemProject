public class Player {
    private char type;
    private String state;
    private int chips;
    private int buys;
    private int handsWon;
    private Card[] cards;
    private boolean isQuit;
    private int bet;
    private int numOfPlayer=0;
    public PlayerActionService actionService;


    public Player(char type,String state,int chips,int buys, int numOfPlayer){
        this.type = type;
        this.state = state;
        this.chips = chips;
        this.buys = buys;
        this.bet = 0;
        isQuit = false;
        handsWon = 0;
        this.numOfPlayer = numOfPlayer;
    }
    public void setPlayerHandCards(Card[] cards)
    {
        this.cards = cards;
    }


    public void fold()
    {
        isQuit = true;
    }


    public void Bet(int amount) {
        actionService.turnBetOn();
        chips = chips - amount;
        actionService.updateCashBox(amount);
        actionService.updateCurrBet(amount);
        bet = amount;

    }
    public void call()
    {
        int currBet = actionService.getCurrBet();
        int remainder = currBet - bet;
        chips = chips - remainder;
        actionService.updateCashBox(remainder);
        bet = currBet;

    }
    public void check()
    {

    }
    public void Raise(int amount)
    {
        int currBet = actionService.getCurrBet();
        int RaiceAmount = (currBet + amount) - bet;
        chips = chips - RaiceAmount;
        actionService.updateCashBox(RaiceAmount);
        actionService.updateCurrBet(RaiceAmount);
        bet = RaiceAmount;

    }

    public void updateState(String state) {
        this.state = state;
    }
    public void resetState(){this.state = "";}
    public void updateHandsWon(){
        handsWon++;
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

    public void setBuysAndChips(int numOfChipsPerBuy) {
        this.buys++;
        this.chips += numOfChipsPerBuy;
    }
    public int getHandsWon()
    {
        return this.handsWon;
    }
    public Card[] getCard()
    {
        return this.cards;
    }

    public boolean getQuit() {return this.isQuit;}

    public void initActionService(Round round)
    {
        this.actionService = round;
    }

    public int getBet()
    {
        return this.bet;
    }
    public void initBet()
    {
        bet =0;
    }

    public void updateWinnerChips(int cashBox) {
        chips = chips + cashBox;
    }
    public int getNumOfPlayer()
    {
        return numOfPlayer;
    }
}
