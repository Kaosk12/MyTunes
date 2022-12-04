package BLL.Interfaces;

import BE.PlayList;
import BE.Song;

import java.util.List;

public interface ISongManager {

    /**
     * Get all songs in the library
     * @return a list songs.
     * @throws Exception up the layers
     */
    List<Song> getAllSongs() throws Exception;

    /**
     * Filter the list of songs in library using a search query
     * @param query, the string input used to filter
     * @return a list of songs matching the query in either title, artist or category
     * @throws Exception up the layers
     */
    List<Song> search(String query) throws Exception;

    /**
     * Update/Edit a song
     * @param song, the selected song to update
     * @throws Exception up the layers
     */
    void updateSong(Song song) throws Exception;

    /**
     * Delete a song.
     * @param song The song to delete.
     * @throws Exception If it fails to delete the song.
     */
    void deleteSong(Song song) throws Exception;
}
