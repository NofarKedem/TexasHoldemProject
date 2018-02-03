package GameLogic;

import GameLogic.PlayerInfo;

import java.util.List;

public class StatusSnapShot {
    private List<PlayerInfo> playerStatus;
    private TableInfo tableStatus;
    private int currPlayer;
    private String lastGameMove;
    private int lastBetAmount;
    private int currHand;
    private int currRound;

    StatusSnapShot(List<PlayerInfo> playerStatus, TableInfo tableStatus, String lastGameMove, int lastBetAmount, int currPlayer,
                   int currHand, int currRound){
        this.playerStatus = playerStatus;
        this.tableStatus = tableStatus;
        this.lastGameMove = lastGameMove;
        this.lastBetAmount = lastBetAmount;
        this.currPlayer = currPlayer;
        this.currHand = currHand;
        this.currRound = currRound;
    }

    public List<PlayerInfo> getPlayerStatus(){
        return this.playerStatus;
    }

    public TableInfo getTableStatus(){
        return this.tableStatus;
    }

    public int getCurrPlayer(){return this.currPlayer;}

    public String getLastGameMove(){
        return lastGameMove;
    }

    public int getLastBetAmount(){
        return lastBetAmount;
    }

    public int getCurrHand() {
        return currHand;
    }

    public int getCurrRound() {
        return currRound;
    }
}
