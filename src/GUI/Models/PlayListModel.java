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
    private static PlayList selectedPlaylist;
    private static Song selectedSOP;

    public PlayList getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public void setSelectedPlaylist(PlayList selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }
    public static Song getSelectedSOP() {
        return selectedSOP;
    }

    public static void setSelectedSOP(Song selectedSOP) {
        PlayListModel.selectedSOP = selectedSOP;
    }



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
     * @throws Exception
     */
    public void addSongToPlayList() throws Exception {
        Song song = SongModel.getSelectedSong();
        playListManager.addSongToPlayList(selectedPlaylist, song);
        //This will update the GUI.
        songsInPlayList.add(song);
        //Updates the list in the effected PlayList object.
        selectedPlaylist.addSongToPlaylist(song);
    }

    public void deleteSOP() throws Exception {
        //This will remove the song from the playlist in the database.
        playListManager.removeSOP(selectedPlaylist, selectedSOP);
        //this will remove the song from the GUI.
        songsInPlayList.remove(selectedSOP);
        //Updates the list in the effected PlayList object.
        selectedPlaylist.removeSOP(selectedSOP);
    }

    public void updatePlayList(PlayList playList) throws Exception {
        playListManager.updatePlayList(playList);
    }

    public void deletePlayList() throws Exception{
        //deletes the playlist from the db
        playListManager.deletePlayList(selectedPlaylist);
        //removes the playlist from observables.
        playListsInList.remove(selectedPlaylist);
    }

    public void createPlayList(PlayList playList) throws Exception {
        //Inserts the new playlist into the db
        playListManager.createPlayList(playList);
        //Adds the new playlist to observable playlists.
        playListsInList.add(playList);
    }

}
