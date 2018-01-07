import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    Server refServer;
    MainUIController mainUIFather;

    int replayListIter;

    public void setFather(MainUIController father)
    {
        mainUIFather = father;
    }
    public void SetServer(Server ser)
    {
        refServer = ser;
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
        NumOfHands.set(refServer.getNumberOfHands());
        Buys.set(refServer.getNumOfChipsPerBuy());
        BigSize.set(refServer.getNumOfChipsForBig());
        SmallSize.set(refServer.getNumOfChipsForsmall());
        if(refServer.getFixedState()){
            BlindsState.set("Fixed");
            Addition.set(0);
        }
        else{
            BlindsState.set("Changes");
            Addition.set(refServer.getAddition());
        }
    }

    public void pressOnFold(ActionEvent event)
    {
        humanPlayerMove("1",0);
        doAfterMove();
    }
    public void pressOnBet(ActionEvent event)
    {
        IntHolder amount = new IntHolder();
        if(checkTextField(textFieldToBet, errorBetLabel,amount))
            humanPlayerMove("2", amount.value);
        doAfterMove();
    }
    public void pressOnCall(ActionEvent event)
    {
        humanPlayerMove("3",0);
        doAfterMove();
    }
    public void pressOnCheck(ActionEvent event)
    {

        humanPlayerMove("4",0);
        doAfterMove();
    }
    public void pressOnRaise(ActionEvent event)
    {
        IntHolder amount = new IntHolder();
        if(checkTextField(textFieldToRaise, errorRaiseLabel,amount))
            humanPlayerMove("5", amount.value);
        doAfterMove();
    }

    private void doAfterMove()
    {
        mainUIFather.updateAllBoard();

    }
    public void pressOnNextHand(ActionEvent event)
    {
        mainUIFather.hideGameMoves();
        mainUIFather.StartHand();
    }
    public void pressOnReplay(ActionEvent event)
    {
        ButtonNext.setDisable(false);
        replayListIter=0;
        mainUIFather.updateAllPlayersFromReplayList(replayListIter);
        mainUIFather.updateTheTableFromReplayList(replayListIter);

    }
    public void pressOnNext(ActionEvent event)
    {
        if(ButtonPrev.isDisable()){
            ButtonPrev.setDisable(false);
        }
        if(replayListIter + 1 < refServer.getReplayListSize())
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
    }

    private void humanPlayerMove(String numOfMove,int amount)
    {
        Utils.RoundResult moveResult= refServer.gameMove(numOfMove, amount);
        mainUIFather.updateAllBoard();
        if(mainUIFather.checkStatus(moveResult))
            mainUIFather.ifCompPlayerIsPlaying();
    }

    public boolean checkTextField(TextField textField, Label errorLabel, IntHolder amount)
    {
        boolean isSucsses = false;
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
                errorLabel.setVisible(false);
                isSucsses = true;
            } catch (NumberFormatException e) {
                errorLabel.setVisible(true);
                errorLabel.setText("Invalid input");
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

        if (refServer.getTypeOfPlayer(Utils.numOfPlayers) == 'H')
            ShowMyCardButton.setDisable(false);
        else
            ShowMyCardButton.setDisable(true);
        ButtonFold.setDisable(!refServer.validateMove("1"));
        ButtonBet.setDisable(!refServer.validateMove("2"));
        ButtonCall.setDisable(!refServer.validateMove("3"));
        ButtonCheck.setDisable(!refServer.validateMove("4"));
        ButtonRaise.setDisable(!refServer.validateMove("5"));
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
    }
}


