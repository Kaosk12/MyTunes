package BLL;

import BE.PlayList;
import BE.Song;
import BLL.Interfaces.IPlayListManager;
import DAL.DB.PlayListDAO_DB;
import DAL.Interfaces.IPlaylistDAO;

import java.util.List;

public class PlayListManager implements IPlayListManager {
    private IPlaylistDAO databaseAcces;

    public PlayListManager() {
        databaseAcces = new PlayListDAO_DB();
    }

    /**
     * Returns all playlists.
     * @return a list of all playlists.
     * @throws Exception If it fails to retrieve all playlists.
     */
    public List<PlayList> getAllPlayLists() throws Exception{
        return databaseAcces.getAllPlayLists();
    }

    /**
     * Adds a song to a playlist.
     * @param playList The playlist to add the song to.
     * @param song The song to add to the playlist.
     * @throws Exception If it fails to add the song.
     */
    public void addSongToPlayList(PlayList playList, Song song) throws Exception {
        databaseAcces.addSongToPlayList(playList, song);
    }
}
