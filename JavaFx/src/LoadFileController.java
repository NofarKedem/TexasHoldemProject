import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LoadFileController {
    private Stage primaryStage;
    @FXML
    MenuItem ButtonLoadFile;
    @FXML Menu menuStartGame;
    @FXML
    MenuItem ButtonStartGame;
    @FXML Label statusFile;
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
    public void PressOnStartGame()
    {
        ButtonLoadFile.setDisable(true);
       // mainUIFather.displayPlayerOnBoard();
        mainUIFather.StartHand();
        ButtonStartGame.setDisable(true);

    }
    public void PressOnLoadFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml file", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();

        try {
            refServer.loadFile(absolutePath);
            statusFile.setText("File loaded successfully");
            mainUIFather.updateXMLDetails();
            ButtonStartGame.setDisable(false);
        }
            catch(Exception e)
        {
            statusFile.setText(e.getMessage());
        }

    }

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }


    public void changeSkinOption1()
    {
        mainUIFather.changeSkinOption1();
    }

    public void changeSkinOption2()
    {
        mainUIFather.changeSkinOption2();
    }

}
