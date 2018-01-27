import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainUIController implements Initializable {
    @FXML private PlayerTableController playerTableController;
    @FXML private GameDetailsController gameDetailsController;
    @FXML private LoadFileController loadFileController;
    @FXML private PlayerBoardController playerBoardController;
    @FXML private WinnerInfoController winnerInfoController;

    GameEngine gameEngine = new GameEngine();
    int numOfCurrHand=0;
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
        gameEngine.initHandReplay();
        gameEngine.initializeHand();
        gameEngine.setPlayHand();
        gameEngine.cardDistribusionToPlayer();
        gameEngine.initRound();
        gameEngine.blindBetSmall();
        gameEngine.blindBetBig();
        numOfCurrRound++;
        numOfCurrHand++;
        gameDetailsController.disableHandFinishButton(true);
        playerBoardController.unExposeAllPlayers();
        playerBoardController.hideAllCommunityCard();
        loadFileController.disableGameButton(true);
        loadFileController.setStatus("");
        updateAllBoard();

        ifCompPlayerIsPlaying();
    }

    public void ifCompPlayerIsPlaying() //אם זה שחקן ממוחשב הוא משחק
    {
        gameDetailsController.setDisableToMoveButton();
        int numOfPlayer = gameEngine.getCurrPlayer();
        if (gameEngine.getTypeOfPlayer(Utils.numOfPlayers) == 'C') {
            if(checkStatus(gameEngine.playWithComputer(),numOfPlayer)) {
                ifCompPlayerIsPlaying();
            }
            updateAllBoard();
        }
    }

    public boolean checkStatus(Utils.RoundResult resultOfMove, int numOfPlayer)
    {
        displayFoldLabelToPlayer(numOfPlayer);

        gameEngine.addSnapShotToReplay();
        boolean successes = false;

            if (resultOfMove == Utils.RoundResult.CLOSEROUND) {
                if(numOfCurrRound==4) {
                    //endHand(resultOfMove);
                    resultOfMove = Utils.RoundResult.ENDGAME;
                }

                else {
                    cardDistribusionInRound();
                    gameEngine.initRound();
                    numOfCurrRound++;
                    successes = true;
                }
            }  if (resultOfMove == Utils.RoundResult.ENDGAME) {
                try {
                    WinnerMap = gameEngine.WhoIsTheWinner();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                endHand(resultOfMove);
            }
            else if (resultOfMove == Utils.RoundResult.HUMANFOLD) {
                WinnerMap = gameEngine.setTechniqWinners(Utils.RoundResult.HUMANFOLD);
                endHand(resultOfMove);
            }
            else if (resultOfMove == Utils.RoundResult.ALLFOLDED) {
                WinnerMap = gameEngine.setTechniqWinners(Utils.RoundResult.ALLFOLDED);
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
        gameEngine.closeTheHand();
        playerBoardController.stopFrameSparking();
        playerBoardController.hideGameMoves();
        validNextHand();
    }

    public void validNextHand()
    {
        if(numOfCurrHand == gameEngine.getNumberOfHands())
        {
            loadFileController.setStatus("Game was over, you can start new game or restart the current game");

        }
        else if(!gameEngine.isThereAreHumanPlayer())
        {
            loadFileController.setStatus("Game was over, there are no human player");
        }

        else if(!gameEngine.isThereAreMoreThenOnePlayer())
        {
            loadFileController.setStatus("Game was over, there are only one player");
        }
        else
            return;

        gameDetailsController.disableHandFinishButton(true);
        loadFileController.disableGameButton(false);
    }

    public void cardDistribusionInRound()
    {
        switch (numOfCurrRound)
        {
            case 1: gameEngine.callFlop();
                break;
            case 2: gameEngine.callTurn();
                break;
            case 3: gameEngine.callRiver();
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

        playerTableController.SetServer(gameEngine);
        gameDetailsController.SetServer(gameEngine);
        loadFileController.SetServer(gameEngine);
        playerBoardController.SetServer(gameEngine);
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

        List<PlayerInfo> playerList = gameEngine.getAllPlayerInfo();
        for(PlayerInfo p: playerList)
            pokerPlayers.add(p);
        Collections.sort(pokerPlayers, (PlayerInfo p1, PlayerInfo p2) -> (p2.getPlayerChips() - p1.getPlayerChips()));
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
            winnerInfoController.SetServer(gameEngine);
            winnerInfoController.setWinnerDetails(winResult);
            popUpStage.setTitle("Hand Winner");
            popUpStage.setScene(new Scene(root1));
            popUpStage.showAndWait();
        }catch (Exception ex){

        }
    }

    public void exposeCurrentCardOfHumanPlayer()
    {
        playerBoardController.exposeCard(gameEngine.getCurrPlayer());
    }
    public void unExposeCurrentCardOfHumanPlayer()
    {
        playerBoardController.unExposeCard(gameEngine.getCurrPlayer());
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


    public void updateAllPlayersFromReplayList(int replayListIter) {
        playerBoardController.displayPlayerDetailsOnTheBoardFromReplayList(replayListIter);
        playerBoardController.exposeAllPlayersCardsAccordingReplayStatus(replayListIter);
    }

    public void updateTheTableFromReplayList(int replayListIter) {
        playerBoardController.displayBoardStateFromReplayList(replayListIter);
    }

    public void updateTableWithWinProp() {
        updatePlayersTable();
    }

    @FXML
    public void showBuyPopUp(){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL BuyPopUpFXML = getClass().getResource("BuyPopUp.fxml");
            loader.setLocation(BuyPopUpFXML);
            AnchorPane root2 = loader.load();
            BuyPopUpController buyPopUpController = loader.getController();
            Stage popUpStage = new Stage();
            buyPopUpController.setPrimaryStage(popUpStage);
            buyPopUpController.setFather(this);
            buyPopUpController.SetServer(gameEngine);
            buyPopUpController.setAllNotVisible();
            buyPopUpController.setPlayersDetails();

            popUpStage.setTitle("Buy Chips");
            popUpStage.setScene(new Scene(root2));
            popUpStage.showAndWait();
        }catch (Exception ex){

        }
    }


    public void statusPlayerNotHasEnoughChips()
    {
        loadFileController.statusPlayerNotHasEnoughChips();
    }


    public void hideGameMoves() {
        playerBoardController.hideGameMoves();
    }

    public void clearTable() {
        playerTableController.clearTable();
    }

    public void displayFoldLabelToPlayer(int numOfPlayer)
    {
        if(gameEngine.getLastMove().equals("FOLD"))
            playerBoardController.displayFoldLabelToPlayer(numOfPlayer);
    }
}
