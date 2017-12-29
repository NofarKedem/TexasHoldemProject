import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class PlayerTableController implements Initializable{
    Server refServer;
    MainUIController mainUIFather;
    @FXML
    TableView<PlayerInfo> tableView;
    @FXML
    TableColumn Name = new TableColumn();
    @FXML
    TableColumn Id = new TableColumn();
    @FXML
    TableColumn Type = new TableColumn();



    public void SetServer(Server ser)
    {
        refServer = ser;
    }
    public void setFather(MainUIController father)
    {
        mainUIFather = father;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Name.setCellValueFactory(new PropertyValueFactory<PlayerInfo, String>("name"));
        Id.setCellValueFactory(new PropertyValueFactory<PokerPlayer1, Integer>("id"));
        Type.setCellValueFactory(new PropertyValueFactory<PokerPlayer1, Character>("type"));
    }

    public void displayTable(ObservableList<PlayerInfo> pokerPlayers)
    {
        tableView.getItems().clear();
        tableView.getItems().addAll(pokerPlayers);
    }


}
