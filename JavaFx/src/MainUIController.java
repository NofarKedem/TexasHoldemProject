import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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
    @FXML private WinnerInfoController winnerInfoController;

    Server server = new Server();
    int numOfCurrHand=2;
    int numOfCurrRound=0;
    boolean isEndHand=false;
    Map<Integer, String> WinnerMap;
    Scene scene;

    public boolean getIsEndHand() {
        return isEndHand;
    }

    public void setScene(Scene scene)
    {
        this.scene = scene;
    }


    public void StartHand()
    {
        isEndHand = false;
        int cashBoxReminder = 0;
        server.initHandReplay();
        server.initializeHand();
        server.setPlayHand();
        server.cardDistribusionToPlayer();
        server.initRound();
        server.blindBetSmall();
        server.blindBetBig();
        //gameDetailsController.setDisableToMoveButton();
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
        gameDetailsController.disableHandFinishButton(true);
        playerBoardController.unExposeAllPlayers();
        playerBoardController.hideAllCommunityCard();
        loadFileController.disableGameButton(true);
        loadFileController.resetStatusGameLabel();
        updateAllBoard();

        ifCompPlayerIsPlaying();
    }

    public void ifCompPlayerIsPlaying() //אם זה שחקן ממוחשב הוא משחק
    {
        gameDetailsController.setDisableToMoveButton();
        if (server.getTypeOfPlayer(Utils.numOfPlayers) == 'C') {
            if(checkStatus(server.playWithComputer())) {
                ifCompPlayerIsPlaying();
            }

            updateAllBoard();
        }



    }

    public boolean checkStatus(Utils.RoundResult resultOfMove)
    {
        //for test
            System.out.print("last move was: " + server.getLastMove());
            if (server.getLastMove() == "RAISE")
                System.out.println(" with amount: " + server.getLastGenerateAmount());
            else
                System.out.println();
        //for test



        server.addSnapShotToReplay();
        boolean successes = false;

            if (resultOfMove == Utils.RoundResult.CLOSEROUND) {
                if(numOfCurrRound==4) {
                    //endHand(resultOfMove);
                    resultOfMove = Utils.RoundResult.ENDGAME;
                }

                else {
                    cardDistribusionInRound();
                    server.initRound();
                    numOfCurrRound++;
                    successes = true;
                }
            }  if (resultOfMove == Utils.RoundResult.ENDGAME) {
                try {
                    WinnerMap = server.WhoIsTheWinner();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                endHand(resultOfMove);
            }
            else if (resultOfMove == Utils.RoundResult.HUMANFOLD) {
                //System.out.println("Human fold from choice or he didn't has chips, therefor the computer player has Technical victory");
                WinnerMap = server.setTechniqWinners(Utils.RoundResult.HUMANFOLD);
                endHand(resultOfMove);
            }
            else if (resultOfMove == Utils.RoundResult.ALLFOLDED) {
                // System.out.println("All the three computer player were quit, therefor the human player has Technical victory");
                WinnerMap = server.setTechniqWinners(Utils.RoundResult.ALLFOLDED);
                endHand(resultOfMove);
            }
            else
                successes = true;


        return successes;
    }

    public void endHand(Utils.RoundResult winResult)
    {
        //endGame
        isEndHand = true;
        showWinnerPopUp(winResult);
        playerBoardController.exposeAllPlayers();
        gameDetailsController.disableHandFinishButton(false);
        gameDetailsController.disablePlayerMove();
        numOfCurrRound=0;
        server.closeTheHand();
        playerBoardController.stopFrameSparking();
        if(numOfCurrHand == server.getNumberOfHands())
        {
            //end game
            loadFileController.setStatusGameLabelToEndGame();
            gameDetailsController.disableHandFinishButton(true);
            loadFileController.disableGameButton(false);
        }
    }

    public void cardDistribusionInRound()
    {
        switch (numOfCurrRound)
        {
            case 1: server.callFlop();
                break;
            case 2: server.callTurn();
                break;
            case 3: server.callRiver();
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


    public void updateAllBoard()
    {
        playerBoardController.updateAllBoard();
    }



    @FXML
    private void showWinnerPopUp(Utils.RoundResult winResult){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL winnerPopUpFXML = getClass().getResource("WinnerInfo.fxml");
            loader.setLocation(winnerPopUpFXML);
            AnchorPane root1 = loader.load();
            WinnerInfoController winnerInfoController = loader.getController();
            Stage popUpStage = new Stage();
            winnerInfoController.setPrimaryStage(popUpStage);
            winnerInfoController.setFather(this);
            winnerInfoController.SetServer(server);
            winnerInfoController.setWinnerDetails(winResult);
            popUpStage.setTitle("Hand Winner");
            popUpStage.setScene(new Scene(root1));
            popUpStage.showAndWait();
        }catch (Exception ex){

        }
    }

    public void exposeCurrentCardOfHumanPlayer()
    {
        playerBoardController.exposeCard(server.getCurrPlayer());
    }
    public void unExposeCurrentCardOfHumanPlayer()
    {
        playerBoardController.unExposeCard(server.getCurrPlayer());
    }

    public void changeSkinOption1()
    {
        scene.getStylesheets().add(getClass().getResource("SkinOption1.css").toExternalForm());
    }

    public void changeSkinOption2()
    {
        scene.getStylesheets().add(getClass().getResource("SkinOption2.css").toExternalForm());
    }
    public void reset()
    {
        numOfCurrHand=0;
        numOfCurrRound=0;
    }


}
