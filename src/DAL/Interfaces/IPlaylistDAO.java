package DAL.Interfaces;

import BE.PlayList;
import BE.Song;

import java.util.List;

public interface IPlaylistDAO {
    //void createPlayList() throws Exception;

    /**
     * it sends a query to the database to retreive all data in the Playlist table,
     * then uses that data to instansiate a object. lastly it calls readAllSongsInPlaylist,
     * with the new playlist object.
     * @return returns a list of all Playlist in DB
     * @throws Exception
     */
    List<PlayList> getAllPlayLists() throws Exception;

    /**
     * the method reads the SongsInPlaylist table,
     * from the database and retreive data based on the given parameter.
     * @param playList its a Playlist id.
     * @throws Exception
     */
    void readAllSongsInPlayList(PlayList playList) throws Exception;

    /**
     * it finds data in the database based in the given songId in parameters.
     * It makes a songobject with the data, then it returns the object.
     * @param songId
     * @return returns a song object
     * @throws Exception
     */
    Song getSongFromId(int songId) throws Exception;
    //void updatePlayList() throws Exception;
    //void deletePlayList() throws Exception;
}
