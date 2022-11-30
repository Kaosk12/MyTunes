package GUI.Controllers;

import BE.Song;
import GUI.Models.SongModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SongUpdateController implements Initializable {

    private SongModel songModel;
    @FXML
    private TextField textTitle, textArtist, textGenre;
    
    @FXML
    private Button btnOK, btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Song song = SongModel.selectedSong;
        textTitle.setText(song.getTitle());
        textArtist.setText(song.getArtist());
        textGenre.setText(song.getGenre());
    }

    /**
     * Set the model to the same model used in MainView.
     * @param songModel, a songModel object
     */
    public void setSongModel(SongModel songModel) {
        this.songModel = songModel;
    }

    public void handleOK() {

    }

    public void handleCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
