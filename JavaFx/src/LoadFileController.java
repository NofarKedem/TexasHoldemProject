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
    @FXML MenuItem menuItemRestartNewGame;
    @FXML MenuItem menuItemRestartCurrGame;
    @FXML MenuItem ButtonStartGame;
    @FXML Label statusGameLabel;
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
            statusGameLabel.setText("File loaded successfully");
            mainUIFather.updateXMLDetails();
            ButtonStartGame.setDisable(false);
        }
            catch(Exception e)
        {
            statusGameLabel.setText(e.getMessage());
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

    public void restartGameForNewGame()
    {
        disableGameButtonAtStartingGame();
        refServer.restartGameForNewGame();
        mainUIFather.reset();

    }
    public void restartCurrentGame()
    {
        refServer.restartCurrentGame();
        mainUIFather.reset();
        mainUIFather.StartHand();
    }
    public void disableGameButtonAtStartingGame()
    {
        menuItemRestartNewGame.setDisable(true);
        menuItemRestartCurrGame.setDisable(true);
        ButtonStartGame.setDisable(true);
        ButtonLoadFile.setDisable(false);
        resetStatusGameLabel();
    }

    public void disableGameButton(boolean bool)
    {
        menuItemRestartNewGame.setDisable(bool);
        menuItemRestartCurrGame.setDisable(bool);
    }
    public void setStatusGameLabelToEndGame()
    {
        statusGameLabel.setText("Game was over, you can start new game or restart the current game");
    }
    public void resetStatusGameLabel()
    {
        statusGameLabel.setText("");
    }
}
