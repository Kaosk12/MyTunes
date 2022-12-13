package GUI.Controllers;

import BE.Song;
import GUI.Models.AlbumCoverModel;
import GUI.Models.SongModel;
import GUI.Util.ErrorDisplayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SongUpdateController implements Initializable {
    private SongModel songModel;
    private Song song;
    private AlbumCoverModel albumCoverModel;
    private File albumCover;
    @FXML
    private TextField textTitle, textArtist, textGenre, textImage;
    @FXML
    private Button btnOK, btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        song = SongModel.getSelectedSong();
        textTitle.setText(song.getTitle());
        textArtist.setText(song.getArtist());
        textGenre.setText(song.getGenre());

        addTitleListener();
        addArtistListener();
    }

    /**
     * Adds a listener to the artist property.
     * If it is empty, then it disables the "ok" button.
     */
    private void addArtistListener() {
        //Adding a listener, and enabling/disabling the OK button if artist is empty
        textArtist.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isArtistEmpty()) {
                btnOK.setDisable(true);
            }
            else if (!isArtistEmpty() && !isTitleEmpty()) {
                btnOK.setDisable(false);
            }
        });
    }

    /**
     * Adds a listener to the title property.
     * If it is empty, then it disables the "ok" button.
     */
    private void addTitleListener() {
        textTitle.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isTitleEmpty()) {
                btnOK.setDisable(true);
            }
            else if (!isTitleEmpty() && !isArtistEmpty()) {
                btnOK.setDisable(false);
            }
        });
    }

    private boolean isTitleEmpty() {
        return textTitle.getText().trim().isEmpty();
    }

    private boolean isArtistEmpty() {
        return textArtist.getText().trim().isEmpty();
    }

    /**
     * Set the model to the same model used in MainView.
     * @param songModel, a songModel object
     */
    public void setModel(SongModel songModel) {
        this.songModel = songModel;
    }

    /**
     * Changes the title, artist, and genre of the song to the new input once the user presses OK.
     * closes the window 
     */
    public void handleOK() {
        if (isInputMissing()) {
            return;
        }

        //Set the title, artist, and genre to new input
        song.setTitle(textTitle.getText());
        song.setArtist(textArtist.getText());
        song.setGenre(textGenre.getText());


        try {
            songModel.updateSong(song); //Send the song down the layers to update it in the Database.
            songModel.search(""); //Refreshes the list shown to the user by simply searching an empty string.
            albumCoverModel.updateCover(song.getId(), albumCover);
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }

        handleClose();
    }

    /**
     * Loads an album cover image into a file
     */
    public void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add album cover art");

        FileChooser.ExtensionFilter imageExtensions = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");

        fileChooser.getExtensionFilters().add(imageExtensions);

        albumCover = fileChooser.showOpenDialog((Stage) btnCancel.getScene().getWindow());
        textImage.setText(albumCover.getAbsolutePath());
    }
    public void handleDeleteImage() {
        //TODO
    }

    /**
     * Double check that title and artist is added.
     * (not-null values for the database).
     */
    private boolean isInputMissing() {
        if (textTitle.getText().trim().isEmpty()) {
            ErrorDisplayer.displayError(new Exception("Title can not be empty"));
            return true;
        }

        if (textArtist.getText().trim().isEmpty()) {
            ErrorDisplayer.displayError(new Exception("Artist can not be empty"));
            return true;
        }

        if (textGenre.getText().trim().isEmpty()) {
            ErrorDisplayer.displayError(new Exception("Genre can not be empty"));
            return true;
        }
        return false;
    }

    /**
     * Allows updating by pressing Enter (instead of using the OK-button).
     * @param keyEvent, a key-press
     */
    public void handleEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleOK();
        }
    }

    /***
     * closes the window
     */
    public void handleClose() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
