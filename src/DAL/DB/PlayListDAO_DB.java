package DAL.DB;

import BE.PlayList;
import BE.Song;
import DAL.Interfaces.IPlaylistDAO;
import DAL.Interfaces.ISongDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PlayListDAO_DB implements IPlaylistDAO {
    private DatabaseConnector databaseConnector;
    private ISongDAO songDAO;

    public PlayListDAO_DB(){
        databaseConnector = new DatabaseConnector();
        songDAO = new SongDao_DB();
    }


    public void createPlayList() {

    }


    /**
     * reads all data in the Playlist table in the database. It makes the data into PlayList objects and adds
     * them to an ArrayList that it returns.
     * @return a list containing all playlists
     */
    public List<PlayList> getAllPlayLists() throws Exception {

        try(Connection connection = databaseConnector.getConnection())
        {
            String sql = "SELECT * FROM Playlists;";

            Statement statement = connection.createStatement();
            ArrayList<PlayList> allPlayList = new ArrayList<>();


            ResultSet rs = statement.executeQuery(sql);

            // Loop through rows from database result set
            while(rs.next()){
                //map dp row to object
                int playListId = rs.getInt("Id");
                String title = rs.getString("Title");
                PlayList playList = new PlayList(playListId, title);
                allPlayList.add(playList);
                readAllSongsInPlayList(playList);
            }
            return allPlayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It reads the data from SongsInPlaylists table from the database,
     * on the given playlist in parameter.
     * It calls the method getSongFromId with a song id it got from the database.
     * @param playList
     * @throws Exception
     */
    private void readAllSongsInPlayList(PlayList playList) throws Exception{
        try(Connection connection = databaseConnector.getConnection())
        {
            String sql = "SELECT * FROM SongsInPlaylists WHERE PlaylistId = " + playList.getPlayListId() + ";";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            //we use theese list to sort the correct order of the song.
            ArrayList<Integer> tempSongsId = new ArrayList<>();
            ArrayList<Integer> tempOrders = new ArrayList<>();
            ArrayList<Integer> properOrder = new ArrayList<>();

            // Loop through rows from database result set
            while(rs.next()){
                //map dp row to object
                int songIdFromDB = rs.getInt("SongId");
                //int playListIdFromDB = rs.getInt("PlaylistId");
                int songPlacement = rs.getInt("NumberInPlaylist");

                //we use the 3 list to keep track of the data from the table SongInPlaylist.
                //The list are used to sort the songs placement in the playlist.
                tempSongsId.add(songIdFromDB);
                tempOrders.add(songPlacement);
                properOrder.add(songPlacement);

            }
            //sort the list to the correct order.
            Collections.sort(properOrder);
            for (int i:properOrder){
                //finds the song id of the next song in the list.
                int songId = tempSongsId.get(tempOrders.indexOf(i));
                //returns a song object from given song id.
                Song song =  songDAO.getSongFromId(songId);
                //adds the song object to the playlist object.
                playList.addSongToPlaylist(song);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void updatePlayList() {

    }


    public void deletePlayList() {

    }
}
