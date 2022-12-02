package DAL.Interfaces;

import BE.PlayList;
import BE.Song;

import java.util.List;

public interface IPlaylistDAO {
    //void createPlayList() throws Exception;

    /**
     * it gets a list of PlayList objects and returns it.
     * @return returns a list of all Playlist in DB
     * @throws Exception throws exception if it fails to return a list of Playlist objects.
     */
    List<PlayList> getAllPlayLists() throws Exception;
    //void updatePlayList() throws Exception;
    //void deletePlayList() throws Exception;
}
