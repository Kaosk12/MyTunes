package GUI.Models;

import BE.Song;
import BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MainModel {

    private ObservableList<Song> songsInList;
    private SongManager songManager;

    public ObservableList<Song> getObservableSongs() {
        return songsInList;
    }

    public MainModel() throws Exception {
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
