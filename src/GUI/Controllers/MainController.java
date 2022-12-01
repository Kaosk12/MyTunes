package GUI.Controllers;

import BE.PlayList;
import BE.Song;
import GUI.Models.PlayListModel;
import GUI.Util.ErrorDisplayer;
import GUI.Models.SongModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainController implements Initializable {


    @FXML
    private TextField txtSongSearch;
    @FXML
    private TableView<Song> lstSongs;
    @FXML
    private TableColumn<Song, String> titleColum, artistColum, genreColum;
    @FXML
    private TableColumn<Song, Integer> timeColum;
    @FXML
    private Button btnSearchClear, btnSongEdit, btnSongDelete, btnSOPAdd;

    //PlayList variables
    public TableColumn<PlayList, String> clmPlayListName;
    public TableColumn<PlayList, Integer> clmPlayListSongs;
    public TableColumn<PlayList, String> clmPlayListTime;
    public TableView<PlayList> tbvPlayLists;
    public Button btnNewPlayList;
    public Button btnEditPlayList;
    public Button btnDeletePlayList;


    private SongModel songModel;
    private PlayListModel playlistModel;

    public MainController(){
        try {
            songModel = new SongModel();
            playlistModel = new PlayListModel();
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstSongs.setItems(songModel.getObservableSongs());

        titleColum.setCellValueFactory(new PropertyValueFactory<>("Title"));
        artistColum.setCellValueFactory(new PropertyValueFactory<>("Artist"));
        genreColum.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        timeColum.setCellValueFactory(new PropertyValueFactory<>("Time"));

        /**
         * sets the Cell value for the 3 cells in tableView for PlayList.
         * Its uses the name of the variable in the PlayList object from BE.
         */
        tbvPlayLists.setItems(playlistModel.getObservablePlayLists());
        clmPlayListName.setCellValueFactory(new PropertyValueFactory<>("Title"));
        clmPlayListSongs.setCellValueFactory(new PropertyValueFactory<>("SongAmount"));
        clmPlayListTime.setCellValueFactory(new PropertyValueFactory<>("Time"));


        /**
         * Search "real-time" as soon as text is entered.
         * Moved it to the handleSearch method instead, as it seems laggy using the listener.
         * In handleSearch it only searches when the button is pressed.


        txtSongSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                songModel.search(newValue);
            } catch (Exception e) {
                e.printStackTrace();
                // TO DO
                // Create a displayError method to show errors to the user?
            }
        });
         */

        //Disable the clear button
        btnSearchClear.setDisable(true);
        //Adding a listener, and enabling/disabling the clear button once text is entered or removed
        txtSongSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!txtSongSearch.getText().isEmpty()) {
                    btnSearchClear.setDisable(false);
                } else {
                    btnSearchClear.setDisable(true);
                }
            } catch (Exception e) {
                ErrorDisplayer.displayError(e);
            }
        });

        //Disable the Edit & Delete Song buttons and the Add to SoP button.
        btnSongEdit.setDisable(true);
        btnSongDelete.setDisable(true);
        btnSOPAdd.setDisable(true);

        //Adding a listener, and enabling/disabling the aforementioned buttons once a song is selected/deselected.
        lstSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                if (newValue != null) {
                    //
                    btnSongEdit.setDisable(false);
                    btnSongDelete.setDisable(false);
                    btnSOPAdd.setDisable(false);

                }
                if (newValue == null) {
                    btnSongEdit.setDisable(true);
                    btnSongDelete.setDisable(true);
                    btnSOPAdd.setDisable(true);
                }
            }
        });

    }
    /**
     * converts time to hours, minutes and seconds
     * @param timeInSeconds
     */
    private void convertTime(int timeInSeconds){
        int totalTime = timeInSeconds;
        long hour = TimeUnit.MINUTES.toHours(totalTime);

        long minute  = TimeUnit.SECONDS.toMinutes(totalTime) - (TimeUnit.MINUTES.toHours(totalTime) * 60);

        long second = totalTime -(TimeUnit.SECONDS.toMinutes(totalTime)*60);

        String convertedTime = "Hours " + hour + " Mins " + minute + " Sec " + second;
    }

    /**
     * Change to previous song
     */
    public void handlePlayerPrevious() {
        //TO DO
    }

    /**
     * Change to next song
     */
    public void handlePlayerNext() {
        //TO DO
    }

    /**
     * Play or pause the current song
     */
    public void handlePlayerPlayPause() {
        //TO DO
    }

    /**
     * Search for a song in the library
     */
    public void handleSearch() {
        // Can be switched to search as soon as text is entered, using the out-commented listener in our initialize method.
        try {
            songModel.search(txtSongSearch.getText());
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    /**
     * Create a new playlist
     */
    public void handlePlaylistNew() {
    }

    /**
     * Edit a playlist
     */
    public void handlePlaylistEdit() {
        //TO DO
    }

    /**
     * Delete a playlist
     */
    public void handlePlaylistDelete() {
        //TO DO
    }

    /**
     * Add a new song from the library to the Songs on Playlist editor
     */
    public void handleSOPAdd() {
        //TO DO
    }

    /**
     * Move the song up in the order of Songs on Playlist
     */
    public void handleSOPMoveUp() {
        //TO DO
    }

    /**
     * Move the song down in the order of Songs on Playlist
     */
    public void handleSOPMoveDown() {
        //TO DO
    }

    /**
     * Remove a song from the Songs on Playlist editor
     */
    public void handleSOPDelete() {
        //TO DO
    }

    /**
     * Add a new song to the library
     */
    public void handleSongNew() {
        //TO DO
    }

    /**
     * Open up a new window to edit the title, artist or genre of a song.
     */
    public void handleSongEdit() throws IOException {
        //Save information about the selected song in the songModel.
        Song song = lstSongs.getSelectionModel().getSelectedItem();
        songModel.setSelectedSong(song);

        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/SongUpdateView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Edit");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        //Set the SongUpdateController's model to be the same songModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        SongUpdateController controller = loader.getController();
        controller.setSongModel(songModel);
    }

    /**
     * Delete a song from the library
     */
    public void handleSongDelete() {
        //TO DO
    }

    /**
     * Close the application
     */
    public void handleClose() {
        //TO DO
    }

    /**
     * Allows searching by pressing Enter (instead of using the 🔍-button).
     * @param keyEvent, a key-press
     */
    public void handleEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleSearch();
        }
    }

    /**
     * Clear the search input from the txtSongSearch textfield and searches (for an empty string) to show all songs again.
     */
    public void handleClear() {
        txtSongSearch.clear();
        handleSearch();
    }
}
