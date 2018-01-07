import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource("MainUI.fxml");
        loader.setLocation(mainFXML);
        //BorderPane root = loader.load();
        ScrollPane root = loader.load();
        MainUIController mainUIController = loader.getController();
        mainUIController.setServerToContoroller();
        mainUIController.setFatherToConroller();
        mainUIController.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Texsas Holdem");
        Scene scene = new Scene(root, 1050, 620);
        mainUIController.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
