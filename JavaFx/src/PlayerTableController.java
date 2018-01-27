import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;


public class PlayerTableController implements Initializable{
    GameEngine refGameEngine;
    MainUIController mainUIFather;
    @FXML
    TableView<PlayerInfo> tableView;
    @FXML
    TableColumn Name = new TableColumn();
    @FXML
    TableColumn Id = new TableColumn();
    @FXML
    TableColumn Type = new TableColumn();
    @FXML
    TableColumn Buys = new TableColumn();
    @FXML
    TableColumn HandWins = new TableColumn();
    @FXML
    TableColumn WinningPrice = new TableColumn();



    public void SetServer(GameEngine ser)
    {
        refGameEngine = ser;
    }
    public void setFather(MainUIController father)
    {
        mainUIFather = father;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Name.setCellValueFactory(new PropertyValueFactory<PlayerInfo, String>("name"));
        Id.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("id"));
        Type.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Character>("typeOfPlayer"));
        Buys.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("playerBuys"));
        HandWins.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("playerHandsWon"));
        WinningPrice.setCellValueFactory(new PropertyValueFactory<PlayerInfo, Integer>("playerChips"));
    }

    public void displayTable(ObservableList<PlayerInfo> pokerPlayers)
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(pokerPlayers);
    }

    public void clearTable() {
        tableView.getItems().clear();
    }



}
