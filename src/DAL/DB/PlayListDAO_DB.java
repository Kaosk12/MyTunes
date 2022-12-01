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

    public PlayListDAO_DB(){
        databaseConnector = new DatabaseConnector();
    }


    public void createPlayList() {

    }


    /**
     * reads all playlists/data in the Playlist table in the database, then makes them into PlayList objects and adds
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

    public void readAllSongsInPlayList(PlayList playList) throws Exception{
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

                tempSongsId.add(songIdFromDB);
                tempOrders.add(songPlacement);
                properOrder.add(songPlacement);

            }
            //sort the list to the corect order.
            Collections.sort(properOrder);
            for (int i:properOrder){
                playList.addSongToPlaylist(getSongFromId(tempSongsId.get(tempOrders.indexOf(i))));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Song getSongFromId(int songId) throws Exception {
        try(Connection connection = databaseConnector.getConnection();)
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Songs WHERE Id = " + songId + ";";

            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String title = rs.getString("Title").trim();
                String artist = rs.getString("Artist").trim();
                String genre = rs.getString("Genre").trim();
                int time = rs.getInt("Duration");
                String path = rs.getString("SongPath");
                int id = rs.getInt("Id");
                Song song = new Song(title, artist, genre, time, path, id);
                return song;
            }
            return null;



        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve songs", e);
        }
    }

    public void updatePlayList() {

    }


    public void deletePlayList() {

    }
}
