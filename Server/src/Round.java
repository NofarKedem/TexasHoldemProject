import java.util.List;

public class Round implements PlayerActionService{


    public static enum GameMoves{
        FOLD("F"), BET("B"), CALL("C"), CHECK("K"), RAISE("R");

        private String value;
        GameMoves(String str){this.value = str;}
        public String toString(){return value;}
    }
    private int roundNum;
    private List<Player> playersRef;
    private boolean isBetOn;
    private int currBet;
    private int closeTheRound; //who is the player that closes the round
    private int smallIdx;
    private int bigIdx;
    private int roundCashBox;

    Round(List<Player> playersRef, int roundNum, int roundCashBox){
        this.roundNum = roundNum;
        this.playersRef = playersRef;
        for(Player player : playersRef){
            player.initActionService(this);
        }
        this.roundCashBox = roundCashBox;
        isBetOn = false;
        currBet = 0;
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

    public void startRound(){
        this.findSmallBigIndex();
        //run over the players starting the first one in the curr round and get the game moves
        if(roundNum == 1){
            startBlindRound();
        }
        else{
            startRegularRound();
        }
    }

    private void startBlindRound(){
        boolean result;
        closeTheRound = bigIdx + 1; //the big closes the round and the one after him stops the loop
        do {result = playersRef.get(smallIdx).Bet(5);}while(!result);  //the game move is the blind small!
        do{ result = playersRef.get(bigIdx).Bet(10);}while (!result); //the game move is the blind big!
        int currPlayerIndex = nextTurn(bigIdx);
        while(currPlayerIndex != closeTheRound){
            boolean needUpdateRoundClose = gameMove(playersRef.get(currPlayerIndex));
            if(needUpdateRoundClose){
                closeTheRound = currPlayerIndex;
            }
            currPlayerIndex = nextTurn(currPlayerIndex);
        }

    }

    private void startRegularRound(){
        boolean result;
        closeTheRound = smallIdx;
        result = gameMove(playersRef.get(smallIdx)); //small starts the round
        if(result){ }//raise an error and start again? need to make sure that not raice and not call here
        int currPlayerIndex = nextTurn(smallIdx);
        while(currPlayerIndex != closeTheRound){
            boolean needUpdateRoundClose = gameMove(playersRef.get(currPlayerIndex));
            if(needUpdateRoundClose){
                closeTheRound = currPlayerIndex;
            }
            currPlayerIndex = nextTurn(currPlayerIndex);
        }
    }
    private int nextTurn(int lastToPlayIndex){
            int theNextPlayerInArray = (lastToPlayIndex + 1)%(playersRef.size());
            while (playersRef.get(theNextPlayerInArray).isQuit()){
                theNextPlayerInArray = (theNextPlayerInArray + 1)%(playersRef.size());
            }
        return  theNextPlayerInArray;
    }

    public boolean gameMove(Player currPlayer){
        Round.GameMoves playerChoise = GameMoves.CALL; //for test needs
        int amount = 6;
        boolean legalAction;
       // playerChoise = getPlayerChoise(); //need to merge with Hadar
        switch (playerChoise){
            case RAISE:
                //get amount from user - need to merge with Hadar
                do{legalAction = currPlayer.Raise(amount);}while (!legalAction);
                return true;
            case CALL:
                do {legalAction = currPlayer.call();}while (!legalAction);
                return false;
            case BET:
                //get amount from user - need to merge with Hadar
                do{legalAction = currPlayer.Bet(amount);} while (!legalAction);
                return true;
            case CHECK:
                do{legalAction = currPlayer.check();}while (!legalAction);
                return false;
            case FOLD:
                currPlayer.fold();
                return false;
            default:
                //error
                return false;
        }
    }
}
