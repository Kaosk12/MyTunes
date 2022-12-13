package DAL.Interfaces;

import BE.Song;
import javafx.scene.image.Image;

import java.io.File;

public interface IAlbumCoverDAO {

    /**
     * Updates an album cover with a new one.
     * @param songID ID of the song in question
     * @param file The new album cover image
     * @throws Exception If it fails to update the album cover.
     */
    void updateCover(int songID, File file) throws Exception;

    /**
     * Deletes an album cover.
     * @param songID ID of the song to delete from.
     * @throws Exception If it fails to delete the album cover.
     */
    void deleteCover(int songID) throws Exception;

    /**
     * Returns an album cover from the song.
     * @param songID ID of the song to return from.
     * @return An image object.
     * @throws Exception If it fails to retrieve an album cover from given song.
     */
    Image getAlbumCover(int songID) throws Exception;

    /**
     * Creates an album cover in the database.
     * @param songID ID of the song to create album cover for.
     * @param file The image of the album cover.
     * @return Returns the newly created image.
     * @throws Exception If it fails to create the album cover.
     */
    void createAlbumCover(int songID, File file) throws Exception;
}
