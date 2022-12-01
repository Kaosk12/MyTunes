package DAL.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFileDeleter {
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
}
