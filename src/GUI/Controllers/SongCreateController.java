package GUI.Controllers;

import BE.Song;
import GUI.Models.MediaModel;
import GUI.Models.SongModel;
import GUI.Util.ErrorDisplayer;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SongCreateController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private TextField textTitle, textArtist, textGenre, textFile;
    private SongModel songModel;

    private  MediaPlayer mediaPlayer;
    private int duration;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setModel(SongModel songModel) {
        this.songModel = songModel;
    }

    /**
     * creates a song with the data from the input fields
     * creates the song in the database.
     */
    public void handleOK() {
        String title = textTitle.getText();
        String artist = textArtist.getText();
        String genre = textGenre.getText();
        int time = duration;
        String path = textFile.getText(); //??

        Song song = new Song(title, artist, genre, time, path);

        try {
            songModel.createSong(song); //Send the song down the layers to create it in the Database.
            songModel.search(""); //Refreshes the list shown to the user by simply searching an empty string.
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
        handleClose();
    }

    /**
     * closes the window
     */
    public void handleClose() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * takes the path from input fields and fills the input fields with metadata from file.
     */
    public void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add your song");

        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("Audio files", "*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(fileExtensions);
        File file = fileChooser.showOpenDialog((Stage) btnCancel.getScene().getWindow());
        textFile.setText(file.getAbsolutePath());

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        //MediaPlayer must be set on ready to get the duration
        getSongDuration(media);

        metaGetListener(media);
    }

    /**
     * waits for metadata and fills out fields in view
     * @param media
     */
    private void metaGetListener(Media media) {
        //Insert the metadata to the text fields once it has been loaded
        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            textTitle.setText((String)mediaPlayer.getMedia().getMetadata().get("title"));
            textArtist.setText((String)mediaPlayer.getMedia().getMetadata().get("artist"));
            textGenre.setText((String)mediaPlayer.getMedia().getMetadata().get("category"));
        });
    }

    /**
     * gets the duration in seconds and turns it to minutes and seconds
     * @param media
     */
    private void getSongDuration(Media media) {
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = (int) media.getDuration().toSeconds();
            }
        });
    }
}
