package GUI.Controllers;

import GUI.Models.SongModel;
import GUI.Util.ErrorDisplayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SongCreateController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private TextField textTitle, textArtist, textGenre, textTime, textFile;
    private SongModel songModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setModel(SongModel songModel) {
        this.songModel = songModel;
    }

    public void handleOK() {
        //String title = textTitle.getText();
        //String artist = textArtist.getText();
        //String genre = textGenre.getText();
        //int time = Integer.parseInt(textTime.getText());
        //String path = textFile.getText(); //??
        //int id = ??;
        //Song song = new Song(title, artist, genre, time, path, id);

        try {
            //songModel.createSong(song); //Send the song down the layers to create it in the Database.
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
        fileChooser.showOpenDialog((Stage) btnCancel.getScene().getWindow());
        textFile.setText(fileChooser.getInitialFileName());
    }
}
