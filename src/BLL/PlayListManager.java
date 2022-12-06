package BLL;

import BE.PlayList;
import BE.Song;
import BLL.Interfaces.IPlayListManager;
import DAL.DB.PlayListDAO_DB;
import DAL.Interfaces.IPlaylistDAO;

import java.util.List;

public class PlayListManager implements IPlayListManager {
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
    public void addSongToPlayList(PlayList playList, Song song) throws Exception {
        databaseAcces.addSongToPlayList(playList, song);
    }

    public void removeSOP(PlayList playList, Song song) throws Exception{
        databaseAcces.removeSOP(playList, song);
    }

    public void updatePlayList(PlayList playList) throws Exception{
        databaseAcces.updatePlayList(playList);
    }
}
