package BLL;

import BE.Song;
import BLL.Interfaces.ISongManager;
import BLL.Util.SongSearcher;
import DAL.Interfaces.ISongDAO;
import DAL.DB.SongDao_DB;

import java.util.List;

public class SongManager implements ISongManager {
    private ISongDAO databaseAccess;
    private SongSearcher songSearcher = new SongSearcher();

    public SongManager() {
        databaseAccess = new SongDao_DB();
    }
    
    private SongDao_DB songDAO;

    /**
     * gets all songs from dal and sends them on to the model
     * @return returns a list of all songs from database
     * @throws Exception up the layer
     */
    public List<Song> getAllSongs() throws Exception {
        return databaseAccess.getAllSongs();
    }

    /**
     * Filter the list of songs in library using a search query
     * @param query, the string input used to filter
     * @return a list of songs matching the query in either title, artist or category
     * @throws Exception up the layer
     */
    public List<Song> search(String query) throws Exception {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }

    /**
     * Update/Edit a song
     * @param song, the selected song to update
     * @throws Exception up the layers
     */
    @Override
    public void updateSong(Song song) throws Exception {
        databaseAccess.updateSong(song);
    }

    @Override
    public void deleteSong(Song song) throws Exception {
        databaseAccess.deleteSong(song);
    }

    public Song createSong(Song song){
        databaseAccess.createSong(song);
        return null;
    }

}
