package DAL.DB;

import BE.Song;
import DAL.Interfaces.ISongDAO;
import DAL.Util.FileType;
import DAL.Util.LocalFileHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDao_DB implements ISongDAO {
    private DatabaseConnector databaseConnector;
    private static List<Song> everySong;

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
                String coverPath = rs.getString("CoverPath");

                Song song = new Song(title, artist, genre, time, path, id, coverPath);
                allSongs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve songs", e);
        }
        everySong = allSongs;
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

            // Out commented because we ran into issues with the MediaPlayer class
            // not properly disposing of the songs.
            //LocalFileHandler.deleteLocalFile(song.getPath());
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
        String sql = "UPDATE Songs SET Title=?, Artist=?, Genre=?, CoverPath=? WHERE Id=?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            Path relativeCoverPath = !song.getCoverPath().isEmpty() ? LocalFileHandler.createLocalFile(song.getCoverPath(), FileType.IMAGE) : null;
            String coverPath = relativeCoverPath != null ? String.valueOf(relativeCoverPath) : "";

            // Bind parameters
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getGenre());
            statement.setString(4, coverPath);
            statement.setInt(5, song.getId());

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
    @Override
    public Song getSongObjectFromId(int songId) throws Exception {
        for (Song s:everySong) {
            if (s.getId() == songId) {
                return s;
            }
        }
        return null;
    }

    @Override
    public Song createSong(Song song) throws Exception {

        String sql = "INSERT INTO Songs (Title, Artist, Genre, Duration, SongPath, CoverPath) VALUES (?,?,?,?,?,?) ;";

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            Path relativePath = LocalFileHandler.createLocalFile(song.getPath(), FileType.SONG);
            Path relativeCoverPath = !song.getCoverPath().isEmpty() ? LocalFileHandler.createLocalFile(song.getCoverPath(), FileType.IMAGE) : null;

            String title = song.getTitle();
            String artist = song.getArtist();
            String genre = song.getGenre();
            String path = String.valueOf(relativePath);
            String coverPath = relativeCoverPath != null ? String.valueOf(relativeCoverPath) : "";

            int time = song.getTime();

            statement.setString(1, title);
            statement.setString(2, artist);
            statement.setString(3, genre);
            statement.setInt(4, time);
            statement.setString(5, path);
            statement.setString(6, coverPath);

            statement.executeUpdate();
            int id = 0;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            Song generatedSong = new Song(title, artist, genre, time,path, id, coverPath);

            return generatedSong;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create song", e);
        }
    }
}
