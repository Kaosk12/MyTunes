package DAL.DB;

import BE.Song;
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
     * Returns all songs from the database connection.
     * @return A list of all songs.
     * @throws Exception If it fails to retrieve all songs.
     */
    public List<Song> getAllSongs() throws Exception {
        ArrayList<Song> allSongs = new ArrayList<>();

        try(Connection connection = databaseConnector.getConnection();
            Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM Songs;";

            ResultSet rs = statement.executeQuery(sql);

            // Loop through rows from database result set.
            while(rs.next()){
                //map database row to object.
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

    /**
     * Deletes a song from the database connection.
     * @param song The song to delete.
     * @throws Exception If it fails to delete the song.
     */
    @Override
    public void deleteSong(Song song) throws Exception {
        String sql = "DELETE FROM Songs WHERE Id = ?;";

        try (Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            // Bind parameters
            statement.setInt(1, song.getId());

            // Run the specified SQL Statement
            statement.executeUpdate();

            // Delete the file locally.
            LocalFileDeleter.deleteLocalFile(song.getPath());
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete song", e);
        }
    }

    /**
     * Updates a song in the database with a new one.
     * @param song The new song.
     * @throws Exception If it fails to update the song.
     */
    @Override
    public void updateSong(Song song) throws Exception {
        String sql = "UPDATE Songs SET Title=?, Artist=?, Genre=? WHERE Id=?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Bind parameters
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getGenre());
            statement.setInt(4, song.getId());

            // Run the specified SQL statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to edit the song", e);
        }
    }

    /**
     * Returns a song from the database with given ID.
     * @param songId The ID of the song to return.
     * @return A song object.
     * @throws Exception If it fails to retrieve a song with given ID.
     */
    public Song getSongFromId(int songId) throws Exception {
        String sql = "SELECT * FROM Songs WHERE Id = ?;";

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            // Bind parameters
            statement.setInt(1, songId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
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
}
