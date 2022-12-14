package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Views/MainView.fxml"));
        primaryStage.setTitle("MyTunes | Gruppe 2");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image("/play.png"));
        primaryStage.setScene(new Scene(root));

        //Fixed width & height. Could be removed once HGrow & VGrow has been set correctly.
        primaryStage.setMinWidth(1010.0);
        primaryStage.setMinHeight(736.0);
        primaryStage.setMaxWidth(1010.0);
        primaryStage.setMaxHeight(736.0);

        //Add styling with CSS
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/GUI/CSS/TestColorVar.css").toExternalForm());

        primaryStage.show();
    }
}