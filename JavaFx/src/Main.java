import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception
    {/*
        Label label = new Label("Blink");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        Scene scene = new Scene(new StackPane(label));
        primaryStage.setScene(scene);
        primaryStage.show();
        */
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource("MainUI.fxml");
        loader.setLocation(mainFXML);
        BorderPane root = loader.load();
        MainUIController mainUIController = loader.getController();
        mainUIController.setServerToContoroller();
        mainUIController.setFatherToConroller();
        mainUIController.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Texsas Holdem");
        Scene scene = new Scene(root, 1050, 600);
        mainUIController.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
