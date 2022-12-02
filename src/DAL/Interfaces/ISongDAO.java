package DAL.Interfaces;

import BE.Song;

import java.util.List;

public interface ISongDAO {

    void updateSong(Song song) throws Exception;

    List<Song> getAllSongs() throws Exception;

    Song getSongFromId(int songId) throws Exception;

}
