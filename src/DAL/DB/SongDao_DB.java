package DAL.DB;

import BE.Song;
import DAL.DB.DatabaseConnector;
import DAL.Interfaces.ISongDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDao_DB implements ISongDAO {

    private DatabaseConnector databaseConnector;

    public SongDao_DB() {
        databaseConnector = new DatabaseConnector();
    }

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
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String genre = rs.getString("Genre");
                int time = rs.getInt("Duration");


                Song song = new Song(title, artist, genre, time);
                allSongs.add(song);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allSongs;
    }
}
