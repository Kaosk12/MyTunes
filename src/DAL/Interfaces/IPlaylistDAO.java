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

    /**
     * removes a songs from a playlist.
     * @param playList last selected playlist
     * @param song last selected song
     * @throws Exception throws exception if it fails to remove song
     */
    void removeSOP(PlayList playList, Song song) throws Exception;

    /**
     * updates a playlist title.
     * @param playList last selected playlist.
     * @throws Exception throws exception if it fails to update song.
     */
    void updatePlayList(PlayList playList) throws Exception;

    /**
     * creates a new playlist.
     * @param playList the new playlist.
     * @throws Exception if it fails to create new playlist
     */
    public void createPlayList(PlayList playList) throws Exception;

    /**
     * deletes a playlist from the database
     * @param playList selected playlist.
     * @throws Exception if it fails to delete playlist.
     */
    public void deletePlayList(PlayList playList) throws Exception;
}
