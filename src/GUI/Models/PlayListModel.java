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
    private static PlayList selectedPlayList;

    public PlayList getSelectedPlayList() {
        return selectedPlayList;
    }

    public void setSelectedPlayList(PlayList selectedPlayList) {
        this.selectedPlayList = selectedPlayList;
    }

    public ObservableList<PlayList> getObservablePlayLists() {
        return playListsInList;
    }

    /**
     * Returns all songs in playlist.
     * @param playList The playlist to retrieve the songs from.
     * @return An observable list of songs.
     */
    public ObservableList<Song> getObservableSongsInPlayList(PlayList playList){
        songsInPlayList = FXCollections.observableArrayList();
        songsInPlayList.addAll(playList.getAllSongsInPlaylist());

        return songsInPlayList;
    }

    /**
     * Adds all playlists to playListInList
     * @throws Exception If it fails to retrieve all playlists.
     */
    public PlayListModel() throws Exception {
        playListManager = new PlayListManager();
        playListsInList = FXCollections.observableArrayList();
        playListsInList.addAll(playListManager.getAllPlayLists());
    }

    /**
     * Adds the last selected song to the last selected PlayList.
     * @throws Exception If it fails to add the song to the playlist.
     */
    public void addSongToPlayList() throws Exception {
        Song song = SongModel.getSelectedSong();

        playListManager.addSongToPlayList(selectedPlayList, song);

        //This will update the GUI.
        songsInPlayList.add(song);

        //Updates the list in the effected PlayList object.
        selectedPlayList.getAllSongsInPlaylist().add(song);
    }

    public void setSelectedPlaylist(PlayList selectedPlayList) {
        PlayListModel.selectedPlayList = selectedPlayList;
    }
}
