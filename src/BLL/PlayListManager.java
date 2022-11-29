package BLL;

import BE.PlayList;
import DAL.Interfaces.IPlaylistDAO;
import DAL.DB.PlayListDAO_DB;

import java.util.List;

public class PlayListManager {
    private IPlaylistDAO databaseAcces;

    public PlayListManager() {
        databaseAcces = new PlayListDAO_DB();
    }
    private PlayListDAO_DB playListDAO;
    public List<PlayList> getAllPlayLists() throws Exception{
        return databaseAcces.getAllPlayLists();
    }

}
