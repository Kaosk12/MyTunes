package GUI.Controllers;

import BE.Song;
import GUI.Models.SongModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SongUpdateController implements Initializable {

    private SongModel songModel;
    private Song song;
    @FXML
    private TextField textTitle, textArtist, textGenre;
    
    @FXML
    private Button btnOK, btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        song = SongModel.selectedSong;
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

    /**
     * Changes the title, artist, and genre of the song to the new input once the user presses OK.
     */
    public void handleOK() {
        //Set the title, artist, and genre to new input
        song.setTitle(textTitle.getText());
        song.setArtist(textArtist.getText());
        song.setGenre(textGenre.getText());

        //Send the song down the layers and update it in DB
        try {
            songModel.updateSong(song);
            songModel.search("");
        } catch (Exception e) {
            throw new RuntimeException(e); //Change to a nice error display?
        }

        //Get handle of the stage, and close it.
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }

    public void handleCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
