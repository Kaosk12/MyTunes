package DAL.DB;

import BE.Song;
import DAL.DB.DatabaseConnector;
import DAL.Interfaces.ISongDAO;
import DAL.Util.LocalFileDeleter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDao_DB implements ISongDAO {

    private DatabaseConnector databaseConnector;


    public SongDao_DB() {
        databaseConnector = new DatabaseConnector();
    }

    /**
     * gets all the information from song table
     * takes all data and creates a song object for each
     * lists all the songs in a list called allSongs
     * @return a list of song
     */

    public List<Song> getAllSongs() throws Exception {
        ArrayList<Song> allSongs = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection();
            Statement statement = connection.createStatement();)
        {
            String sql = "SELECT * FROM Songs;";

            ResultSet rs = statement.executeQuery(sql);

            // Loop through rows from database result set
            while(rs.next()){
                //map dp row to object
                String title = rs.getString("Title").trim();
                String artist = rs.getString("Artist").trim();
                String genre = rs.getString("Genre").trim();
                int time = rs.getInt("Duration");
                String path = rs.getString("SongPath");
                int id = rs.getInt("Id");



                Song song = new Song(title, artist, genre, time, path, id);
                allSongs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve songs", e);
        }

        return allSongs;
    }

    @Override
    public void deleteSong(Song song) throws Exception {
        String sql = "DELETE FROM Songs WHERE Id = ?;";

        try (Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            //Bind parameters
            statement.setInt(1, song.getId());

            //Run the specified SQL Statement
            statement.executeUpdate();

            LocalFileDeleter.deleteLocalFile(song.getPath());
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete song", e);
        }
    }

    /**
     * Update/Edit the title, artist, and genre of a song in the database.
     * @param song, the selected song to update.
     * @throws Exception up the layers
     */
    @Override
    public void updateSong(Song song) throws Exception {
        String sql = "UPDATE Songs SET Title=?, Artist=?, Genre=? WHERE Id=?;"; //Match to database column name

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //Bind parameters
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getGenre());
            statement.setInt(4, song.getId());

            //Run the specified SQL statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to edit the song", e);
        }
    }
    /**
     *
     * @param songId
     * @return it returns a songs object from the given parameter
     * @throws Exception
     */
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

    @Override
    public void createSong(Song song) {
        
    }
}
