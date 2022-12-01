package GUI.Models;

import BE.PlayList;
import BE.Song;
import BLL.Interfaces.IPlayListManager;
import BLL.PlayListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayListModel {
    private ObservableList<PlayList> playListsInList;
    private ObservableList<Song> songsInPlayList;
    private IPlayListManager playListManager;

    /**
     * returns the ObservableList playListsInList
     * @return an observable list woth all playlists
     */
    public ObservableList<PlayList> getObservablePlayLists() {
        return playListsInList;
    }

    /**
     * the method retreive and returns the songs of the Playlist object given i parameter.
     * @param playList
     * @return
     */
    public ObservableList<Song> getObservableSongsInPlayList(PlayList playList){
        songsInPlayList = FXCollections.observableArrayList();
        songsInPlayList.addAll(playList.getAllSongsInPlaylist());
        return songsInPlayList;
    }

    /**
     * adds all playlists to playListInList
     * @throws Exception
     */
    public PlayListModel() throws Exception {
        playListManager = new PlayListManager();
        playListsInList = FXCollections.observableArrayList();
        playListsInList.addAll(playListManager.getAllPlayLists());



    }

}
