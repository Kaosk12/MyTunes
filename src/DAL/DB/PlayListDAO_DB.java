package DAL.DB;

import BE.PlayList;
import BE.Song;
import DAL.Interfaces.IPlaylistDAO;
import DAL.Interfaces.ISongDAO;
import DAL.Util.LocalFileDeleter;

import java.sql.*;
import java.util.*;

public class PlayListDAO_DB implements IPlaylistDAO {
    private DatabaseConnector databaseConnector;
    private ISongDAO songDAO;

    public PlayListDAO_DB(){
        databaseConnector = new DatabaseConnector();
        songDAO = new SongDao_DB();
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

    public void addSongToPlayList(PlayList playList, Song song) throws Exception{
        String sql = "INSERT INTO SongsInPlaylists (SongId, PlaylistId, NumberInPlaylist) VALUES (?,?,?);";

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);)
        {


            //Parameters
            int songID = song.getId();
            int playlistId = playList.getPlayListId();
            int songPlacement = playList.getSongAmount() + 1;

            //Bind parameters
            statement.setInt(1, songID);
            statement.setInt(2, playlistId);
            statement.setInt(3, songPlacement);

            //Run the specified SQL statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to add new song", e);
        }
    }

    /**
     * remove the song from the playlist in the database.
     * @param playList last selected playlist
     * @param song last selected song
     * @throws Exception
     */
    public void removeSOP(PlayList playList, Song song) throws Exception {
        String sql = "DELETE FROM SongsInPlaylists WHERE SongId = ? AND PlaylistId = ?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //Bind parameters
            statement.setInt(1, song.getId());
            statement.setInt(2, playList.getPlayListId());

            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to remove song", e);
        }
    }


    public void updatePlayList(PlayList playList) throws Exception {
        String sql = "UPDATE Playlists SET Title = ? WHERE Id = ?;";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, playList.getTitle());
            statement.setInt(2, playList.getPlayListId());
            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update playlist", e);
        }
    }



    public void deletePlayList(PlayList playList) throws Exception {
        String sql = "DELETE FROM Playlists WHERE Id = ?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //Bind parameters
            statement.setInt(1, playList.getPlayListId());

            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to remove playlist", e);
        }
    }

    public void createPlayList(PlayList playList) throws Exception {
        String sql = "INSERT INTO Playlists(Title, Creator) VALUES (?,?);";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            //Bind parameters
            statement.setString(1, playList.getTitle());
            statement.setString(2, playList.getCreatorName());

            //Run the specified SQL Statement
            statement.executeUpdate();

            //Get the generated Id from the DB
            ResultSet rs = statement.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }
            playList.setPlayListId(id);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create playlist", e);
        }
    }
}
