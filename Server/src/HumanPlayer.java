
public class HumanPlayer implements Player{

    private char type;
    private String state;
    private int chips;
    private int buys;
    private int handsWon;
    private Card[] cards;
    private boolean isQuit;
    private int bet;
    public PlayerActionService actionService;

    public HumanPlayer(char type,String state,int chips,int buys){
        this.type = type;
        this.state = state;
        this.chips = chips;
        this.buys = buys;
        this.bet = 0;
        isQuit = false;
    }

    @Override
    public void getPlayerHandCards(Card[] cards) {
        this.cards = cards;
    }

    public void fold(){
        isQuit = true;
    }

    /**
     * if the action is legal and performed, return true
     * @param amount
     * @return
     */
    @Override
    public boolean Bet(int amount) {
        if(amount <= chips && amount <= actionService.getCashBox()){
            actionService.turnBetOn();
            chips = chips - amount;
            actionService.updateCashBox(amount);
            actionService.updateCurrBet(amount);
            bet = amount;
            return true;
        }
        return false;
    }

    public boolean call(){
        int currBet = actionService.getCurrBet();
        //int remainder = currBet - bet;
        if(actionService.isBetOn() && currBet <= chips && currBet <= actionService.getCashBox()){
            chips = chips - currBet;
            actionService.updateCashBox(currBet);
            return true;
        }
        return false;
    }

    /**
     * If the Bet is on it means that the bet in the round is already started and
     * check action canot be performed, otherwise there is no problem to check an the turn moves on.
     * @return
     */
    public boolean check(){
        if(actionService.isBetOn()){
            return false;
        }
        return true;
    }

    public boolean Raise(int amount){
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

    @Override
    public void updateState(String state) {
        this.state = state;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public boolean isQuit() {
        return this.isQuit;
    }

    @Override
    public void initActionService(Round round) {
        this.actionService = round;
    }
}
