package DAL.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.*;

public class LocalFileHandler {
    /**
     * Attempts to delete a local file from path.
     * @param path The path of the file to delete.
     * @throws IOException If it failed to delete the file.
     */
    public static void deleteLocalFile(String path) throws Exception {
        try {
            Path newPath = Paths.get(path);
            Files.delete(newPath);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to delete local file", e);
        }
    }

    /**
     * gets the local path for the file, then creates a new file in the data package and copies the original over in the new file
     * @param path The path of the file to create.
     * @param fileType The type of the file.
     * @return new path for the song
     * @throws Exception If it fails to create the local file.
     */
    public static Path createLocalFile(String path, FileType fileType) throws Exception {
        try {
            File localFilePath = new File(path);
            String fileName = localFilePath.getName();

            String relativeFilePath = "data//";

            if (fileType == FileType.SONG) relativeFilePath += "songs//";

            if (fileType == FileType.IMAGE) relativeFilePath += "images//";

            Path originalFile = Paths.get(path);
            File file = new File(relativeFilePath+=fileName);
            Path finalFile = Paths.get(file.getPath());
            Files.copy(originalFile, finalFile, REPLACE_EXISTING);
            return finalFile;

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create song", e);
        }
    }
}
