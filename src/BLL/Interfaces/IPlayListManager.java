package BLL.Interfaces;

import BE.PlayList;
import BE.Song;

import java.util.List;

public interface IPlayListManager {

    /**
     * gets all playlists from PlayListDAO in DB.
     * @return a list of all playlists.
     * @throws Exception throws exception if
     */
    List<PlayList> getAllPlayLists() throws Exception;

    /**
     * adds the last selected song to the last selected playlist
     * @param playList
     * @param song
     * @throws Exception
     */
    void addSongToPlayList(PlayList playList, Song song) throws Exception;
}
