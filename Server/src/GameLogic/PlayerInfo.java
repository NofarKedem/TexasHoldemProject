package GameLogic;

public class PlayerInfo {
    private String PlayerName;
    private int PlayerId;
    private int playerIndex;
    private char typeOfPlayer;
    private String playerState;
    private int playerChips;
    private int playerBuys;
    private int playerHandsWon;
    //private Card[] playerCards;
    private boolean isQuit;

    public PlayerInfo(char typeOfPlayer, String playerState, int playerChips, int playerBuys,
               int playerHandsWon, int playerIndex, String PlayerName, int PlayerId, boolean isQuit){
            //, Card[] playerCards){
        this.typeOfPlayer = typeOfPlayer;
        this.playerState = playerState;
        this.playerChips = playerChips;
        this.playerBuys = playerBuys;
        this.playerHandsWon = playerHandsWon;
        this.playerIndex = playerIndex;
        this.PlayerName = PlayerName;
        this.PlayerId = PlayerId;
        this.isQuit = isQuit;
       // this.playerCards = playerCards;
    }

    public String getName()
    {
        return PlayerName;
    }

    public int getId()
    {
        return PlayerId;
    }
    public char getType()
    {
        return typeOfPlayer;
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

/*
    public Card[] getPlayerCards(){
        return this.playerCards;
    }*/

    public boolean getIsQuit()
    {
        return isQuit;
    }

    public void setPlayerBuys(int playerBuys) {
        this.playerBuys = playerBuys;
    }

    public void setPlayerChips(int playerChips) {
        this.playerChips = playerChips;
    }

    public void setPlayerHandsWon(int playerHandsWon) {
        this.playerHandsWon = playerHandsWon;
    }

    public void setPlayerId(int playerId) {
        this.PlayerId = playerId;
    }

    public void setPlayerName(String playerName) {
        this.PlayerName = playerName;
    }

    public void setTypeOfPlayer(char typeOfPlayer) {
        this.typeOfPlayer = typeOfPlayer;
    }

}
