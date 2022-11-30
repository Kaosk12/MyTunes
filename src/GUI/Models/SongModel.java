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
}
