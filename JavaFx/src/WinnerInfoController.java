import GameLogic.BlindsHelper;
import GameLogic.GameEngine;
import GameLogic.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

public class WinnerInfoController {
    private Stage primaryStage;
    @FXML Label winnerLable;
    @FXML Label cardCombinationLable;
    @FXML Label prizeLable;
    @FXML Button okButton;

    @FXML Text theWinnerText;
    @FXML Text cardsCombText;
    @FXML Text prizeText;

    private GameEngine refGameEngine;
    private MainUIController mainUIFather;
    private Map<Integer, String> WinnerMapRef;

    public void SetServer(GameEngine ser)
    {
        refGameEngine = ser;
    }

    public void setFather(MainUIController father)
    {
        mainUIFather = father;
    }

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    public void setWinnerDetails(Utils.RoundResult winResult){
        WinnerMapRef = refGameEngine.getWinnerMap();
        String WinnersNames = "";
        String cardsComb = "";
        if(winResult == Utils.RoundResult.ENDGAME) {
            if (WinnerMapRef.size() == 1) {
                theWinnerText.setText("The Winner Is:");
                cardsCombText.setText("His Cards Combination:");
                prizeText.setText("The winning prize:");
            } else {
                theWinnerText.setText("The Winners Are:");
                cardsCombText.setText("Cards Combinations of each:");
                prizeText.setText("The winning prize for each:");
            }

            for (Integer numOfPlayer : WinnerMapRef.keySet()) {
                //need to find the player index
                WinnersNames = WinnersNames + refGameEngine.getPlayerName(numOfPlayer - 1) + "  ";
                cardsComb = cardsComb + WinnerMapRef.get(numOfPlayer) + "  ";
            }
            winnerLable.setText(WinnersNames);
            cardCombinationLable.setText(cardsComb);
            prizeLable.setText(String.valueOf(refGameEngine.calcWinnersChipPrize()));
        }
        else if(winResult == Utils.RoundResult.ALLFOLDED){
            theWinnerText.setText("All the other players folded, " +
                            "this is a Technical victory to:");
            prizeText.setText("The winning prize:");
            for (Integer numOfPlayer : WinnerMapRef.keySet()) {
                WinnersNames = WinnersNames + refGameEngine.getPlayerName(numOfPlayer - 1) + "  ";
            }
            winnerLable.setText(WinnersNames);
            cardsCombText.setVisible(false);
            cardCombinationLable.setVisible(false);
            prizeLable.setText(String.valueOf(refGameEngine.calcWinnersChipPrize()));
        }
        else{
            theWinnerText.setText("All the Human players folded, " +
                    "this is a Technical victory to computer players:");
            prizeText.setText("The winning prize:");
            for (Integer numOfPlayer : WinnerMapRef.keySet()) {
                WinnersNames = WinnersNames + refGameEngine.getPlayerName(numOfPlayer - 1) + "  ";
            }
            winnerLable.setText(WinnersNames);
            cardsCombText.setVisible(false);
            cardCombinationLable.setVisible(false);
            prizeLable.setText(String.valueOf(refGameEngine.calcWinnersChipPrize()));
        }
    }


    public void pressOK(){

        mainUIFather.updateTableWithWinProp();
        primaryStage.close();
    }




}
