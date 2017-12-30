import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
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
    @FXML Button ButtonNextHand;
    @FXML TextField textFieldToBet;
    @FXML TextField textFieldToRaise;
    @FXML Label errorBetLabel;
    @FXML Label errorRaiseLabel;
    private SimpleLongProperty NumOfHands;
    private SimpleLongProperty Buys;
    private SimpleLongProperty BigSize;
    private SimpleLongProperty SmallSize;
    Server refServer;
    MainUIController mainUIFather;

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
    }

    @FXML
    private void initialize() {
        NumOfHandsLabel.textProperty().bind(Bindings.format("%,d", NumOfHands));
        BuysLabel.textProperty().bind(Bindings.format("%,d", Buys));
        BigSizeLabel.textProperty().bind(Bindings.format("%,d", BigSize));
        SmallSizeLabel.textProperty().bind(Bindings.format("%,d", SmallSize));
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
    }

    public void pressOnFold(ActionEvent event)
    {
        humanPlayerMove("1",0);
        mainUIFather.displayCurrBetAndCashBoxOnBoard();
        mainUIFather.ifCompPlayerIsPlaying();
    }
    public void pressOnBet(ActionEvent event)
    {
        IntHolder amount = new IntHolder();
        if(checkTextField(textFieldToBet, errorBetLabel,amount))
            humanPlayerMove("2", amount.value);
        mainUIFather.displayCurrBetAndCashBoxOnBoard();
        mainUIFather.ifCompPlayerIsPlaying();
    }
    public void pressOnCall(ActionEvent event)
    {
        humanPlayerMove("3",0);
        mainUIFather.displayCurrBetAndCashBoxOnBoard();
        mainUIFather.ifCompPlayerIsPlaying();
    }
    public void pressOnCheck(ActionEvent event)
    {

        humanPlayerMove("4",0);
        mainUIFather.displayCurrBetAndCashBoxOnBoard();
        mainUIFather.ifCompPlayerIsPlaying();
    }
    public void pressOnRaise(ActionEvent event)
    {
        IntHolder amount = new IntHolder();
        if(checkTextField(textFieldToRaise, errorRaiseLabel,amount))
            humanPlayerMove("5", amount.value);
        mainUIFather.displayCurrBetAndCashBoxOnBoard();
        mainUIFather.ifCompPlayerIsPlaying();
    }
    public void pressOnNextHand(ActionEvent event)
    {

    }
    public void pressOnReplay(ActionEvent event)
    {

    }
    public void pressOnNext(ActionEvent event)
    {

    }
    public void pressOnPrev(ActionEvent event)
    {

    }
    public void pressOnBuyChips(ActionEvent event)
    {

    }

    private void humanPlayerMove(String numOfMove,int amount)
    {
        Utils.RoundResult moveResult= refServer.gameMove(numOfMove, amount);
        mainUIFather.displayPlayerOnBoard();
        if(mainUIFather.checkStatus(moveResult));
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
}


