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


    @FXML
    private Label btnPlayerPlaying;
    @FXML
    private Button btnSOPDelete;
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
    private TableColumn<PlayList, String> clmPlayListName;
    @FXML
    private TableColumn<PlayList, Integer> clmPlayListSongs;
    @FXML
    private TableColumn<PlayList, String> clmPlayListTime;
    @FXML
    private TableView<PlayList> tbvPlayLists;
    @FXML
    private ListView<Song> tbvSongsInPlayList;
    @FXML
    private Button btnNewPlayList;
    @FXML
    private Button btnEditPlayList;
    @FXML
    private Button btnDeletePlayList;

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
        initializeSongTbv();
        initializePlaylistTbv();

        //Disable the clear button
        btnSearchClear.setDisable(true);

        addSongSearchListener();

        //Disable the Edit & Delete Song buttons and the Add to SoP button.
        setSongManipulatingButtons(true);

        addSongSelectionListener();

        //Disable the Edit & Delete button for Playlists.
        setPlaylistManipulatingButtons(true);

        addPlaylistSelectionListener();
    }

    /**
     * Allows for enabling and disabling the buttons
     * for manipulating playlists as a group.
     * @param disable true to disable the buttons, false to enable.
     */
    private void setPlaylistManipulatingButtons(boolean disable) {
        btnDeletePlayList.setDisable(disable);
        btnEditPlayList.setDisable(disable);
    }

    /**
     * Allows for enabling and disabling the buttons
     * for manipulating songs as a group.
     * @param disable true to disable the buttons, false to enable.
     */
    private void setSongManipulatingButtons(boolean disable) {
        btnSongEdit.setDisable(disable);
        btnSongDelete.setDisable(disable);
        btnSOPAdd.setDisable(disable);
    }

    /**
     * Adds a listener for when the user selects a playlist.
     * It enables the buttons for playlist manipulation
     * if there is a selected playlist. It also sets the selected playlist in the playlist model.
     */
    private void addPlaylistSelectionListener() {
        tbvPlayLists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setPlaylistManipulatingButtons(false);

                // Displays the songs in this playlist.
                tbvSongsInPlayList.setItems(playlistModel.getObservableSongsInPlayList(newValue));

                playlistModel.setSelectedPlayList(newValue);
            }
            else {
                setPlaylistManipulatingButtons(true);
            }
        });
    }

    /**
     * Adds a listener for when the user selects a song.
     * It enables the buttons for song manipulation
     * if there is a selected song. It also sets the selected song in the song model.
     */
    private void addSongSelectionListener() {
        lstSongs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setSongManipulatingButtons(false);

                songModel.setSelectedSong(newValue);
            }
            else {
                setSongManipulatingButtons(true);
            }
        });
    }

    /**
     * Adds a listener to the text field for song searching.
     * If it is empty, then it disables the clear button for searching.
     */
    private void addSongSearchListener() {
        txtSongSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!txtSongSearch.getText().isEmpty()) {
                btnSearchClear.setDisable(false);
            } else {
                btnSearchClear.setDisable(true);
            }
        });
    }

    /**
     * Sets the cell value for the 4 cells in the tableview for songs.
     */
    private void initializeSongTbv() {
        lstSongs.setItems(songModel.getObservableSongs());

        titleColum.setCellValueFactory(new PropertyValueFactory<>("Title"));
        artistColum.setCellValueFactory(new PropertyValueFactory<>("Artist"));
        genreColum.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        timeColum.setCellValueFactory(new PropertyValueFactory<>("Time"));
    }

    /**
     * sets the Cell value for the 3 cells in tableView for PlayList.
     * Its uses the name of the variable in the PlayList object from BE.
     */
    private void initializePlaylistTbv() {
        tbvPlayLists.setItems(playlistModel.getObservablePlayLists());

        clmPlayListName.setCellValueFactory(new PropertyValueFactory<>("Title"));
        clmPlayListSongs.setCellValueFactory(new PropertyValueFactory<>("SongAmount"));
        clmPlayListTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
    }

    /**
     * converts time to hours, minutes and seconds
     * @param timeInSeconds The time to convert in seconds.
     */
    private void convertTime(int timeInSeconds){
        int totalTime = timeInSeconds;
        long hour = TimeUnit.MINUTES.toHours(totalTime);

        long minute  = TimeUnit.SECONDS.toMinutes(totalTime) - (TimeUnit.MINUTES.toHours(totalTime) * 60);

        long second = totalTime -(TimeUnit.SECONDS.toMinutes(totalTime)*60);

        String convertedTime = "Hours " + hour + " Mins " + minute + " Sec " + second;
    }

    /**
     * Change to the previous song.
     * to do make a class that can see how far in the song you are,
     * if over 10% start song over, otherwise skip to previous.
     */
    public void handlePlayerPrevious() {
        lstSongs.getSelectionModel().selectPrevious();

        chosenSong = lstSongs.getSelectionModel().getSelectedItem();

        mediaModel.previousSong(chosenSong);

        btnPlayerPlaying.setText(chosenSong.getTitle());
    }

    /**
     * Change to the next song
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
     * Search for a song in the list.
     */
    public void handleSearch() {
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
        controller.setTbvPlayLists(tbvPlayLists);
        controller.setCreateNewPlayList(true);
        //Set the SongUpdateController's model to be the same songModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        controller.setModel(playlistModel);

        //disables edit and delete playlist button.
        btnEditPlayList.setDisable(true);
        btnDeletePlayList.setDisable(true);
    }

    /**
     * Edit a playlist
     */
    public void handlePlaylistEdit() {
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
        //Tableview to be refreshed
        PlaylistController controller = loader.getController();
        controller.setTbvPlayLists(tbvPlayLists);
        //Set the SongUpdateController's model to be the same songModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        controller.setModel(playlistModel);

        //Move stage.show() down here, use showAndWait() instead, and clear+re-add all observable PlayLists to update the list?
    }

    /**
     * Delete a playlist
     */
    public void handlePlaylistDelete() {
        try {
            String header = "Are you sure you want to delete this playlist?";
            String content = PlayListModel.getSelectedPlaylist().getTitle();
            boolean deletePlayList = ConfirmDelete.confirm(header, content);
            if (deletePlayList){
                playlistModel.deletePlayList();
            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
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
        // TODO
    }

    /**
     * Move the song down in the order of Songs on Playlist
     */
    public void handleSOPMoveDown() {
        // TODO
    }

    /**
     * Remove a song from the Songs on Playlist editor
     */
    public void handleSOPDelete() {
        try {
            String header = "Are you sure you want to remove this song?";
            String content = PlayListModel.getSelectedSOP().getTitle();
            boolean deleteSOP = ConfirmDelete.confirm(header, content);
            if (deleteSOP){
                //Deletes in the DAL
                playlistModel.deleteSOP();
                //Updates the GUI.
                tbvPlayLists.refresh();
            }
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

        handleClear();
    }

    /**
     * Close the application
     */
    public void handleClose() {
        //TODO
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
     * Clear the search input from the txtSongSearch text field
     * and searches (for an empty string) to show all songs again.
     */
    public void handleClear() {
        txtSongSearch.clear();
        handleSearch();
    }
}
