import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.List;


public class PlayerBoardController {
    @FXML Label nameLabel1;
    @FXML Label stateLabel1;
    @FXML Label chipsLabel1;
    @FXML Label buysLabel1;
    @FXML Label handWonLabel1;
    @FXML Label nameLabel2;
    @FXML Label stateLabel2;
    @FXML Label chipsLabel2;
    @FXML Label buysLabel2;
    @FXML Label handWonLabel2;
    @FXML Label nameLabel3;
    @FXML Label stateLabel3;
    @FXML Label chipsLabel3;
    @FXML Label buysLabel3;
    @FXML Label handWonLabel3;
    @FXML Label nameLabel4;
    @FXML Label stateLabel4;
    @FXML Label chipsLabel4;
    @FXML Label buysLabel4;
    @FXML Label handWonLabel4;
    @FXML Label nameLabel5;
    @FXML Label stateLabel5;
    @FXML Label chipsLabel5;
    @FXML Label buysLabel5;
    @FXML Label handWonLabel5;
    @FXML Label nameLabel6;
    @FXML Label stateLabel6;
    @FXML Label chipsLabel6;
    @FXML Label buysLabel6;
    @FXML Label handWonLabel6;
    @FXML GridPane gridPane1;
    @FXML GridPane gridPane2;
    @FXML GridPane gridPane3;
    @FXML GridPane gridPane4;
    @FXML GridPane gridPane5;
    @FXML GridPane gridPane6;
    @FXML ImageView CommunityCard1;
    @FXML ImageView CommunityCard2;
    @FXML ImageView CommunityCard3;
    @FXML ImageView CommunityCard4;
    @FXML ImageView CommunityCard5;
    @FXML ImageView frame1;
    @FXML ImageView frame2;
    @FXML ImageView frame3;
    @FXML ImageView frame4;
    @FXML ImageView frame5;
    @FXML ImageView frame6;
    @FXML ImageView card11;
    @FXML ImageView card12;
    @FXML ImageView card21;
    @FXML ImageView card22;
    @FXML ImageView card31;
    @FXML ImageView card32;
    @FXML ImageView card41;
    @FXML ImageView card42;
    @FXML ImageView card51;
    @FXML ImageView card52;
    @FXML ImageView card61;
    @FXML ImageView card62;
    @FXML Label currentBetLabel;
    @FXML Label totalCashBoxLabel;
    @FXML Label betLabel;
    @FXML Label cashBoxLabel;
    @FXML Label currentRoundLabel;
    @FXML Label currentHandLabel;

    //For Replay Display
    @FXML Label gameMove1;
    @FXML Label gameMove2;
    @FXML Label gameMove3;
    @FXML Label gameMove4;
    @FXML Label gameMove5;
    @FXML Label gameMove6;

    GameEngine refGameEngine;
    MainUIController mainUIFather;
    FadeTransition fadeTransition = new FadeTransition();

    @FXML
    private void initialize() {
        currentBetLabel.setId("currentBetLabel");
        totalCashBoxLabel.setId("totalCashBoxLabel");
        betLabel.setId("betLabel");
        cashBoxLabel.setId("cashBoxLabel");
        gridPane1.setVisible(false);
        gridPane2.setVisible(false);
        gridPane3.setVisible(false);
        gridPane4.setVisible(false);
        gridPane5.setVisible(false);
        gridPane6.setVisible(false);
        frame1.setVisible(false);
        frame2.setVisible(false);
        frame3.setVisible(false);
        frame4.setVisible(false);
        frame5.setVisible(false);
        frame6.setVisible(false);
        card11.setVisible(false);
        card12.setVisible(false);
        card21.setVisible(false);
        card22.setVisible(false);
        card31.setVisible(false);
        card32.setVisible(false);
        card41.setVisible(false);
        card42.setVisible(false);
        card51.setVisible(false);
        card52.setVisible(false);
        card61.setVisible(false);
        card62.setVisible(false);
        gameMove1.setVisible(false);
        gameMove2.setVisible(false);
        gameMove3.setVisible(false);
        gameMove4.setVisible(false);
        gameMove5.setVisible(false);
        gameMove6.setVisible(false);
    }

    public void setFather(MainUIController father)
    {
        mainUIFather = father;
    }
    public void SetServer(GameEngine ser)
    {
        refGameEngine = ser;
    }

    public void updateAllBoard()
    {
        currentRoundLabel.setText(Integer.toString(refGameEngine.getCurrNumOfRound()));
        currentHandLabel.setText(Integer.toString(refGameEngine.getCurrentNumberOfHand()));
        displayCurrBetAndCashBoxOnBoard();
        displayPlayerDetailsOnTheBoard();
    }

    private void displayPlayerDetailsOnTheBoard()
    {
        stopFrameSparking();

        for(int i=0;i<Utils.numOfPlayers;i++)
        {
            PlayerInfo player =  refGameEngine.getPlayerInfo(i);
            if(!player.getIsQuit()) {
                displayPlayerByIndex(player, i);
                if(!mainUIFather.getIsEndHand()){frameSpark();}
            }
            else
                visiblePlayerByIndex(player,i);
        }
    }

    public void displayPlayerDetailsOnTheBoardFromReplayList(int replayListIter)
    {
        hideGameMoves();
        stopFrameSparking();
        for(int i=0;i<Utils.numOfPlayers;i++)
        {
            PlayerInfo player =  refGameEngine.getPlayerInfoFromReplayList(i,replayListIter);

            if(!player.getIsQuit()) {
                displayPlayerByIndex(player, i);
            }
            else
                visiblePlayerByIndex(player,i);
        }
        int currTurn = refGameEngine.getCurrTurnFromReplayList(replayListIter);
        frameSparkForReplayMode(currTurn);
        displayGameMove(currTurn,replayListIter);

    }

    private void displayGameMove(int currTurn, int replayListIter) {
        String lastGameMove = refGameEngine.getLastMoveFromRelayList(replayListIter);
        /*
        if (currTurn == 0){
            currTurn = (utils.numOfPlayers - 1);
        }
        else{
            currTurn = currTurn - 1;
        }
        */
        switch (currTurn){
            case 0:
                if(lastGameMove != "NONE") {
                    gameMove1.setText(lastGameMove);
                    gameMove1.setVisible(true);
                }
                break;
            case 1:
                if(lastGameMove != "NONE") {
                    gameMove2.setText(lastGameMove);
                    gameMove2.setVisible(true);
                }
                break;
            case 2:
                if(lastGameMove != "NONE"){
                    gameMove3.setText(lastGameMove);
                    gameMove3.setVisible(true);
                }
                break;
            case 3:
                if(lastGameMove != "NONE"){
                    gameMove4.setText(lastGameMove);
                    gameMove4.setVisible(true);
                }
                break;
            case 4:
                if(lastGameMove != "NONE"){
                    gameMove5.setText(lastGameMove);
                    gameMove5.setVisible(true);
                }
                break;
            case 5:
                if(lastGameMove != "NONE"){
                    gameMove6.setText(lastGameMove);
                    gameMove6.setVisible(true);
                }
                break;
        }
    }

    public void hideGameMoves(){
        gameMove1.setVisible(false);
        gameMove2.setVisible(false);
        gameMove3.setVisible(false);
        gameMove4.setVisible(false);
        gameMove5.setVisible(false);
        gameMove6.setVisible(false);
    }

    public void displayPlayerByIndex(PlayerInfo player, int numOfPlayer)
    {
        switch(numOfPlayer)
        {
            case 0:
                changeLabelToPlayer(player,nameLabel1,stateLabel1,chipsLabel1,buysLabel1,handWonLabel1,gridPane1);
                frame1.setVisible(true);
                card11.setVisible(true);
                card12.setVisible(true);
                break;
            case 1:
                changeLabelToPlayer(player,nameLabel2,stateLabel2,chipsLabel2,buysLabel2,handWonLabel2,gridPane2);
                frame2.setVisible(true);
                card21.setVisible(true);
                card22.setVisible(true);
                break;
            case 2:
                changeLabelToPlayer(player,nameLabel3,stateLabel3,chipsLabel3,buysLabel3,handWonLabel3,gridPane3);
                frame3.setVisible(true);
                card31.setVisible(true);
                card32.setVisible(true);
                break;
            case 3:
                changeLabelToPlayer(player,nameLabel4,stateLabel4,chipsLabel4,buysLabel4,handWonLabel4,gridPane4);
                frame4.setVisible(true);
                card41.setVisible(true);
                card42.setVisible(true);
                break;
            case 4:
                changeLabelToPlayer(player,nameLabel5,stateLabel5,chipsLabel5,buysLabel5,handWonLabel5,gridPane5);
                frame5.setVisible(true);
                card51.setVisible(true);
                card52.setVisible(true);
                break;
            case 5:
                changeLabelToPlayer(player,nameLabel6,stateLabel6,chipsLabel6,buysLabel6,handWonLabel6,gridPane6);
                frame6.setVisible(true);
                card61.setVisible(true);
                card62.setVisible(true);
                break;
        }

    }

    private void visiblePlayerByIndex(PlayerInfo player, int numOfPlayer)
    {
        switch(numOfPlayer)
        {
            case 0:
                gridPane1.setVisible(false);
                frame1.setVisible(false);
                card11.setVisible(false);
                card12.setVisible(false);
                break;
            case 1:
                gridPane2.setVisible(false);
                frame2.setVisible(false);
                card21.setVisible(false);
                card22.setVisible(false);
                break;
            case 2:
                gridPane3.setVisible(false);
                frame3.setVisible(false);
                card31.setVisible(false);
                card32.setVisible(false);
                break;
            case 3:
                gridPane4.setVisible(false);
                frame4.setVisible(false);
                card41.setVisible(false);
                card42.setVisible(false);
                break;
            case 4:
                gridPane5.setVisible(false);
                frame5.setVisible(false);
                card51.setVisible(false);
                card52.setVisible(false);
                break;
            case 5:
                gridPane6.setVisible(false);
                frame6.setVisible(false);
                card61.setVisible(false);
                card62.setVisible(false);
                break;
        }
    }

    private void changeLabelToPlayer(PlayerInfo player, Label name,Label state,Label chips,Label buy, Label handsOfWon, GridPane grinPane)
    {
        name.setText(player.getName());
        //confStateTextToPlayer();
        state.setText(confStateTextToPlayer(player));
        chips.setText(Integer.toString(player.getPlayerChips()));
        buy.setText(Integer.toString(player.getPlayerBuys()));
        handsOfWon.setText(Integer.toString(player.getPlayerHandsWon()));
        grinPane.setVisible(true);
    }
    private String confStateTextToPlayer(PlayerInfo player)
    {
        String state = player.getPlayerState();
        if(state == "S")
            return state + " - "+ refGameEngine.getNumOfChipsForsmall();
        else if(state == "B")
            return state + " - "+ refGameEngine.getNumOfChipsForBig();
        else if(state == "D")
            return state;

        return "";

    }

    public void displayCommunityCards(int numOfRound)
    {
        List<Card> listOfCard =  refGameEngine.getCommunityCards();
        int indexOfCard =1;
        for(Card card : listOfCard)
        {
            displayCommunityCard(card.getImagecard(),indexOfCard);
            indexOfCard++;
        }
    }

    public void displayCommunityCardsFromReplayList(List<Card> communityCradsFromReplayList)
    {
        int indexOfCard =1;
        for(Card card : communityCradsFromReplayList)
        {
            displayCommunityCard(card.getImagecard(),indexOfCard);
            indexOfCard++;
        }
    }

    public void displayCommunityCard(Image image,int indexOfCard)
    {

        switch (indexOfCard)
        {
            case 1:CommunityCard1.setImage(image);
                break;
            case 2:CommunityCard2.setImage(image);
                break;
            case 3:CommunityCard3.setImage(image);
                break;
            case 4:CommunityCard4.setImage(image);
                break;
            case 5:CommunityCard5.setImage(image);
                break;
            default: break;
        }

    }

    public void hideAllCommunityCard()
    {
        Image imj = new Image("closeCard.png");
        for(int i=1;i<=5;i++)
        {
            displayCommunityCard(imj,i);
        }
    }

    public void displayCurrBetAndCashBoxOnBoard()
    {
        currentBetLabel.setText(Integer.toString(refGameEngine.getCurrBet()));
        totalCashBoxLabel.setText(Integer.toString(refGameEngine.getTableInfo().getCashBox()));
    }

    public void displayBoardStateFromReplayList(int listIter)
    {
        TableInfo Temp = refGameEngine.getTableInfoFromReplayList(listIter);
        currentBetLabel.setText(Integer.toString(Temp.getPot()));
        totalCashBoxLabel.setText(Integer.toString(Temp.getCashBox()));
        displayCommunityCardsFromReplayList(Temp.getCommunityCards());
        currentHandLabel.setText(Integer.toString(refGameEngine.getCurrHandFromReplay(listIter)));
        currentRoundLabel.setText(Integer.toString(refGameEngine.getCurrRoundFromReplay(listIter)));

    }



    public void exposeAllPlayers()
    {
        for(int i=0;i<Utils.numOfPlayers;i++)
        {
            PlayerInfo player =  refGameEngine.getPlayerInfo(i);
            if(!player.getIsQuit())
            {
                exposeCard(i);
            }
        }
    }



    public void exposeAllPlayersCardsAccordingReplayStatus(int listIter)
    {
        for(int i=0;i<Utils.numOfPlayers;i++)
        {
            PlayerInfo player =  refGameEngine.getPlayerInfoFromReplayList(i,listIter);
            if(!player.getIsQuit())
            {
                exposeCard(i);
            }
        }
    }

    public void exposeCard(int numOfPlayer)
    {
        PlayerInfo player =  refGameEngine.getPlayerInfo(numOfPlayer);
        Card[] playerCards = player.getPlayerCards();
        switch(numOfPlayer)
        {
            case 0:
                card11.setImage(playerCards[0].getImagecard());
                card12.setImage(playerCards[1].getImagecard());
                break;
            case 1:
                card21.setImage(playerCards[0].getImagecard());
                card22.setImage(playerCards[1].getImagecard());
                break;
            case 2:
                card31.setImage(playerCards[0].getImagecard());
                card32.setImage(playerCards[1].getImagecard());
                break;
            case 3:
                card41.setImage(playerCards[0].getImagecard());
                card42.setImage(playerCards[1].getImagecard());
                break;
            case 4:
                card51.setImage(playerCards[0].getImagecard());
                card52.setImage(playerCards[1].getImagecard());
                break;
            case 5:
                card61.setImage(playerCards[0].getImagecard());
                card62.setImage(playerCards[1].getImagecard());
                break;
        }
    }

    public void unExposeAllPlayers()
    {
        for(int i=0;i<Utils.numOfPlayers;i++)
        {
            PlayerInfo player =  refGameEngine.getPlayerInfo(i);
            if(!player.getIsQuit())
            {
                unExposeCard(i);
            }
        }
    }

    public void unExposeCard(int numOfPlayer)
    {
        Image imj = new Image("closeCard.png");
        switch(numOfPlayer)
        {
            case 0:
                card11.setImage(imj);
                card12.setImage(imj);
                break;
            case 1:
                card21.setImage(imj);
                card22.setImage(imj);
                break;
            case 2:
                card31.setImage(imj);
                card32.setImage(imj);
                break;
            case 3:
                card41.setImage(imj);
                card42.setImage(imj);
                break;
            case 4:
                card51.setImage(imj);
                card52.setImage(imj);
                break;
            case 5:
                card61.setImage(imj);
                card62.setImage(imj);
                break;
        }
    }

    public void frameSpark()
    {
        int numOfPlayer = refGameEngine.getCurrPlayer();
        ImageView frame = null;
        switch(numOfPlayer)
        {
            case 0:
                frame = frame1;
                break;
            case 1:
                frame = frame2;
                break;
            case 2:
                frame = frame3;
                break;
            case 3:
                frame = frame4;
                break;
            case 4:
                frame = frame5;
                break;
            case 5:
                frame = frame6;
                break;
        }
        fadeTransition.setDuration(Duration.seconds(1.0));
        fadeTransition.setNode(frame);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }
    public void stopFrameSparking()
    {
        fadeTransition.jumpTo(Duration.ZERO);
        fadeTransition.stop();
    }

    public void frameSparkForReplayMode(int numOfPlayer)
    {

        ImageView frame = null;
        switch(numOfPlayer)
        {
            case 0:
                frame = frame1;
                break;
            case 1:
                frame = frame2;
                break;
            case 2:
                frame = frame3;
                break;
            case 3:
                frame = frame4;
                break;
            case 4:
                frame = frame5;
                break;
            case 5:
                frame = frame6;
                break;
        }
        fadeTransition.setDuration(Duration.seconds(1.0));
        fadeTransition.setNode(frame);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }

    public void displayFoldLabelToPlayer(int numOfPlayer)
    {
        //int numOfPlayer = refGameEngine.getCurrPlayer();
        switch (numOfPlayer){
            case 0:
                    gameMove1.setText("Fold");
                    gameMove1.setVisible(true);
                break;
            case 1:
                    gameMove2.setText("Fold");
                    gameMove2.setVisible(true);
                break;
            case 2:
                    gameMove3.setText("Fold");
                    gameMove3.setVisible(true);
                break;
            case 3:
                    gameMove4.setText("Fold");
                    gameMove4.setVisible(true);
                break;
            case 4:
                    gameMove5.setText("Fold");
                    gameMove5.setVisible(true);
                break;
            case 5:
                    gameMove6.setText("Fold");
                    gameMove6.setVisible(true);
                break;
        }
    }
}
