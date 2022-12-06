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
     * @param playList last selected playlist
     * @param song last selected song
     * @throws Exception
     */
    void addSongToPlayList(PlayList playList, Song song) throws Exception;

    /**
     * removes a song from a playlist.
     * @param playList last selected playlist.
     * @param song last selected song.
     * @throws Exception if it fails to remove the song.
     */
    void removeSOP(PlayList playList, Song song) throws Exception;

    /**
     * updates a playlist title.
     * @param playList last selected playlist.
     * @throws Exception if it fails to update playlist.
     */
    void updatePlayList(PlayList playList) throws Exception;

}
