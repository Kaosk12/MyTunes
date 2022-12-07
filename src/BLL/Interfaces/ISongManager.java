package BLL.Interfaces;

import BE.Song;

import java.util.List;

public interface ISongManager {

    /**
     * Returns all songs
     * @return A list of all songs.
     * @throws Exception If it fails to retrieve all songs.
     */
    List<Song> getAllSongs() throws Exception;

    /**
     * Filter the list of songs in library using a search query
     * @param query, the string input used to filter
     * @return a list of songs matching the query in either title, artist or category
     * @throws Exception If it fails to search.
     */
    List<Song> search(String query) throws Exception;

    /**
     * Update/Edit a song
     * @param song, the selected song to update
     * @throws Exception If it fails to update the song.
     */
    void updateSong(Song song) throws Exception;

    /**
     * Delete a song.
     * @param song The song to delete.
     * @throws Exception If it fails to delete the song.
     */
    void deleteSong(Song song) throws Exception;

    Song createSong(Song song) throws Exception;
}
