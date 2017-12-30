import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


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

    Server refServer;
    MainUIController mainUIFather;

    @FXML
    private void initialize() {
        gridPane1.setVisible(false);
        gridPane2.setVisible(false);
        gridPane3.setVisible(false);
        gridPane4.setVisible(false);
        gridPane5.setVisible(false);
        gridPane6.setVisible(false);
    }

    public void setFather(MainUIController father)
    {
        mainUIFather = father;
    }
    public void SetServer(Server ser)
    {
        refServer = ser;
    }


    public void displayPlayerDetailsOnTheBoard()
    {
        for(int i=0;i<Utils.numOfPlayers;i++)
        {
            PlayerInfo player =  refServer.getPlayerInfo(i);
            if(!player.getIsQuit())
                displayPlayerByIndex(player,i);
        }

    }
    public void displayPlayerByIndex(PlayerInfo player, int numOfPlayer)
    {
        switch(numOfPlayer)
        {
            case 0:
                changeLabelToPlayer(player,nameLabel1,stateLabel1,chipsLabel1,buysLabel1,handWonLabel1,gridPane1);
                break;
            case 1:
                changeLabelToPlayer(player,nameLabel2,stateLabel2,chipsLabel2,buysLabel2,handWonLabel2,gridPane2);
                break;
            case 2:
                changeLabelToPlayer(player,nameLabel3,stateLabel3,chipsLabel3,buysLabel3,handWonLabel3,gridPane3);
                break;
            case 3:
                changeLabelToPlayer(player,nameLabel4,stateLabel4,chipsLabel4,buysLabel4,handWonLabel4,gridPane4);
                break;
            case 4:
                changeLabelToPlayer(player,nameLabel5,stateLabel5,chipsLabel5,buysLabel5,handWonLabel5,gridPane5);
                break;
            case 5:
                changeLabelToPlayer(player,nameLabel6,stateLabel6,chipsLabel6,buysLabel6,handWonLabel6,gridPane6);
                break;
        }

    }

    private void changeLabelToPlayer(PlayerInfo player, Label name,Label state,Label chips,Label buy, Label handsOfWon, GridPane grinPane)
    {
        name.setText(player.getName());
        state.setText(player.getPlayerState());
        chips.setText(Integer.toString(player.getPlayerChips()));
        buy.setText(Integer.toString(player.getPlayerBuys()));
        handsOfWon.setText(Integer.toString(player.getPlayerHandsWon()));
        grinPane.setVisible(true);
    }

}
