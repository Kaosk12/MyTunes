package BLL;

import BE.Song;
import DAL.Interfaces.ISongDAO;
import DAL.DB.SongDao_DB;

import java.util.List;

public class SongManager {
    private ISongDAO databaseAcces;

    public SongManager() {
        databaseAcces = new SongDao_DB();
    }

    private SongDao_DB songDAO;

    public List<Song> getAllSongs() throws Exception {
        return databaseAcces.getAllSongs();
    }
}
