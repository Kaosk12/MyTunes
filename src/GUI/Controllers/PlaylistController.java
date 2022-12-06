package GUI.Controllers;

import BE.PlayList;
import GUI.Models.PlayListModel;
import GUI.Util.ErrorDisplayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

    @FXML
    private TextField textName;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    public TableView<PlayList> tbvPlayLists;

    private PlayListModel playListModel;
    private PlayList playList;
    private MainController mainController;
    private String playlistName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = new MainController();
        playListModel = mainController.getPlaylistModel();
        if (playListModel.getSelectedPlaylist() != null) {
            playList = playListModel.getSelectedPlaylist();
            textName.setText(playList.getTitle());
        }

        //Disable the OK button until there is new input in the text field.
        btnOK.setDisable(true);
        //Adding a listener, and enabling/disabling the OK button if name is empty
        textName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!textName.getText().trim().isEmpty()) {
                    btnOK.setDisable(false);
                    playlistName = newValue;
                } else {
                    btnOK.setDisable(true);
                }
            } catch (Exception e) {
                ErrorDisplayer.displayError(e);
            }
        });
    }

    public void handleOK() {

        try {
            //set the new title
            playList.setTitle(playlistName);
            //updates title in the db
            playListModel.updatePlayList(playList);
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
        //refreshes the GUI.
        tbvPlayLists.refresh();
        //Closes the window.
        handleClose();
    }

    public void handleClose() {
        Stage stage = (Stage) textName.getScene().getWindow();
        stage.close();
    }
    public void setTbvPlayLists(TableView tableView){
        tbvPlayLists = tableView;
    }
}
