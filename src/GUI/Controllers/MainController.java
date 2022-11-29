package GUI.Controllers;

import BE.Song;
import GUI.Models.MainModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public TextField txtSongSearch;
    public TableView<Song> lstSongs;
    public TableColumn<Song, String> titleColum, artistColum, genreColum;
    public TableColumn<Song, Integer> timeColum;
    public Button btnSearch, btnSearchClear;


    private MainModel songModel;

    public MainController(){
        try {
            songModel = new MainModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstSongs.setItems(songModel.getObservableSongs());

        titleColum.setCellValueFactory(new PropertyValueFactory<>("Title"));
        artistColum.setCellValueFactory(new PropertyValueFactory<>("Authour"));
        genreColum.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        timeColum.setCellValueFactory(new PropertyValueFactory<>("Time"));


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
                e.printStackTrace();
                // To do: Create a displayError method to show errors to the user?
            }
        });


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
            throw new RuntimeException(e);
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
     * Edit a song in the library
     */
    public void handleSongEdit() {
        //TO DO
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
