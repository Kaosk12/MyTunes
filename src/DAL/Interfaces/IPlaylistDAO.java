package DAL.Interfaces;

import BE.PlayList;

import java.util.List;

public interface IPlaylistDAO {
    //void createPlayList() throws Exception;
    List<PlayList> getAllPlayLists() throws Exception;
    //void updatePlayList() throws Exception;
    //void deletePlayList() throws Exception;
}
