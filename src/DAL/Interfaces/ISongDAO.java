package DAL.Interfaces;

import BE.Song;

import java.util.List;

public interface ISongDAO {

    void updateSong(Song song) throws Exception;

    List<Song> getAllSongs() throws Exception;

    /**
     * Deletes a song from the database connection.
     * @param song The song to delete.
     * @throws Exception If it fails to delete the song.
     */
    void deleteSong(Song song) throws Exception;
}
