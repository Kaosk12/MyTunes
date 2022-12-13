package GUI.Models;

import BE.PlayList;
import BE.Song;
import BLL.Interfaces.IPlayListManager;
import BLL.PlayListManager;
import GUI.Util.ConfirmDelete;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayListModel {
    private ObservableList<PlayList> playListsInList;
    private ObservableList<Song> songsInPlayList;
    private IPlayListManager playListManager;
    private static PlayList selectedPlaylist;
    private static Song selectedSOP;

    public static PlayList getSelectedPlaylist() {
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
        //if the user have selected a playlist, then adds a song to it.
        if(selectedPlaylist != null){
            playListManager.addSongToPlayList(selectedPlaylist, song);

            //This will update the GUI.
            songsInPlayList.add(song);

            //Updates the list in the effected PlayList object.
            selectedPlaylist.addSongToPlaylist(song);
        }
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
        PlayList newPlaylist = playListManager.createPlayList(playList);
        //Adds the new playlist to observable playlists.
        playListsInList.add(newPlaylist);
    }

    public void moveSOP(Boolean moveUp) throws Exception{
        playListManager.moveSOP(selectedPlaylist, selectedSOP, moveUp);
        songsInPlayList.clear();
        songsInPlayList.addAll(selectedPlaylist.getAllSongsInPlaylist());
    }

}
