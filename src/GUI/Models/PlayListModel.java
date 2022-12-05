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
    public static PlayList selectedPlaylist;

    public PlayList getSelectedPlayList() {
        return selectedPlayList;
    }

    public void setSelectedPlayList(PlayList selectedPlayList) {
        this.selectedPlayList = selectedPlayList;
    }

    private PlayList selectedPlayList;

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

    /**
     * add a song to the last selected PlayList
     * @param song
     * @throws Exception
     */
    public void addSongToPlayList(Song song) throws Exception {
        playListManager.addSongToPlayList(selectedPlayList, song);
        //This will update the GUI.
        songsInPlayList.add(song);
        //Updates the list in the effected PlayList object.
        selectedPlayList.getAllSongsInPlaylist().add(song);
    }
    public void setSelectedPlaylist(PlayList selectedPlaylist) {
        PlayListModel.selectedPlaylist = selectedPlaylist;
    }
}
