package GUI.Util;

import GUI.Controllers.MainController;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class ErrorDisplayer {
    /**
     * Displays an error as a modal to the user.
     * @param throwable The error to display.
     */
    public static void displayError(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().getStylesheets().removeAll();
        alert.getDialogPane().getStylesheets().add(MainController.currentStyle);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(throwable.getLocalizedMessage());
        alert.showAndWait();
    }
}
