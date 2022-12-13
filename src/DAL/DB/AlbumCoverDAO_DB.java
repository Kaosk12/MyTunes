package DAL.DB;

import BE.Song;
import DAL.Interfaces.IAlbumCoverDAO;
import DAL.Util.LocalFileHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.sql.*;

public class AlbumCoverDAO_DB implements IAlbumCoverDAO {
    private DatabaseConnector databaseConnector;

    public AlbumCoverDAO_DB() {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public void updateCover(int songID, File file) throws Exception {

    }

    @Override
    public void deleteCover(int songID) throws Exception {

    }

    @Override
    public Image getAlbumCover(int songID) throws Exception {
        int songId = songID;
        String sql = "SELECT Image FROM AlbumCovers WHERE Id = ?;";
        /**
         *
        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            // Bind parameters
            statement.setInt(1, songId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                //FileInputStream (/output?) til Image?
                //return image;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve album cover", e);
        }
         */
        return null;
    }

    @Override
    public void createAlbumCover(int songID, File file) throws Exception {
        String path = file.getAbsolutePath();
        FileInputStream fileInputStream = null;

        String sql = "INSERT INTO AlbumCovers (SongID, Image) VALUES (?,?);";

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            fileInputStream = new FileInputStream(path);

            statement.setInt(1, songID);
            statement.setBinaryStream(2, fileInputStream);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to add album cover", e);

        }
    }
}
