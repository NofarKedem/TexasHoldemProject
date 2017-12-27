public class PlayerInfo {
    private int playerIndex;
    private char typeOfPlayer;
    private String playerState;
    private int playerChips;
    private int playerBuys;
    private int playerHandsWon;
    //private int playerCurrBet;
    private String playerCards;

    PlayerInfo(char typeOfPlayer,String playerState,int playerChips, int playerBuys,
               int playerHandsWon, int playerIndex){
        this.typeOfPlayer = typeOfPlayer;
        this.playerState = playerState;
        this.playerChips = playerChips;
        this.playerBuys = playerBuys;
        this.playerHandsWon = playerHandsWon;
        this.playerIndex = playerIndex;
        //this.playerCurrBet = playerCurrBet;
        //this.playerCards = playerCards;
    }

    public int getNumOfPlayer(){
        return this.playerIndex;
    }
    public char getTypeOfPlayer(){
        return this.typeOfPlayer;
    }
    public String getPlayerState(){
        return this.playerState;
    }
    public int getPlayerChips(){
        return this.playerChips;
    }
    public int getPlayerBuys(){
        return this.playerBuys;
    }
    public int getPlayerHandsWon(){
        return this.playerHandsWon;
    }

//    public int getPlayerCurrBet(){
//        return this.playerCurrBet;
//    }
    public String getPlayerCards(){
        return this.playerCards;
    }

}
