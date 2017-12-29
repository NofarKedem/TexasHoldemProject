import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameDetailsController {

    @FXML Label NumOfHandsLabel;
    @FXML Label BuysLabel;
    @FXML Label BigSizeLabel;
    @FXML Label SmallSizeLabel;
    @FXML Button ButtonNextHand;
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
    public void SetServer(Server ser)
    {
        refServer = ser;
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
        //resultOfMove = server.gameMove(numOfMove, amount);
        refServer.gameMove("1", 0);
        mainUIFather.ifCompPlayerIsPlaying();
    }
    public void pressOnBet(ActionEvent event)
    {

    }
    public void pressOnCall(ActionEvent event)
    {

    }
    public void pressOnCheck(ActionEvent event)
    {
        if(mainUIFather.checkStatus(refServer.gameMove("4", 0)));
            mainUIFather.ifCompPlayerIsPlaying();
    }
    public void pressOnRaise(ActionEvent event)
    {

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
}


