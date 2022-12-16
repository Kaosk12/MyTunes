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
    private PlayListModel playListModel;
    private static Song selectedSong;

    public ObservableList<Song> getObservableSongs() {
        return songsInList;
    }
    public static Song getSelectedSong() {
        return selectedSong;
    }

    public void setPlayListModel(PlayListModel playListModel){
        this.playListModel = playListModel;
    }

    /**
     * sets the song manager
     * gets a list of all songs and stores them in an observable list
     * @throws Exception If it fails to retrieve all songs.
     */
    public SongModel() throws Exception {
        songManager = new SongManager();
        songsInList = FXCollections.observableArrayList();
        songsInList.addAll(songManager.getAllSongs());
    }

    /**
     * Filter the list of songs in library using a search query
     * @param query, a String to search for.
     */
    public void search(String query) throws Exception {
        List<Song> searchResults = songManager.search(query);

        songsInList.clear();
        songsInList.addAll(searchResults);
    }

    /**
     * Used to save the selected song from the MainView list of songs.
     * This is needed in the SongUpdateView & SongUpdateController to fill the text fields.
     * @param selectedSong, the song object selected from the MainView.
     */
    public void setSelectedSong(Song selectedSong) {
        this.selectedSong = selectedSong;
    }



    /**
     * Update/Edit a song
     * @param song, the selected song to update
     * @throws Exception If it fails to update the song.
     */
    public void updateSong(Song song) throws Exception {
        songManager.updateSong(song);
        playListModel.updateCFS(song, false);
    }

    /**
     * Deletes a song.
     * @param song The song to delete.
     * @throws Exception If it fails to delete the song.
     */
    public void deleteSong(Song song) throws Exception {
        playListModel.updateCFS(song,true);
        songManager.deleteSong(song);


    }


    public Song createSong(Song song) throws Exception {
        return songManager.createSong(song);
    }

}
