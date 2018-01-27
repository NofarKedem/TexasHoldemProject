import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BuyPopUpController {

    private Stage primaryStage;
    private GameEngine refGameEngine;
    private MainUIController mainUIFather;

    @FXML Button okButton;

    @FXML Text buyText;
    @FXML CheckBox player1;
    @FXML CheckBox player2;
    @FXML CheckBox player3;
    @FXML CheckBox player4;
    @FXML CheckBox player5;
    @FXML CheckBox player6;

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

    public void setAllNotVisible(){
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        player4.setVisible(false);
        player5.setVisible(false);
        player6.setVisible(false);
    }

    public void setPlayersDetails(){
        List<PlayerInfo> players = refGameEngine.getAllPlayerInfo();
        for(int i=1; i <= Utils.numOfPlayers;i++){
            switch (i){
                case 1:
                    player1.setText(players.get(i-1).getName());
                    player1.setVisible(true);
                    if(players.get(i-1).getTypeOfPlayer() == 'C') {
                        player1.setDisable(true);
                    }
                    break;
                case 2:
                    player2.setText(players.get(i-1).getName());
                    player2.setVisible(true);
                    if(players.get(i-1).getTypeOfPlayer() == 'C') {
                        player2.setDisable(true);
                    }
                    break;
                case 3:
                    player3.setText(players.get(i-1).getName());
                    player3.setVisible(true);
                    if(players.get(i-1).getTypeOfPlayer() == 'C') {
                        player3.setDisable(true);
                    }
                    break;
                case 4:
                    player4.setText(players.get(i-1).getName());
                    player4.setVisible(true);
                    if(players.get(i-1).getTypeOfPlayer() == 'C') {
                        player4.setDisable(true);
                    }
                    break;
                case 5:
                    player5.setText(players.get(i-1).getName());
                    player5.setVisible(true);
                    if(players.get(i-1).getTypeOfPlayer() == 'C') {
                        player5.setDisable(true);
                    }
                    break;
                case 6:
                    player6.setText(players.get(i-1).getName());
                    player6.setVisible(true);
                    if(players.get(i-1).getTypeOfPlayer() == 'C') {
                        player6.setDisable(true);
                    }
                    break;
            }
        }
    }

    public List<PlayerInfo> getCheckBoxInf() {
        List<PlayerInfo> playersInfo = refGameEngine.getAllPlayerInfo();
        List<PlayerInfo> selectedPlayers = new ArrayList<>();
        for (int i = 1; i <= Utils.numOfPlayers; i++) {
            switch (i) {
                case 1:
                    if (player1.isSelected()) {
                        selectedPlayers.add(playersInfo.get(i - 1));
                    }
                    break;
                case 2:
                    if (player2.isSelected()) {
                        selectedPlayers.add(playersInfo.get(i - 1));
                    }
                    break;
                case 3:
                    if (player3.isSelected()) {
                        selectedPlayers.add(playersInfo.get(i - 1));
                    }
                    break;
                case 4:
                    if (player4.isSelected()) {
                        selectedPlayers.add(playersInfo.get(i - 1));
                    }
                    break;
                case 5:
                    if (player5.isSelected()) {
                        selectedPlayers.add(playersInfo.get(i - 1));
                    }
                    break;
                case 6:
                    if (player6.isSelected()) {
                        selectedPlayers.add(playersInfo.get(i - 1));
                    }
                    break;
            }
        }
        return selectedPlayers;
    }

    public void updatePlayersBuys(List<PlayerInfo> players){
        refGameEngine.addChipsToPlayer(players);
    }

    public void pressOkButton(){
        List<PlayerInfo> result = getCheckBoxInf();
        if(result.size() > 0) {
            updatePlayersBuys(result);
            mainUIFather.updatePlayersTable();
        }
        primaryStage.close();
    }
}

