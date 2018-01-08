import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LoadFileController{
    private Stage primaryStage;
    @FXML
    MenuItem ButtonLoadFile;
    @FXML Menu menuStartGame;
    @FXML MenuItem menuItemRestartNewGame;
    @FXML MenuItem menuItemRestartCurrGame;
    @FXML MenuItem ButtonStartGame;
    @FXML Label statusGameLabel;
    @FXML ProgressBar progressBar = new ProgressBar(0);


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
    public void PressOnLoadFile() throws Exception
    {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml file", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        Task loadFileTask = new Task(refServer, absolutePath);

        /*if(progressBar.getProgress() == 100) {
            progressBar.setProgress(0);
        }*/

        try {
            Thread e = new Thread(loadFileTask);
            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(loadFileTask.progressProperty());

            e.start();
            loadFileTask.setOnSucceeded( (event) -> {

                if(loadFileTask.getValue().result) {
                    statusGameLabel.setText("File loaded successfully");
                    mainUIFather.updateXMLDetails();
                    ButtonStartGame.setDisable(false);
                } else {
                    statusGameLabel.setText(loadFileTask.getValue().msg);
                    mainUIFather.clearTable();
                }
            });
            /*refServer.loadFile(absolutePath);
            statusGameLabel.setText("File loaded successfully");
            mainUIFather.updateXMLDetails();
            ButtonStartGame.setDisable(false);*/
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
        mainUIFather.updateTableWithWinProp();
        mainUIFather.StartHand();
    }
    public void disableGameButtonAtStartingGame()
    {
        menuItemRestartNewGame.setDisable(true);
        menuItemRestartCurrGame.setDisable(true);
        ButtonStartGame.setDisable(true);
        ButtonLoadFile.setDisable(false);
        setStatus("");
    }

    public void disableGameButton(boolean bool)
    {
        menuItemRestartNewGame.setDisable(bool);
        menuItemRestartCurrGame.setDisable(bool);
    }

    public void statusPlayerNotHasEnoughChips()
    {
        String msg = refServer.getPlayerInfo(refServer.getCurrPlayer()).getName()+ " doesn't have enough chips to play in this hand, therefor the player quit!";
        statusGameLabel.setText(msg);
        statusGameLabel.setDisable(false);
    }

    public void setStatus(String msg)
    {
        statusGameLabel.setDisable(false);
        statusGameLabel.setText(msg);
    }

}
