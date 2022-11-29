package GUI.Controllers;

import BE.Song;
import GUI.Models.MainModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public TextField txtSongSearch;
    public TableView<Song> lstSongs;
    public TableColumn<Song, String> titleColum, artistColum, genreColum;
    public TableColumn<Song, Integer> timeColum;


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


        txtSongSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                songModel.search(newValue);
            } catch (Exception e) {
                e.printStackTrace();
                // TO DO
                // Create a displayError method to show errors to the user?
            }
        });


    }

    /**
     * Change to previous song
     * @param actionEvent, an action of pressing the button
     */
    public void handlePlayerPrevious(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Change to next song
     * @param actionEvent, an action of pressing the button
     */
    public void handlePlayerNext(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Play or pause the current song
     * @param actionEvent, an action of pressing the button
     */
    public void handlePlayerPlayPause(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Search for a song in the library
     * @param actionEvent, an action of pressing the button
     */
    public void handleSearch(ActionEvent actionEvent) {
        // Obsolete?
        // It already searches as soon as text is entered, using the listener in our initialize method.
        try {
            songModel.search(txtSongSearch.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new playlist
     * @param actionEvent, an action of pressing the button
     */
    public void handlePlaylistNew(ActionEvent actionEvent) {
    }

    /**
     * Edit a playlist
     * @param actionEvent, an action of pressing the button
     */
    public void handlePlaylistEdit(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Delete a playlist
     * @param actionEvent, an action of pressing the button
     */
    public void handlePlaylistDelete(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Add a new song from the library to the Songs on Playlist editor
     * @param actionEvent, an action of pressing the button
     */
    public void handleSOPAdd(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Move the song up in the order of Songs on Playlist
     * @param actionEvent, an action of pressing the button
     */
    public void handleSOPMoveUp(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Move the song down in the order of Songs on Playlist
     * @param actionEvent, an action of pressing the button
     */
    public void handleSOPMoveDown(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Remove a song from the Songs on Playlist editor
     * @param actionEvent, an action of pressing the button
     */
    public void handleSOPDelete(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Add a new song to the library
     * @param actionEvent, an action of pressing the button
     */
    public void handleSongNew(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Edit a song in the library
     * @param actionEvent, an action of pressing the button
     */
    public void handleSongEdit(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Delete a song from the library
     * @param actionEvent, an action of pressing the button
     */
    public void handleSongDelete(ActionEvent actionEvent) {
        //TO DO
    }

    /**
     * Close the application
     * @param actionEvent, an action of pressing the button
     */
    public void handleClose(ActionEvent actionEvent) {
        //TO DO
    }
}
