package BLL;

import BE.Song;
import DAL.Interfaces.ISongDataAccess;
import DAL.Interfaces.SongDao_DB;

import java.util.List;

public class SongManager {
    private ISongDataAccess databaseAcces;

    public SongManager() {
        databaseAcces = new SongDao_DB();
    }

    private SongDao_DB songDAO;

    public List<Song> getAllSongs() throws Exception {
        return databaseAcces.getAllSongs();
    }
}
