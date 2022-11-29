package GUI.Models;

import BE.PlayList;
import BE.Song;
import BLL.PlayListManager;
import BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainModel {

    private ObservableList<Song> songsInList;
    private ObservableList<PlayList> playListsInList;
    private SongManager songManager;
    private PlayListManager playListManager;

    public ObservableList<Song> getObservableSongs() {
        return songsInList;
    }

    public ObservableList<PlayList> getObservablePlayLists() {
        return playListsInList;
    }

    public MainModel() throws Exception {
        songManager = new SongManager();
        songsInList = FXCollections.observableArrayList();
        songsInList.addAll(songManager.getAllSongs());

        //Playlists
        playListManager = new PlayListManager();
        playListsInList = FXCollections.observableArrayList();
        playListsInList.addAll(playListManager.getAllPlayLists());

    }


}
