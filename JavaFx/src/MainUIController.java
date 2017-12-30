import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainUIController implements Initializable {
    @FXML private PlayerTableController playerTableController;
    @FXML private GameDetailsController gameDetailsController;
    @FXML private LoadFileController loadFileController;
    @FXML private PlayerBoardController playerBoardController;

    Server server = new Server();
    int numOfCurrHand=0;
    int numOfCurrRound=0;
    Map<Integer, String> WinnerMap;


    public void StartHand()
    {
        gameDetailsController.setButtonNextHandDisable(true);
        if(numOfCurrHand+1 == server.getNumberOfHands())
        {
            //end game
        }
        server.initHandReplay();
        server.initializeHand();
        server.setPlayHand();
        server.cardDistribusionToPlayer();
        server.initRound();
        cardDistribusionInRound();
        server.blindBetSmall();
        server.blindBetBig();
        /*
        if(server.blindBetSmall() || server.blindBetBig())
        {

        }
        else
        {
            System.out.println("The current blind bet player\\s do not have enough chips for bet!");
        }
        */
        numOfCurrRound++;
        numOfCurrHand++;
        displayPlayerOnBoard();
        displayCurrBetAndCashBoxOnBoard();
        ifCompPlayerIsPlaying();
    }

    public void ifCompPlayerIsPlaying() //אם זה שחקן ממוחשב הוא משחק
    {

        if (server.getTypeOfPlayer(Utils.numOfPlayers) == 'C') {
            checkStatus(server.playWithComputer());
            displayCurrBetAndCashBoxOnBoard();
            ifCompPlayerIsPlaying();
        }

    }

    public boolean checkStatus(Utils.RoundResult resultOfMove)
    {
        boolean successes = false;

            if (resultOfMove == Utils.RoundResult.CLOSEROUND) {
                if(numOfCurrRound==3)
                    endHand();

                else {
                    cardDistribusionInRound();
                    server.initRound();
                    numOfCurrRound++;
                }
            } else if (resultOfMove == Utils.RoundResult.ENDGAME) {
                endHand();
            }
            else if (resultOfMove == Utils.RoundResult.HUMANFOLD) {
                //System.out.println("Human fold from choice or he didn't has chips, therefor the computer player has Technical victory");
                WinnerMap = server.setTechniqWinners(Utils.RoundResult.HUMANFOLD);
                endHand();
            }
            else if (resultOfMove == Utils.RoundResult.ALLCOMPUTERFOLD) {
                // System.out.println("All the three computer player were quit, therefor the human player has Technical victory");
                WinnerMap = server.setTechniqWinners(Utils.RoundResult.ALLCOMPUTERFOLD);
                endHand();
            }
            else
                successes = true;


        return successes;
    }

    public void endHand()
    {
        //endGame
        try {
            WinnerMap = server.WhoIsTheWinner();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        gameDetailsController.setButtonNextHandDisable(true);
        numOfCurrRound=0;
    }

    public void cardDistribusionInRound()
    {
        switch (numOfCurrRound)
        {
            case 0: server.callFlop();
                break;
            case 1: server.callTurn();
                break;
            case 2: server.callRiver();
                break;
            default: break;
        }
        playerBoardController.displayCommunityCards(numOfCurrRound);
    }

    public void setFatherToConroller()
    {
        playerTableController.setFather(this);
        gameDetailsController.setFather(this);
        loadFileController.setFather(this);
        playerBoardController.setFather(this);
    }

    public void setServerToContoroller()
    {

        playerTableController.SetServer(server);
        gameDetailsController.SetServer(server);
        loadFileController.SetServer(server);
        playerBoardController.SetServer(server);
    }
    public void setPrimaryStage(Stage primaryStage)
    {
        loadFileController.setPrimaryStage(primaryStage);
    }

    public void updateXMLDetails()
    {

        updatePlayersTable();
        gameDetailsController.displayGameDetails();
    }

    public void updatePlayersTable()
    {
        ObservableList<PlayerInfo> pokerPlayers = FXCollections.observableArrayList();

        List<PlayerInfo> playerList = server.getAllPlayerInfo();
        for(PlayerInfo p: playerList)
            pokerPlayers.add(p);
        //pokerPlayers.add(po);
        playerTableController.displayTable(pokerPlayers);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void displayPlayerOnBoard()
    {
        playerBoardController.displayPlayerDetailsOnTheBoard();

    }

    public void displayCurrBetAndCashBoxOnBoard() {
        playerBoardController.displayCurrBetAndCashBoxOnBoard();
    }
}
