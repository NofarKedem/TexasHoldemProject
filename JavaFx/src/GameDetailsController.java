import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;

public class GameDetailsController {

    @FXML Label NumOfHandsLabel;
    @FXML Label BuysLabel;
    @FXML Label BigSizeLabel;
    @FXML Label SmallSizeLabel;
    @FXML Label BlindStateLable;
    @FXML Label AdditionLable;
    @FXML Button ButtonNextHand;
    @FXML Button ButtonReplay;
    @FXML Button ButtonNext;
    @FXML Button ButtonPrev;
    @FXML Button ButtonBuyChips;
    @FXML Button QuitFromTheGameButton;
    @FXML TextField textFieldToBet;
    @FXML TextField textFieldToRaise;
    @FXML Label errorBetLabel;
    @FXML Label errorRaiseLabel;
    @FXML Button ShowMyCardButton;
    @FXML Button ButtonFold;
    @FXML Button ButtonBet;
    @FXML Button ButtonCall;
    @FXML Button ButtonCheck;
    @FXML Button ButtonRaise;

    private SimpleLongProperty NumOfHands;
    private SimpleLongProperty Buys;
    private SimpleLongProperty BigSize;
    private SimpleLongProperty SmallSize;
    private SimpleLongProperty Addition;
    private SimpleStringProperty BlindsState;
    GameEngine refGameEngine;
    MainUIController mainUIFather;
    boolean isReplayOn = false;

    int replayListIter;

    public void setFather(MainUIController father)
    {
        mainUIFather = father;
    }
    public void SetServer(GameEngine ser)
    {
        refGameEngine = ser;
    }

    public GameDetailsController() {
        NumOfHands = new SimpleLongProperty(0);
        Buys = new SimpleLongProperty(0);
        BigSize = new SimpleLongProperty(0);
        SmallSize = new SimpleLongProperty(0);
        Addition = new SimpleLongProperty(0);
        BlindsState = new SimpleStringProperty(" ");
    }

    @FXML
    private void initialize() {

        NumOfHandsLabel.textProperty().bind(Bindings.format("%,d", NumOfHands));
        BuysLabel.textProperty().bind(Bindings.format("%,d", Buys));
        BigSizeLabel.textProperty().bind(Bindings.format("%,d", BigSize));
        SmallSizeLabel.textProperty().bind(Bindings.format("%,d", SmallSize));
        BlindStateLable.textProperty().bind(Bindings.concat(BlindsState));
        AdditionLable.textProperty().bind(Bindings.format("%,d",Addition));
    }


    public void setButtonNextHandDisable(boolean flag)
    {
        ButtonNextHand.setDisable(flag);
    }

    public void displayGameDetails()
    {
        NumOfHands.set(refGameEngine.getNumberOfHands());
        Buys.set(refGameEngine.getNumberOfBuys());
        BigSize.set(refGameEngine.getNumOfChipsForBig());
        SmallSize.set(refGameEngine.getNumOfChipsForsmall());
        if(refGameEngine.getFixedState()){
            BlindsState.set("Fixed");
            Addition.set(0);
        }
        else{
            BlindsState.set("Changes");
            Addition.set(refGameEngine.getAddition());
        }
    }

    public void pressOnFold(ActionEvent event)
    {
        moveAfterPressing(Round.GameMoves.FOLD);

    }
    public void pressOnBet(ActionEvent event)
    {
        moveAfterPressing(Round.GameMoves.BET);

    }
    public void pressOnCall(ActionEvent event)
    {
        moveAfterPressing(Round.GameMoves.CALL);

    }
    public void pressOnCheck(ActionEvent event)
    {
        moveAfterPressing(Round.GameMoves.CHECK);

    }
    public void pressOnRaise(ActionEvent event)
    {
        moveAfterPressing(Round.GameMoves.RAISE);

    }

    private void moveAfterPressing(Round.GameMoves GameMove)
    {
        IntHolder amount = new IntHolder();
        refGameEngine.setMoveForAmountValidatoin(GameMove);
        if(!isPlayerHasEnoughChips()) {
            humanPlayerMove("1", 0);
            return;
        }
        switch (GameMove)
        {
            case FOLD:
                humanPlayerMove("1",0);
                break;
            case BET:
                if (checkTextField(textFieldToBet, errorBetLabel, amount))
                    humanPlayerMove("2", amount.value);
                break;
            case CALL:
                humanPlayerMove("3",0);
                break;
            case CHECK:
                humanPlayerMove("4",0);
                break;
            case RAISE:
                if (checkTextField(textFieldToRaise, errorRaiseLabel, amount))
                    humanPlayerMove("5", amount.value);

                break;
        }
        mainUIFather.updateAllBoard();

    }

    public void pressOnNextHand(ActionEvent event)
    {
        mainUIFather.hideGameMoves();
        mainUIFather.StartHand();
    }
    public void pressOnReplay(ActionEvent event)
    {
        isReplayOn = !isReplayOn;
        if(isReplayOn==false)
        {
            ButtonReplay.setText("Start replay");
            ButtonNextHand.setDisable(false);
            ButtonBuyChips.setDisable(false);
            ButtonNext.setDisable(true);
            ButtonPrev.setDisable(true);

        }
        else
        {
            ButtonReplay.setText("Stop replay");
            ButtonNextHand.setDisable(true);
            ButtonBuyChips.setDisable(true);
            ButtonNext.setDisable(false);
            replayListIter=0;
            mainUIFather.updateAllPlayersFromReplayList(replayListIter);
            mainUIFather.updateTheTableFromReplayList(replayListIter);
        }


    }
    public void pressOnNext(ActionEvent event)
    {
        if(ButtonPrev.isDisable()){
            ButtonPrev.setDisable(false);
        }
        if(replayListIter + 1 < refGameEngine.getReplayListSize())
            replayListIter++;
        else{
            ButtonNext.setDisable(true);
        }
        mainUIFather.updateAllPlayersFromReplayList(replayListIter);
        mainUIFather.updateTheTableFromReplayList(replayListIter);
    }
    public void pressOnPrev(ActionEvent event)
    {
        if(ButtonNext.isDisable()){
            ButtonNext.setDisable(false);
        }
        if(replayListIter > 0)
            replayListIter--;
        else {
            ButtonPrev.setDisable(true);
        }
        mainUIFather.updateAllPlayersFromReplayList(replayListIter);
        mainUIFather.updateTheTableFromReplayList(replayListIter);
    }
    public void pressOnBuyChips(ActionEvent event)
    {
        mainUIFather.showBuyPopUp();
        displayGameDetails();
    }

    private void humanPlayerMove(String numOfMove,int amount)
    {
        int numOfPlayer = refGameEngine.getCurrPlayer();
        Utils.RoundResult moveResult= refGameEngine.gameMove(numOfMove, amount);
        mainUIFather.updateAllBoard();
        if(mainUIFather.checkStatus(moveResult,numOfPlayer))
            mainUIFather.ifCompPlayerIsPlaying();
    }

    public boolean checkTextField(TextField textField, Label errorLabel, IntHolder amount)
    {
        boolean isSucsses = false;
        errorLabel.setFont(new Font("Arial", 10));
        String amountStr = textField.getText();
        if(amountStr.equals("")) {
            errorLabel.setText("You have to type amount");
            errorLabel.setVisible(true);
        }
        else {
            try {
                //humanPlayerMove("2", Integer.parseInt(amountStr));
                amount.value = Integer.parseInt(amountStr);
                //amount =  Integer.parseInt(amountStr);

                if(!refGameEngine.validAmount(amount.value)) {
                    errorLabel.setText("The amount you enter is invalid");
                    errorLabel.setVisible(true);

                }
                else {
                    errorLabel.setVisible(false);
                    isSucsses = true;
                }

            } catch (NumberFormatException e) {
                errorLabel.setVisible(true);
                errorLabel.setText("Invalid input");
            }
            catch (Exception e) {
                errorLabel.setVisible(true);
                errorLabel.setText(e.getMessage());

            }
        }
        return isSucsses;
    }

    public void pressOnShowMyCard()
    {
        mainUIFather.exposeCurrentCardOfHumanPlayer();
    }
    public void releaseOnShowMyCard()
    {
        mainUIFather.unExposeCurrentCardOfHumanPlayer();
    }

    public void setDisableToMoveButton() {

        if (refGameEngine.getTypeOfPlayer(Utils.numOfPlayers) == 'H')
            ShowMyCardButton.setDisable(false);
        else
            ShowMyCardButton.setDisable(true);

        QuitFromTheGameButton.setDisable(false);
        ButtonFold.setDisable(!refGameEngine.validateMove("1"));
        ButtonBet.setDisable(!refGameEngine.validateMove("2"));
        ButtonCall.setDisable(!refGameEngine.validateMove("3"));
        ButtonCheck.setDisable(!refGameEngine.validateMove("4"));
        ButtonRaise.setDisable(!refGameEngine.validateMove("5"));
    }

    public void disableHandFinishButton(boolean bool)
    {
        ButtonNextHand.setDisable(bool);
        ButtonReplay.setDisable(bool);
        //ButtonNext.setDisable(bool);
        //ButtonPrev.setDisable(bool);
        ButtonBuyChips.setDisable(bool);
    }

    public void disablePlayerMove()
    {
        ButtonFold.setDisable(true);
        ButtonBet.setDisable(true);
        ButtonCall.setDisable(true);
        ButtonCheck.setDisable(true);
        ButtonRaise.setDisable(true);
        ShowMyCardButton.setDisable(true);
        QuitFromTheGameButton.setDisable(true);
    }

    public boolean isPlayerHasEnoughChips() {
        if (!refGameEngine.isPlayerHasEnoughChips()) {
            mainUIFather.statusPlayerNotHasEnoughChips();
           return false;
        }
        return true;
    }
    public void pressOnQuitFromTheGame(ActionEvent event)
    {
        refGameEngine.setQuitFromTheGameToCurrentPlayer();
        moveAfterPressing(Round.GameMoves.FOLD);
    }
}


