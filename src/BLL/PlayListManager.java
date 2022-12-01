package BLL;

import BE.PlayList;
import DAL.DB.PlayListDAO_DB;
import DAL.Interfaces.IPlaylistDAO;

import java.util.List;

public class PlayListManager {
    private IPlaylistDAO databaseAcces;

    /**
     * initializes PlayListDAO_DB
     */
    public PlayListManager() {
        databaseAcces = new PlayListDAO_DB();
    }
    private PlayListDAO_DB playListDAO;

    /**
     * retreives all playlists from DAL
     * @return list containing all playlists
     * @throws Exception
     */
    public List<PlayList> getAllPlayLists() throws Exception{
        return databaseAcces.getAllPlayLists();
    }

}
