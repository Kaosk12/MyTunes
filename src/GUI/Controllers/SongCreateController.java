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
    private TextField textTitle, textArtist, textGenre, textTime, textFile;
    private SongModel songModel;

    private  MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setModel(SongModel songModel) {
        this.songModel = songModel;
    }

    public void handleOK() {
        String title = textTitle.getText();
        String artist = textArtist.getText();
        String genre = textGenre.getText();
        int time = 0;
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

    public void handleClose() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

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
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                int duration = (int) media.getDuration().toSeconds();
                int m = duration/60;
                int s = duration%60;
                String mins = String.format("%02d", m);
                String secs = String.format("%02d", s);

                textTime.setText(mins+":"+secs);
            }
        });

        //Insert the metadata to the text fields once it has been loaded
        media.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            textTitle.setText((String)mediaPlayer.getMedia().getMetadata().get("title"));
            textArtist.setText((String)mediaPlayer.getMedia().getMetadata().get("artist"));
            textGenre.setText((String)mediaPlayer.getMedia().getMetadata().get("category"));
            //System.out.println(title);
        });
    }
}
