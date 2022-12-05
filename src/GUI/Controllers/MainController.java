package GUI.Controllers;

import BE.PlayList;
import BE.Song;
import GUI.Models.MediaModel;
import GUI.Util.ConfirmDelete;
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
import javafx.scene.control.*;
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


    public Label btnPlayerPlaying;
    public Button btnSOPDelete;
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
    @FXML
    public TableColumn<PlayList, String> clmPlayListName;
    @FXML
    public TableColumn<PlayList, Integer> clmPlayListSongs;
    @FXML
    public TableColumn<PlayList, String> clmPlayListTime;
    @FXML
    public TableView<PlayList> tbvPlayLists;
    @FXML
    public ListView<Song> tbvSongsInPlayList;
    @FXML
    public Button btnNewPlayList;
    @FXML
    public Button btnEditPlayList;
    @FXML
    public Button btnDeletePlayList;




    private SongModel songModel;
    private PlayListModel playlistModel;
    private MediaModel mediaModel;

    private Song chosenSong;

    public MainController(){
        try {
            songModel = new SongModel();
            playlistModel = new PlayListModel();
            mediaModel = new MediaModel();
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Adds a listener to the songs in a playlist.
        songInPlaylistListener();

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
                    songModel.setSelectedSong(newValue);

                }
                if (newValue == null) {
                    btnSongEdit.setDisable(true);
                    btnSongDelete.setDisable(true);
                    btnSOPAdd.setDisable(true);
                }
            }
        });
        //Disable the Edit & Delete button for Playlists.
        btnDeletePlayList.setDisable(true);
        btnEditPlayList.setDisable(true);
        //Adding a listener, and enabling/disabling the buttons when selected.
        //It also retreives the object clicked on and uses it to show songs in the Playlist clicked on.
        tbvPlayLists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PlayList>() {
            @Override
            public void changed(ObservableValue<? extends PlayList> observable, PlayList oldValue, PlayList newValue) {
                if (newValue != null) {
                    btnDeletePlayList.setDisable(false);
                    btnEditPlayList.setDisable(false);
                    tbvSongsInPlayList.setItems(playlistModel.getObservableSongsInPlayList(newValue));
                    //saves last selected playlist in PlayListModel
                    playlistModel.setSelectedPlaylist(newValue);
                }
                else {
                    btnDeletePlayList.setDisable(true);
                    btnEditPlayList.setDisable(true);
                }
            }
        });

    }

    private void songInPlaylistListener(){
        btnSOPDelete.setDisable(true);
        tbvSongsInPlayList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                if (newValue != null) {
                    btnSOPDelete.setDisable(false);
                    PlayListModel.setSelectedSOP(newValue);
                }
                if (newValue == null) {
                    btnSOPDelete.setDisable(true);

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
     * to do make a class that can see how far in the song you are, if over 10% strat song over elles skip to previous.
     */
    public void handlePlayerPrevious() {
        lstSongs.getSelectionModel().selectPrevious();
        chosenSong = lstSongs.getSelectionModel().getSelectedItem();
        mediaModel.previousSong(chosenSong);
        btnPlayerPlaying.setText(chosenSong.getTitle());
    }

    /**
     * Change to next song
     */
    public void handlePlayerNext() {
        lstSongs.getSelectionModel().selectNext();
        chosenSong = lstSongs.getSelectionModel().getSelectedItem();
        mediaModel.skipSong(chosenSong);
        btnPlayerPlaying.setText(chosenSong.getTitle());
    }

    /**
     * Play or pause the current song
     */
    public void handlePlayerPlayPause() {
        chosenSong = lstSongs.getSelectionModel().getSelectedItem();
        mediaModel.playMedia(chosenSong);
        btnPlayerPlaying.setText(chosenSong.getTitle());
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
        //Clear information about the selected playlist in the playListModel to make the input field empty for a new playlist.
        playlistModel.setSelectedPlaylist(null);

        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/PlaylistView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            ErrorDisplayer.displayError(new Exception("Failed to open playlist creator", e));
        }
        Stage stage = new Stage();
        stage.setTitle("Add new playlist");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        //Set the PlaylistController's model to be the same PlayListModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        PlaylistController controller = loader.getController();
        controller.setModel(playlistModel);

        //Move stage.show() down here, use showAndWait() instead, and clear+re-add all observable PlayLists to update the list?
    }

    /**
     * Edit a playlist
     */
    public void handlePlaylistEdit() {
        //Save information about the selected playlist in the playListModel.
        PlayList playList = tbvPlayLists.getSelectionModel().getSelectedItem();
        playlistModel.setSelectedPlaylist(playList);

        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/PlaylistView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            ErrorDisplayer.displayError(new Exception("Failed to open playlist editor", e));
        }
        Stage stage = new Stage();
        stage.setTitle("Edit playlist name");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        //Set the PlaylistController's model to be the same PlayListModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        PlaylistController controller = loader.getController();
        controller.setModel(playlistModel);

        //Move stage.show() down here, use showAndWait() instead, and clear+re-add all observable PlayLists to update the list?
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
        try {
            playlistModel.addSongToPlayList();
            //updates the song amount
            tbvPlayLists.refresh();
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
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
        try {
            //Deletes in the DAL
            playlistModel.deleteSOP();
            //Updates the GUI.
            tbvPlayLists.refresh();
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    /**
     * Add a new song to the library
     */
    public void handleSongNew() {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/SongCreateView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            ErrorDisplayer.displayError(new Exception("Failed to open song creator", e));
        }
        Stage stage = new Stage();
        stage.setTitle("Add new song");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        //Set the SongUpdateController's model to be the same songModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        SongCreateController controller = loader.getController();
        controller.setModel(songModel);
    }

    /**
     * Open up a new window to edit the title, artist or genre of a song.
     */
    public void handleSongEdit() {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/SongUpdateView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            ErrorDisplayer.displayError(new Exception("Failed to open song editor", e));
        }
        Stage stage = new Stage();
        stage.setTitle("Edit");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        //Set the SongUpdateController's model to be the same songModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        SongUpdateController controller = loader.getController();
        controller.setModel(songModel);
    }

    /**
     * Delete a song from the library
     */
    public void handleSongDelete() {
        try {
            chosenSong = lstSongs.getSelectionModel().getSelectedItem();
            String header = "Are you sure you want to delete this song?";
            String content = chosenSong.getTitle();
            boolean deleteSong = ConfirmDelete.confirm(header, content);

            if (deleteSong) {
                songModel.deleteSong(chosenSong);
            }
        }
        catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
        handleSearch();
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
