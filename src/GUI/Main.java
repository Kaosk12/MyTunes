package GUI;

import GUI.Models.PlayerModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Views/MainView.fxml"));
        primaryStage.setTitle("MyTunes | Gruppe 2");
        primaryStage.setScene(new Scene(root));

        //Fixed width & height. Could be removed once HGrow & VGrow has been set correctly.
        primaryStage.setMinWidth(1010.0);
        primaryStage.setMinHeight(640.0);
        primaryStage.setMaxWidth(1010.0);
        primaryStage.setMaxHeight(640.0);

        primaryStage.show();

    }


}