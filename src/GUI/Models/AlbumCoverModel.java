package GUI.Models;

import BLL.AlbumCoverManager;
import BLL.Interfaces.IAlbumCoverManager;
import javafx.scene.image.Image;

import java.io.File;

public class AlbumCoverModel {
    private IAlbumCoverManager albumCoverManager;

    public AlbumCoverModel() {
        albumCoverManager = new AlbumCoverManager();
    }

    public void updateCover(int songID, File file) throws Exception {
        albumCoverManager.updateCover(songID, file);
    }

    public void deleteCover(int songID) throws Exception {
        albumCoverManager.deleteCover(songID);
    }

    public Image getAlbumCover(int songID) throws Exception {
        if (albumCoverManager.getAlbumCover(songID) != null) {
            return albumCoverManager.getAlbumCover(songID);
        }
        return null;
    }

    public void createAlbumCover(int songID, File file) throws Exception {
        albumCoverManager.createAlbumCover(songID, file);
    }
}
