package DAL.Interfaces;

import BE.Song;
import DAL.DB.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDao_DB implements ISongDAO{

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

    public List<Song> getAllSongs() {
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
                String path = rs.getString("Path");
                int id = rs.getInt("Id");



                Song song = new Song(title, artist, genre, time, path, id);
                allSongs.add(song);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allSongs;
    }
}
