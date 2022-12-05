package GUI.Controllers;

import BE.PlayList;
import GUI.Models.PlayListModel;
import GUI.Util.ErrorDisplayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {
    @FXML
    private TextField textName;
    @FXML
    private Button btnOK;
    private PlayListModel playListModel;
    private PlayList playList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (playListModel.getSelectedPlayList() != null) {
            playList = playListModel.getSelectedPlayList();
            textName.setText(playList.getTitle());
        }

        //Disable the OK button until there is new input in the text field.
        btnOK.setDisable(true);
        //Adding a listener, and enabling/disabling the OK button if name is empty
        textName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!textName.getText().trim().isEmpty()) {
                    btnOK.setDisable(false);
                } else {
                    btnOK.setDisable(true);
                }
            } catch (Exception e) {
                ErrorDisplayer.displayError(e);
            }
        });
    }

    public void handleOK() {
        //TO DO: implement functionality.
        if (playListModel.getSelectedPlayList() == null) {
            //Create new playlist
        } else {
            //Update existing playlist
        }
        handleClose();
    }

    public void handleClose() {
        Stage stage = (Stage) textName.getScene().getWindow();
        stage.close();
    }

    public void setModel(PlayListModel playlistModel) {
        this.playListModel = playlistModel;
    }
}
