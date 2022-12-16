package GUI.Models;

import BE.PlayList;
import BE.Song;
import BLL.Interfaces.IPlayListManager;
import BLL.PlayListManager;
import GUI.Controllers.MainController;
import GUI.Util.ConfirmDelete;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

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
        if (playList == null) {
            songsInPlayList.clear();
        } else {
            songsInPlayList = FXCollections.observableArrayList();
            songsInPlayList.addAll(playList.getAllSongsInPlaylist());
        }
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
        songsInPlayList = FXCollections.observableArrayList();
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

    public void createPlayList(PlayList playList, boolean addSong) throws Exception {
        //Inserts the new playlist into the db
        PlayList newPlaylist = playListManager.createPlayList(playList);

        //Adds the new playlist to observable playlists.
        playListsInList.add(newPlaylist);
        if (addSong){
            selectedPlaylist = newPlaylist;
            addSongToPlayList();
        }
    }

    public void moveSOP(Boolean moveUp) throws Exception{
        playListManager.moveSOP(selectedPlaylist, selectedSOP, moveUp);
        songsInPlayList.clear();
        songsInPlayList.addAll(selectedPlaylist.getAllSongsInPlaylist());
    }

    /**
     * This method is used to update the playlist table, if changes have been made to songs in the song table.
     * @param song the song that has been edited or deleted in the song table.
     * @param deleteSong it is used to tell the method, if the song has been deleted or edited.
     */
    public void updateCFS(Song song, boolean deleteSong){
        //CFS changesFromSongs
        //loops through all playlists.
        for(PlayList p: playListsInList){
            //loops through all songs in the playlist, checking if the songId matches the song i parameter.
            for (Song song1:p.getAllSongsInPlaylist()) {
                if ((song1.getId() == song.getId()) && !deleteSong) {
                    //replaces the old data with the new.
                    song1.setArtist(song.getArtist());
                    song1.setGenre(song.getGenre());
                    song1.setTitle(song.getTitle());
                    song1.setCoverPath(song.getCoverPath());
                    return;
                }
                else if ((song1.getId() == song.getId()) && deleteSong) {
                    //checks if the song is currently being shown to the user, then removes it from the list.
                    if (songsInPlayList.contains(song1)){
                        songsInPlayList.remove(song1);
                    }
                    p.removeSOP(song1);
                    return;
                }
            }
        }
    }
}
