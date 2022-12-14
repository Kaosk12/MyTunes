package GUI.Controllers;

import BE.PlayList;
import BE.Song;
import GUI.Models.MediaModel;
import GUI.Models.PlayListModel;
import GUI.Models.SongModel;
import GUI.Util.ConfirmDelete;
import GUI.Util.ErrorDisplayer;
import GUI.Util.TimeCellFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private ArrayList<String> style = new ArrayList<String>();
    public static String currentStyle = "/GUI/CSS/DarkMode.css";
    private int styleCount = 0;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private GridPane app;
    @FXML
    private ImageView imageAlbumCover;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider timeSlider;
    @FXML
    private Label labelPlayerCounter;
    @FXML
    private Label labelPlayerTitle, labelPlayerArtist, labelPlayerDuration, labelCurrentSongDuration;
    @FXML
    private TextField txtSongSearch;
    @FXML
    private TableView<Song> lstSongs;
    @FXML
    private TableColumn<Song, String> titleColum, artistColum, genreColum;
    @FXML
    private TableColumn<Song, Integer> timeColum;

    //Buttons by section
    @FXML
    private Button btnSearchClear, btnSearch, btnClose;
    @FXML
    private Button btnSongEdit, btnSongDelete;
    @FXML
    private Button btnSOPAdd, btnSOPDelete, btnSOPMoveUp, btnSOPMoveDown;
    @FXML
    private Button btnPlayerPlayPause, btnSettings;
    @FXML
    private ToggleButton volumeButton, btnShuffleAtEnd, btnRepeatAtEnd;
    @FXML
    private Button btnEditPlayList, btnDeletePlayList;

    //PlayList variables
    @FXML
    private TableColumn<PlayList, String> clmPlayListName;
    @FXML
    private TableColumn<PlayList, Integer> clmPlayListSongs;
    @FXML
    private TableColumn<PlayList, Integer> clmPlayListTime;
    @FXML
    private TableView<PlayList> tbvPlayLists;
    @FXML
    private ListView<Song> tbvSongsInPlayList;

    //Models
    private SongModel songModel;
    private PlayListModel playlistModel;
    private MediaModel mediaModel;

    public MainController(){
        try {
            songModel = new SongModel();
            playlistModel = new PlayListModel();
            mediaModel = new MediaModel(songModel.getObservableSongs().get(0));//sets the first song in table view as loaded in mediaPlayer
            songModel.setPlayListModel(playlistModel);
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize tables & volume slider
        initializeSongTbv();
        initializePlaylistTbv();
        initializeVolumeSlider();
        initializeStyleSheets();

        //Disable the clear button
        setSearchButtons(true);

        addSongSearchListener();

        //Disable the Edit & Delete Song buttons.
        setSongManipulatingButtons(true);

        addSongSelectionListener();

        //Disable the Edit & Delete button for Playlists and the Add to SoP button.
        setPlaylistManipulatingButtons(true);

        addPlaylistSelectionListener();
        songInPlaylistListener();

        //Disable the Move Up/Down buttons for Song on Playlist.
        setSongsOnPlaylistManipulatingButtons(true);

        addVolumeSliderListener();
        addTimeSliderListener();
        addMoveWindowListener();
    }

    private void initializeStyleSheets() {
        style.add("/GUI/CSS/DarkMode.css");
        style.add("/GUI/CSS/LightMode.css");
        style.add("/GUI/CSS/FourColors.css");
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

    /**
     * Sets the current time on the song to
     * match the time slider's position.
     */
    private void addTimeSliderListener() {
        timeSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double currentTime = mediaModel.getMediaPlayer().getCurrentTime().toSeconds();
                double timeSliderValue = timeSlider.getValue();
                // Makes sure that the user can't seek under 0.5 seconds.
                double minSeek = 0.5;
                if (Math.abs(currentTime - timeSliderValue) > minSeek) {
                    mediaModel.getMediaPlayer().seek(Duration.seconds(timeSliderValue));
                }
            }
        });
    }


    /**
     * sets volume in mediaPlayer according to the volume slider.
     */
    private void addVolumeSliderListener() {
        volumeSlider.valueProperty().addListener(observable -> {
            mediaModel.getMediaPlayer().setVolume(volumeSlider.getValue()/100);
            mediaModel.setVolume(volumeSlider.getValue()/100);
        });
    }

    /**
     * sets the volume slider to max
     */
    private void initializeVolumeSlider() {
        volumeSlider.setValue(100);
    }

    /**
     * Allows for enabling and disabling the buttons
     * for searching in the song list
     */
    private void setSearchButtons(boolean disable) {
        btnSearchClear.setDisable(disable);
        btnSearch.setDisable(disable);
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
     * Allows for enabling and disabling the buttons
     * for manipulating songs on a playlist.
     * @param disable true to disable the buttons, false to enable.
     */
    private void setSongsOnPlaylistManipulatingButtons(boolean disable) {
        btnSOPDelete.setDisable(disable);
        btnSOPMoveUp.setDisable(disable);
        btnSOPMoveDown.setDisable(disable);
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
                playlistModel.setSelectedPlaylist(newValue);
            }
            else {
                setPlaylistManipulatingButtons(true);
                playlistModel.setSelectedPlaylist(null);
                // Clears the Songs in Playlist table when playlist is de-selected
                playlistModel.getObservableSongsInPlayList(PlayListModel.getSelectedPlaylist());
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

                mediaModel.setIsPlaylistSelected(false);
                tbvSongsInPlayList.getSelectionModel().clearSelection();
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
                setSearchButtons(false);
            } else {
                setSearchButtons(true);
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
        timeColum.setCellFactory(new TimeCellFactory<>());
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
        clmPlayListTime.setCellFactory(new TimeCellFactory<>());
    }

    /**
     * Add a listener to songs in playlists.
     */
    private void songInPlaylistListener(){
        tbvSongsInPlayList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
                if (newValue != null) {
                    setSongsOnPlaylistManipulatingButtons(false);
                    lstSongs.getSelectionModel().clearSelection();
                    PlayListModel.setSelectedSOP(newValue);
                    mediaModel.setIsPlaylistSelected(true);
                }
                if (newValue == null) {
                    PlayListModel.setSelectedSOP(null);
                    setSongsOnPlaylistManipulatingButtons(true);
                }
            }
        });
    }

    /**
     * Change to the previous song.
     * to do make a class that can see how far in the song you are,
     * if over 10% start song over, otherwise skip to previous.
     */
    public void handlePlayerPrevious() {
        //checks if the player is over 5 seconds into the song.
        if(mediaModel.getCurrentTime().lessThan(Duration.millis(5000))) {
            //checks if there is a playlist selected,
            if (mediaModel.getIsPlaylistSelected()){
                //if the top row is selected it will jump to the bottom.
                if(tbvSongsInPlayList.getSelectionModel().getSelectedIndex() == 0){
                    tbvSongsInPlayList.getSelectionModel().selectLast();
                }
                else {//selects the previous row
                    tbvSongsInPlayList.getSelectionModel().selectPrevious();
                }
                //plays the selected media
                mediaModel.playMedia(tbvSongsInPlayList.getSelectionModel().getSelectedItem());
            }else {
                //if the top row is selected it will jump to the bottom.
                if(lstSongs.getSelectionModel().getSelectedIndex() == 0){
                    lstSongs.getSelectionModel().selectLast();
                }
                else {//selects the previous row
                    lstSongs.getSelectionModel().selectPrevious();
                }
                //plays the selected media
                mediaModel.playMedia(lstSongs.getSelectionModel().getSelectedItem());
            }
        }
        else{
            mediaModel.restartSong();
        }
        setPlayerLabels();
        endOfSongListener();
    }

    /**
     * Change to the next song
     * jumps to the top if there is no more rows on the table
     */
    public void handlePlayerNext() {
        if (mediaModel.isShuffleBtnSelected()) {
            shuffleSongs();
        }
        else if (mediaModel.getIsPlaylistSelected()){

            //checks if there is a next in the list if not it will go to the top,else it picks the next colum
            if(tbvSongsInPlayList.getItems().size() <= tbvSongsInPlayList.getSelectionModel().getSelectedIndex() + 1){
                tbvSongsInPlayList.getSelectionModel().selectFirst();
            }
            else {
                tbvSongsInPlayList.getSelectionModel().selectNext();
            }
            //plays the selected media
            mediaModel.playMedia(tbvSongsInPlayList.getSelectionModel().getSelectedItem());
        }
        else {
            //checks if there is a next in the list if not it will go to the top,else it picks the next colum
            if(lstSongs.getItems().size() <= lstSongs.getSelectionModel().getSelectedIndex() + 1){
                lstSongs.getSelectionModel().selectFirst();
            }
            else {
                lstSongs.getSelectionModel().selectNext();
            }

            //plays the selected media
            mediaModel.playMedia(lstSongs.getSelectionModel().getSelectedItem());
        }

        setPlayerLabels();
        endOfSongListener();
    }

    /**
     * Play or pause the current song
     * set txt for song playing
     */
    public void handlePlayerPlayPause() {
        if(mediaModel.getIsPlaylistSelected()){
            mediaModel.playMedia(PlayListModel.getSelectedSOP());
        }else if(lstSongs.getSelectionModel().getSelectedItem() != null) {//sets the selectedSong from song table.
            mediaModel.playMedia(SongModel.getSelectedSong());
        }else {
            lstSongs.getSelectionModel().selectFirst();
            mediaModel.playMedia(lstSongs.getItems().get(0));
        }

        if (mediaModel.isPlaying()) {
            btnPlayerPlayPause.setText("???");
            beginTimer();
        } else {
            btnPlayerPlayPause.setText("???");
        }

        endOfSongListener();
        setPlayerLabels();
    }

    /**
     * Sets the player's labels to the current song's title, artist and duration
     */
    private void setPlayerLabels() {
        Song song = mediaModel.getSelectedSong();
        labelPlayerTitle.setText(song.getTitle());
        labelPlayerArtist.setText(song.getArtist());
        int duration = song.getTime();
        int m = duration/60;
        int s = duration%60;
        String mins = String.format("%02d", m);
        String secs = String.format("%02d", s);
        String time = mins + ":" + secs;
        labelPlayerDuration.setText(time);

        if (song.getCoverPath() != null) {
            Path coverPath = Paths.get(song.getCoverPath());
            Image cover = new Image(coverPath.toUri().toString());

            imageAlbumCover.setImage(cover);
        }
    }

    /**
     * ads a listener for when the song is over
     * if the repeat button is clicked the song will start over
     * if the shuffle mode is clicked a random song from the selected table will start playing
     * else it will play next song at end.
     */
    public void endOfSongListener(){
        //calls the handlePlayerNext method when the song is finished.
        mediaModel.getMediaPlayer().setOnEndOfMedia(() -> {
            //if the repeat button is true the song will restart
            if (mediaModel.isRepeatBtnSelected()){
                mediaModel.restartSong();
            }
            //if shuffle mode is selected a random son will play from the selected table.
            else if (mediaModel.isShuffleBtnSelected()) {
                shuffleSongs();
            }
            //play the next song if neither the repeat nor shuffle button is clicked
            else {
                handlePlayerNext();
            }
            //adds listener for when the song is finished
            endOfSongListener();
            beginTimer();
        });
        setPlayerLabels();
    }

    /**
     * begins a timer that updates the time slider and sets current time in song
     */
    public void beginTimer() {
        //sets a timer that updates time-slider each second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            if (!timeSlider.isValueChanging()) {
                //sets the max index of time slider to song duration
                timeSlider.setMax(mediaModel.getMediaPlayer().getTotalDuration().toSeconds());
                //updates the time slider to current time in song
                double current = mediaModel.getMediaPlayer().getCurrentTime().toSeconds();
                timeSlider.setValue(current);

                //display the current time in song
                int duration = (int) mediaModel.getMediaPlayer().getCurrentTime().toSeconds();
                labelPlayerCounter.setText(timeWriter(duration));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * returns seconds formatted as min:seconds
     * @param seconds to convert
     * @return formatted string of min:seconds
     */
    private String timeWriter(int seconds){
        int m = seconds/60;
        int s = seconds%60;
        String mins = String.format("%02d", m);
        String secs = String.format("%02d", s);
        String time = mins + ":" + secs;
        return time;
    }

    private void shuffleSongs() {
        Random random = new Random();

        if(mediaModel.getIsPlaylistSelected()){
            int x = random.nextInt(tbvSongsInPlayList.getItems().size());
            mediaModel.playMedia(tbvSongsInPlayList.getItems().get(x));
            tbvSongsInPlayList.getSelectionModel().select(x);
        }else{
            int x = random.nextInt(lstSongs.getItems().size());
            mediaModel.playMedia(lstSongs.getItems().get(x));
            lstSongs.getSelectionModel().select(x);
        }
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
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getScene().getStylesheets().add(getClass().getResource(currentStyle).toExternalForm());
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
        stage.initStyle(StageStyle.UNDECORATED);
        //Add styling with CSS
        stage.getScene().getStylesheets().add(getClass().getResource(currentStyle).toExternalForm());
        stage.show();
        //Tableview to be refreshed
        PlaylistController controller = loader.getController();
        controller.setTbvPlayLists(tbvPlayLists);
        //Set the SongUpdateController's model to be the same songModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        controller.setModel(playlistModel);
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
            if (PlayListModel.getSelectedPlaylist() != null) {
                playlistModel.addSongToPlayList();
            }
            //if no playlist have been selected, then asks the user to create one.
            else {
                newPlaylistWithSOP();
            }
            //updates the song amount
            tbvPlayLists.refresh();
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    /**
     * creates a playlist and adds a song to it.
     */
    public void newPlaylistWithSOP(){
        String header = "No playlist have been selected!";
        String content = "Do you want to create a new playlist?";
        if (ConfirmDelete.confirm(header, content)){
            handlePlaylistNew();
            PlaylistController.setAddSong(true);
        }
    }

    /**
     * Move the song up in the order of Songs on Playlist
     */
    public void handleSOPMoveUp() {
        try {
            playlistModel.moveSOP(true);
            tbvSongsInPlayList.refresh();
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
    }

    /**
     * Move the song down in the order of Songs on Playlist
     */
    public void handleSOPMoveDown() {
        try {
            playlistModel.moveSOP(false);
            tbvSongsInPlayList.refresh();
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
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
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getScene().getStylesheets().add(getClass().getResource(currentStyle).toExternalForm());
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
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getScene().getStylesheets().add(getClass().getResource(currentStyle).toExternalForm());

        //Set the SongUpdateController's model to be the same songModel as the main window.
        //This should help show any changes in the main window once they are confirmed.
        SongUpdateController controller = loader.getController();
        controller.setModel(songModel);
        stage.showAndWait();
        tbvSongsInPlayList.refresh();
    }

    /**
     * Delete a song from the library
     */
    public void handleSongDelete() {
        try {
            songModel.setSelectedSong(lstSongs.getSelectionModel().getSelectedItem());

            String header = "Are you sure you want to delete this song?";
            String content = SongModel.getSelectedSong().getTitle();
            boolean deleteSong = ConfirmDelete.confirm(header, content);

            if (deleteSong) {
                songModel.deleteSong(SongModel.getSelectedSong());
            }
        }
        catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }
        tbvSongsInPlayList.refresh();
        tbvPlayLists.refresh();
        handleClear();
    }

    /**
     * Close the application
     */
    public void handleClose() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    /**
     * Allows searching by pressing Enter (instead of using the ????-button).
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

    /**
     * sets the boolean in mediaModel isRepeatSelected to true if the button is clicked.
     * changes the color of the button to green if selected, and changes the shuffleAtEnd to blue if selected
     * changes back to blue if pushed again, while sets boolean isRepeatSelected false
     * @param actionEvent
     */
    public void handleRepeatAtEnd(ActionEvent actionEvent) {
        if(mediaModel.isRepeatBtnSelected()){
            mediaModel.setRepeatBtnSelected(false);
        }else {
            mediaModel.setRepeatBtnSelected(true);

            mediaModel.setShuffleBtnSelected(false);
            btnShuffleAtEnd.setSelected(false);
        }
    }

    /**
     * sets the boolean in mediaModel isShuffleSelected to true if the button is clicked.
     * changes the color of the button to green if selected, and changes the repeatAtEnd button to blue if selected
     * changes back to blue if pushed again, while sets boolean isShuffleSelected false
     * @param actionEvent
     */
    public void handleShuffleAtEnd(ActionEvent actionEvent) {
        if(mediaModel.isShuffleBtnSelected()){
            mediaModel.setShuffleBtnSelected(false);
        }else {
            mediaModel.setShuffleBtnSelected(true);
            mediaModel.setRepeatBtnSelected(false);
            btnRepeatAtEnd.setSelected(false);
        }
    }

    /**
     * checks if any song has been double-clicked, then start playing it
     * @param mouseEvent
     */
    public void handleSongDoubleClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            //checks if there has been 2 clicks
            if(mouseEvent.getClickCount() == 2){
                handlePlayerPlayPause();
            }
        }
    }

    /**
     * checks if the volume button is clicked
     * when clicked it will mute and change color, click again to unmute and change color back to normal
     * @param actionEvent
     */
    public void handleVolumeButton(ActionEvent actionEvent) {
        if(mediaModel.isMute()){
            volumeButton.setText("????");
            mediaModel.setMute(false);
        }else {
            volumeButton.setText("????");
            mediaModel.setMute(true);
        }
    }

    /**
     * removes old style and sets a new one when button is pressed
     */
    public void handleStyle() {
        //removes old sheet
        Scene scene = btnSettings.getScene();
        scene.getStylesheets().remove(currentStyle);

        if(styleCount >= style.size() -1){
            styleCount = 0;
        }else{
            styleCount++;
        }
        //sets the new styleSheet
        currentStyle = style.get(styleCount);
        scene.getStylesheets().add(currentStyle);
    }
}