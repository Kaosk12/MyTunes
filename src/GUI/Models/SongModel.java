package GUI.Models;

import BE.Song;
import BLL.Interfaces.ISongManager;
import BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SongModel {

    private ObservableList<Song> songsInList;
    private ISongManager songManager;
    public static Song selectedSong;

    public ObservableList<Song> getObservableSongs() {
        return songsInList;
    }

    /**
     * sets the song mannager
     * gets a list of all songs and stores them in an observable list
     * @throws Exception
     */
    public SongModel() throws Exception {
        songManager = new SongManager();
        songsInList = FXCollections.observableArrayList();
        songsInList.addAll(songManager.getAllSongs());
    }

    /**
     * Search for songs in your library
     * @param query, a String to search for.
     */
    public void search(String query) throws Exception {
        List<Song> searchResults = songManager.search(query);
        songsInList.clear();
        songsInList.addAll(searchResults);
    }

    /**
     * Used to save the selected song from the MainView list of songs.
     * This is needed in the SongUpdateView & SongUpdateController to fill the text fields.
     * @param selectedSong, the song object selected from the MainView.
     */
    public void setSelectedSong(Song selectedSong) {
        this.selectedSong = selectedSong;
    }

    /**
     * Update/Edit a song
     * @param song, the selected song to update
     * @throws Exception up the layers
     */
    public void updateSong(Song song) throws Exception {
        songManager.updateSong(song);
    }
}
