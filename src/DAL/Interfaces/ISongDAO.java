package DAL.Interfaces;

import BE.Song;
import java.util.List;

public interface ISongDAO {
    /**
     * Updates a song with a new one.
     * @param song The new song.
     * @throws Exception If it fails to update the song.
     */
    void updateSong(Song song) throws Exception;

    /**
     * Returns all songs.
     * @return A list of all songs.
     * @throws Exception If it fails to retrieve all songs.
     */
    List<Song> getAllSongs() throws Exception;

    /**
     * Deletes a song.
     * @param song The song to delete.
     * @throws Exception If it fails to delete the song.
     */
    void deleteSong(Song song) throws Exception;

    /**
     * Returns a song with given ID.
     * @param songId The ID of the song to return.
     * @return A song object.
     * @throws Exception If it fails to retrieve a song with given ID.
     */
    Song getSongObjectFromId(int songId) throws Exception;

    /**
     * Creates a song in the database.
     * @param song The song to create.
     * @return Returns the newly created song.
     * @throws Exception If it fails to create the song.
     */
    Song createSong(Song song) throws Exception;
}
