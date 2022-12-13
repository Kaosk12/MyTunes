package BLL;

import BLL.Interfaces.IAlbumCoverManager;
import DAL.DB.AlbumCoverDAO_DB;
import DAL.Interfaces.IAlbumCoverDAO;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class AlbumCoverManager implements IAlbumCoverManager {
    private IAlbumCoverDAO databaseAccess;
    public AlbumCoverManager() {
        databaseAccess = new AlbumCoverDAO_DB();
    }

    @Override
    public void updateCover(int songID, File file) throws Exception {

    }

    @Override
    public void deleteCover(int songID) throws Exception {

    }

    @Override
    public Image getAlbumCover(int songID) throws Exception {
        if(databaseAccess.getAlbumCover(songID) != null) {
            return databaseAccess.getAlbumCover(songID);
        } else {
            //Sets "missing album art" image if getAlbumCover is null (randomly to check it's working)
            boolean r = Math.random() < 0.5;
            return r ? new Image("missing-album-art-icon.png") : new Image("cover-art-template.jpg");
        }
    }

    @Override
    public void createAlbumCover(int songID, File file) throws Exception {
        databaseAccess.createAlbumCover(songID, file);
    }
}
