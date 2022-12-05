package BLL.Interfaces;

import BE.PlayList;
import BE.Song;

import java.util.List;

public interface IPlayListManager {

    /**
     * Returns all playlists.
     * @return a list of all playlists.
     * @throws Exception If it fails to retrieve all playlists.
     */
    List<PlayList> getAllPlayLists() throws Exception;

    /**
     * Adds a song to a playlist.
     * @param playList The playlist to add the song to.
     * @param song The song to add to the playlist.
     * @throws Exception If it fails to add the song.
     */
    void addSongToPlayList(PlayList playList, Song song) throws Exception;
}
