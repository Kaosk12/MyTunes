package BLL.Interfaces;

import BE.PlayList;

import java.util.List;

public interface IPlayListManager {

    /**
     * gets all playlists from PlayListDAO in DB.
     * @return a list of all playlists.
     * @throws Exception
     */
    List<PlayList> getAllPlayLists() throws Exception;
}