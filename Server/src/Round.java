import java.util.List;

public class Round implements PlayerActionService{


    public enum GameMoves{
        FOLD("F"), BET("B"), CALL("C"), CHECK("K"), RAISE("R"), NONE("N");

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
    private GameMoves lastMove;

    Round(List<Player> playersRef, int roundCashBox){
        this.playersRef = playersRef;
        for(Player player : playersRef){
            player.initActionService(this);
        }
        this.roundCashBox = roundCashBox;
        isBetOn = false;
        currBet = 0;
        lastMove = GameMoves.NONE;
        this.findSmallBigIndex();
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

    public GameMoves getLastMove(){
        return lastMove;
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
    public int getCurrPlayer(){
        return currPlayerIndex;
    }

    public void blindBet(){
      //  this.findSmallBigIndex();
        playersRef.get(smallIdx).Bet(5);  //the game move is the blind small!
        playersRef.get(bigIdx).Bet(10); //the game move is the blind big!
        closeTheRound = bigIdx;
        lastMove = GameMoves.BET;
    }

    private int nextTurn(int lastToPlayIndex){
            int theNextPlayerInArray = (lastToPlayIndex + 1)%(playersRef.size());
            while (playersRef.get(theNextPlayerInArray).isQuit()){
                theNextPlayerInArray = (theNextPlayerInArray + 1)%(playersRef.size());
            }
        return  theNextPlayerInArray;
    }

    public Utils.RoundResult gameMove(Round.GameMoves gameMove, int amount){
        lastMove = gameMove;
        switch (gameMove){
            case RAISE:
                playersRef.get(currPlayerIndex).Raise(amount);
                closeTheRound = currPlayerIndex;
                break;
            case CALL:
                playersRef.get(currPlayerIndex).call();
                break;
            case BET:
                playersRef.get(currPlayerIndex).Bet(amount);
                closeTheRound = currPlayerIndex;
                break;
            case CHECK:
                playersRef.get(currPlayerIndex).check();
                break;
            case FOLD:
                playersRef.get(currPlayerIndex).fold();
                break;
            default:
                //error
                break;
        }

        Utils.RoundResult gameStatus = GameStatus();
        currPlayerIndex = nextTurn(currPlayerIndex);

        return gameStatus;
    }

    private Utils.RoundResult GameStatus(){
        int numOfQuitPlayers = 0;
        if(playersRef.get(currPlayerIndex).getType() == 'H' && playersRef.get(currPlayerIndex).isQuit()){
            return Utils.RoundResult.ENDGAME;
        }
        for(Player player : playersRef){
            if(player.isQuit()){
                numOfQuitPlayers++;
            }
        }
        //If all players but 1 quit OR the current player played 'All In' - End the game 4
        if(numOfQuitPlayers >= playersRef.size()-1 || playersRef.get(currPlayerIndex).getChips() == 0){
            return Utils.RoundResult.ENDGAME;
        }
        if(nextTurn(currPlayerIndex) == closeTheRound){
            return Utils.RoundResult.CLOSEROUND;
        }
        else return Utils.RoundResult.NOTHINGHAPPEN;
    }

    public boolean isValidGameMove(Round.GameMoves gameMoves) {
        if(gameMoves == GameMoves.FOLD){
            return true;
        }
        else if(gameMoves == GameMoves.CALL && isBetOn){
            return true;
        }
        else if(gameMoves == GameMoves.RAISE && isBetOn){
            return true;
        }
        else if(gameMoves == GameMoves.BET && !isBetOn){
            return true;
        }
        else if(gameMoves == GameMoves.CHECK && !isBetOn){
            return true;
        }
        else{return false;}
    }

    public boolean isValidAmount(int amount){
        if(amount <= playersRef.get(currPlayerIndex).getChips() && amount <= roundCashBox && amount >= currBet){
            return true;
        }
        else{
            return false;
        }
    }

    public int getLastBetOfTheCurrPlayer()
    {
        return playersRef.get(currPlayerIndex).getBet();
    }

}
