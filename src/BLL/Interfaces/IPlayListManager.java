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

    /**
     creates a new playlist.
     * @param playList the new playlist.
     * @return returns a new playlist.
     * @throws Exception if it fails to create new playlist.
     */
    public PlayList createPlayList(PlayList playList) throws Exception;

    /**
     * deletes a playlist from the database
     * @param playList selected playlist.
     * @throws Exception if it fails to delete playlist.
     */
    public void deletePlayList(PlayList playList) throws Exception;

}
