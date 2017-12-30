import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WinnerInfoController {
    private Stage primaryStage;
    @FXML Label winnerLable;
    @FXML Label cardCombinationLable;
    @FXML Label prizeLable;
    @FXML Button okButton;

    @FXML Text theWinnerText;
    @FXML Text cardsCombText;
    @FXML Text prizeText;


    Server refServer;
    MainUIController mainUIFather;

    public void setServerToContoroller(Server ser)
    {
        refServer = ser;
    }

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }



}
