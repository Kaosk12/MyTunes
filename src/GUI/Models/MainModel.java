package GUI.Models;

import BE.Song;
import BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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


}
