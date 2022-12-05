package DAL.Interfaces;

import BE.PlayList;
import BE.Song;
import java.util.List;

public interface IPlaylistDAO {
    //void createPlayList() throws Exception;

    /**
     * Return a list of PlayList objects.
     * @return A list of all Playlists.
     * @throws Exception throws exception if it fails to return a list of Playlist objects.
     */
    List<PlayList> getAllPlayLists() throws Exception;

    /**
     * Adds a song to a playlist.
     * @param playList The playlist to add a song to.
     * @param song The song to add to the playlist.
     * @throws Exception If it fails to add the song to the playlist.
     */
    void addSongToPlayList(PlayList playList, Song song) throws Exception;

    //void editPlayList() throws Exception;

    //void deletePlayList() throws Exception;
}
