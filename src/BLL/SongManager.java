package BLL;

import BE.Song;
import BLL.Interfaces.ISongManager;
import BLL.Util.SongSearcher;
import DAL.Interfaces.ISongDAO;
import DAL.DB.SongDao_DB;
import java.util.List;

public class SongManager implements ISongManager {
    private ISongDAO databaseAccess;
    private SongSearcher songSearcher;

    public SongManager() {
        databaseAccess = new SongDao_DB();
        songSearcher = new SongSearcher();
    }

    /**
     * Returns all songs
     * @return A list of all songs.
     * @throws Exception If it fails to retrieve all songs.
     */
    public List<Song> getAllSongs() throws Exception {
        return databaseAccess.getAllSongs();
    }

    /**
     * Filter the list of songs in library using a search query
     * @param query, the string input used to filter
     * @return a list of songs matching the query in either title, artist or category
     * @throws Exception If it fails to search.
     */
    public List<Song> search(String query) throws Exception {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);

        return searchResult;
    }

    /**
     * Update/Edit a song
     * @param song, the selected song to update
     * @throws Exception If it fails to update the song.
     */
    @Override
    public void updateSong(Song song) throws Exception {
        databaseAccess.updateSong(song);
    }

    /**
     * Delete a song.
     * @param song The song to delete.
     * @throws Exception If it fails to delete the song.
     */
    @Override
    public void deleteSong(Song song) throws Exception {
        databaseAccess.deleteSong(song);
    }

    /**
     * Creates a new song.
     * @param song The song to create.
     * @return The newly created song.
     * @throws Exception If it fails to create the song.
     */
    public Song createSong(Song song) throws Exception {
       return databaseAccess.createSong(song);
    }
}
