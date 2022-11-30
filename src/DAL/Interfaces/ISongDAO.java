package DAL.Interfaces;

import BE.Song;

import java.util.List;

public interface ISongDAO {
    List<Song> getAllSongs() throws Exception;
}
