import java.util.List;

public class Round implements PlayerActionService{


    public enum GameMoves{
        FOLD("F"), BET("B"), CALL("C"), CHECK("K"), RAISE("R");

        private String value;
        GameMoves(String str){this.value = str;}
        public String toString(){return value;}
    }
    private List<Player> playersRef;
    private boolean isBetOn;
    private int currBet;
    private int closeTheRound; //who is the player that closes the round
    private int smallIdx;
    private int bigIdx;
    private int roundCashBox;
    private int currPlayerIndex; //the curr turn
    private boolean isValidGameMove;

    Round(List<Player> playersRef, int roundCashBox){
        this.playersRef = playersRef;
        for(Player player : playersRef){
            player.initActionService(this);
        }
        this.roundCashBox = roundCashBox;
        isBetOn = false;
        currBet = 0;
        isValidGameMove = false;
    }

    @Override
    public int updateCashBox(int amount) {
        roundCashBox = roundCashBox + amount;
        return 0;
    }

    @Override
    public void updateCurrBet(int amount) {
        currBet = amount;
    }

    @Override
    public int getCurrBet() {
        return currBet;
    }

    private void findSmallBigIndex(){
        for (Player player:playersRef) {
            String state = player.getState();
            if(state == "S"){
                smallIdx = playersRef.indexOf(player);
            }
            if(state == "B"){
                bigIdx = playersRef.indexOf(player);
            }
        }
    }

    public int getCashBox(){
        return roundCashBox;
    }

    @Override
    public boolean isBetOn() {
        return isBetOn;
    }

    @Override
    public void turnBetOn() {
        this.isBetOn = true;
    }

    public void setCurrPlayer(){
        currPlayerIndex = (bigIdx+1)%(playersRef.size());
    }

    public void blindBet(){
        this.findSmallBigIndex();
        boolean result;
        do {result = playersRef.get(smallIdx).Bet(5);}while(!result);  //the game move is the blind small!
        do{ result = playersRef.get(bigIdx).Bet(10);}while (!result); //the game move is the blind big!
    }

    private int nextTurn(int lastToPlayIndex){
            int theNextPlayerInArray = (lastToPlayIndex + 1)%(playersRef.size());
            while (playersRef.get(theNextPlayerInArray).isQuit()){
                theNextPlayerInArray = (theNextPlayerInArray + 1)%(playersRef.size());
            }
        return  theNextPlayerInArray;
    }

    public boolean gameMove(Round.GameMoves gameMove, int amount){
        switch (gameMove){
            case RAISE:
                if(playersRef.get(currPlayerIndex).Raise(amount)){
                    isValidGameMove = true;
                }
                closeTheRound = currPlayerIndex;

            case CALL:
                if(playersRef.get(currPlayerIndex).call()){
                    isValidGameMove = true;
                }

            case BET:
                if(playersRef.get(currPlayerIndex).Bet(amount)){
                    isValidGameMove = true;
                }
                closeTheRound = currPlayerIndex;

            case CHECK:
                if(playersRef.get(currPlayerIndex).check()){
                    isValidGameMove = true;
                }

            case FOLD:
                playersRef.get(currPlayerIndex).fold();

            default:
                //error

        }

        currPlayerIndex = nextTurn(currPlayerIndex);
        if(currPlayerIndex == closeTheRound)
            return true;
        else return false;
    }

    public boolean isValidGameMove() {
        return isValidGameMove;
    }
}
