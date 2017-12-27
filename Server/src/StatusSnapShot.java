public class StatusSnapShot {
    private PlayerInfo playerStatus;
    private TableInfo tableStatus;
    private String lastGameMove;
    private int lastBetAmount; //the amount of the last player bet (according to the player in playerStatus)

    StatusSnapShot(PlayerInfo playerStatus, TableInfo tableStatus, String lastGameMove,int lastBetAmount){
        this.playerStatus = playerStatus;
        this.tableStatus = tableStatus;
        this.lastGameMove = lastGameMove;
        this.lastBetAmount = lastBetAmount;
    }

    public PlayerInfo getPlayerStatus(){
        return this.playerStatus;
    }

    public TableInfo getTableStatus(){
        return this.tableStatus;
    }

    public String getLastGameMove(){
        return lastGameMove;
    }

    public int getLastBetAmount(){
        return lastBetAmount;
    }
}
