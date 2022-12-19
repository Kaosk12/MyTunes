package GUI.Controllers;

import BE.Song;
import GUI.Models.SongModel;
import GUI.Util.ErrorDisplayer;
import javafx.collections.MapChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class SongCreateController implements Initializable {
    @FXML
    private GridPane app;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button btnCancel, btnOK, btnRemoveImage;
    @FXML
    private TextField textTitle, textArtist, textGenre, textFile, textImage;
    @FXML
    private ImageView imageCover;
    private SongModel songModel;

    private  MediaPlayer mediaPlayer;
    private int duration;
    private File albumCover;
    private File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Disable OK button until the 'not null' values are added
        btnOK.setDisable(true);

        //Add listeners for the 'not null' values
        addFileListener();
        addTitleListener();
        addArtistListener();
        addGenreListener();

        btnRemoveImage.setDisable(true);
        addImageListener();
        addMoveWindowListener();
    }

    private void addMoveWindowListener() {
        app.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        app.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                app.getScene().getWindow().setX(event.getScreenX() - xOffset);
                app.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }
        });
    }

    public void setModel(SongModel songModel) {
        this.songModel = songModel;
    }

    /**
     * creates a song with the data from the input fields
     * creates the song in the database.
     */
    public void handleOK() {
        if (isInputMissing()) {
            return;
        }

        String title = textTitle.getText();
        String artist = textArtist.getText();
        String genre = textGenre.getText();
        int time = duration;
        String path = textFile.getText();
        String coverPath = albumCover != null ? albumCover.getAbsolutePath() : "";

        Song song = new Song(title, artist, genre, time, path, coverPath);

        try {
            songModel.createSong(song); //Send the song down the layers to create it in the Database.
            songModel.search(""); //Refreshes the list shown to the user by simply searching an empty string.
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
        handleClose();
    }

    /**
     * takes the path from input fields and fills the input fields with metadata from file.
     */
    public void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add your song");

        FileChooser.ExtensionFilter audioExtensions = new FileChooser.ExtensionFilter("Audio files", "*.mp3", "*.wav", "*.aif", "*.aiff", "*.m4a");
        FileChooser.ExtensionFilter videoExtensions = new FileChooser.ExtensionFilter("Video files", "*.mp4", "*.m4v");

        fileChooser.getExtensionFilters().add(audioExtensions);
        fileChooser.getExtensionFilters().add(videoExtensions);

        file = fileChooser.showOpenDialog((Stage) btnCancel.getScene().getWindow());

        if(file != null) {
            textFile.setText(file.getAbsolutePath());

        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

            //MediaPlayer must be set on ready to get the duration
            getSongDuration(media);

            metaGetListener(media);
        }

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

        if (albumCover != null) {
            textImage.setText(albumCover.getAbsolutePath());

            Path coverPath = Paths.get(albumCover.getAbsolutePath());
            Image cover = new Image(coverPath.toUri().toString());
            imageCover.setImage(cover);
        }
    }

    public void handleRemoveImage() {
        if (!textImage.getText().isEmpty()) {
            albumCover = null;
            textImage.setText("");
            imageCover.setImage(null);
        }
    }

    /**
     * waits for metadata and fills out fields in view
     * @param media The media file to get the metadata from.
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
     * @param media The media file to get the duration of.
     */
    private void getSongDuration(Media media) {
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = (int) media.getDuration().toSeconds();
            }
        });
    }

    /**
     * Adds a listener to the file property.
     * If it is empty, then it disables the "ok" button.
     */
    private void addFileListener() {
        textFile.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isFileEmpty()) {
                btnOK.setDisable(true);
            }
            else if (!isTitleEmpty() && !isArtistEmpty() && !isGenreEmpty()) {
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
            else if (!isFileEmpty() && !isArtistEmpty() && !isGenreEmpty()) {
                btnOK.setDisable(false);
            }
        });
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
            else if (!isFileEmpty() && !isTitleEmpty() && !isGenreEmpty()) {
                btnOK.setDisable(false);
            }
        });
    }

    /**
     * Adds a listener to the genre property.
     * If it is empty, then it disables the "ok" button.
     */
    private void addGenreListener() {
        //Adding a listener, and enabling/disabling the OK button if artist is empty
        textGenre.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isGenreEmpty()) {
                btnOK.setDisable(true);
            }
            else if (!isFileEmpty() && !isTitleEmpty() && !isArtistEmpty()) {
                btnOK.setDisable(false);
            }
        });
    }

    /**
     * Adds a listener to the image property.
     * If it is empty, then it disables the "remove" button.
     */
    private void addImageListener() {
        textImage.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (isImageEmpty()) {
                btnRemoveImage.setDisable(true);
            }
            else {
                btnRemoveImage.setDisable(false);
            }
        });
    }

    /**
     * Double check that file, title, and artist is added.
     * (not-null values for the database).
     */
    private boolean isInputMissing() {
        if (isFileEmpty()) {
            ErrorDisplayer.displayError(new Exception("No song file is chosen"));
            return true;
        }

        if (isTitleEmpty()) {
            ErrorDisplayer.displayError(new Exception("Title can not be empty"));
            return true;
        }

        if (isArtistEmpty()) {
            ErrorDisplayer.displayError(new Exception("Artist can not be empty"));
            return true;
        }

        if (isGenreEmpty()) {
            ErrorDisplayer.displayError(new Exception("Genre can not be empty"));
            return true;
        }
        return false;
    }

    private boolean isFileEmpty() {
        return textFile.getText() == null || textFile.getText().trim().isEmpty();
    }
    private boolean isTitleEmpty() {
        return textTitle.getText() == null || textTitle.getText().trim().isEmpty();
    }
    private boolean isArtistEmpty() {
        return textArtist.getText() == null || textArtist.getText().trim().isEmpty();
    }
    private boolean isGenreEmpty() {
        return textGenre.getText() == null || textGenre.getText().trim().isEmpty();
    }
    private boolean isImageEmpty() {
        return textImage.getText() == null || textImage.getText().trim().isEmpty();
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

    /**
     * closes the window
     */
    public void handleClose() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
