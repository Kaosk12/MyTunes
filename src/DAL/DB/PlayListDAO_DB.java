package DAL.DB;

import BE.PlayList;
import BE.Song;
import DAL.Interfaces.IPlaylistDAO;
import DAL.Interfaces.ISongDAO;

import java.sql.*;
import java.util.*;

public class PlayListDAO_DB implements IPlaylistDAO {
    private DatabaseConnector databaseConnector;
    private ISongDAO songDAO;

    public PlayListDAO_DB(){
        databaseConnector = new DatabaseConnector();
        songDAO = new SongDao_DB();
    }

    /**
     * Return a list of PlayList objects from the database.
     * @return A list of all Playlists.
     * @throws Exception throws exception if it fails to return a list of Playlist objects.
     */
    public List<PlayList> getAllPlayLists() throws Exception {
        ArrayList<PlayList> allPlayList = new ArrayList<>();

        try(Connection connection = databaseConnector.getConnection();
            Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM Playlists;";

            ResultSet rs = statement.executeQuery(sql);

            // Loop through rows from database result set
            while(rs.next()){
                //map database row to object
                int playListId = rs.getInt("Id");
                String title = rs.getString("Title");

                PlayList playList = new PlayList(playListId, title);

                allPlayList.add(playList);

                readAllSongsInPlayList(playList);
            }

            return allPlayList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve playlists", e);
        }
    }

    /**
     * Adds all the songs that should be in
     * this playlist, to the playlist object.
     * @param playList The playlist to get songs from ID, and add songs to.
     * @throws Exception If it fails to retrieve the songs in this playlist.
     */
    private void readAllSongsInPlayList(PlayList playList) throws Exception {
        String sql = "SELECT * FROM SongsInPlaylists WHERE PlaylistId = ?;";

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            // Bind parameters
            statement.setInt(1, playList.getPlayListId());

            ResultSet rs = statement.executeQuery();

            // We use these lists to sort the correct order of the song.
            ArrayList<Integer> tempSongsId = new ArrayList<>();
            ArrayList<Integer> tempOrders = new ArrayList<>();
            ArrayList<Integer> properOrder = new ArrayList<>();

            // Loop through rows from database result set
            while(rs.next()){
                // Map database row to object
                int songIdFromDB = rs.getInt("SongId");
                int songPlacement = rs.getInt("NumberInPlaylist");

                //we use the 3 list to keep track of the data from the table SongInPlaylist.
                //The list are used to sort the songs placement in the playlist.
                tempSongsId.add(songIdFromDB);
                tempOrders.add(songPlacement);
                properOrder.add(songPlacement);
            }

            // Sort the list to the correct order.
            Collections.sort(properOrder);

            sortSongsList(playList, tempSongsId, tempOrders, properOrder);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve songs in playlist", e);
        }
    }

    /**
     * Helper method to readAllSongsInPlaylist.
     * Adds all the required songs to the playlist in the
     * correct order.
     * @param playList The playlist to add songs to.
     * @param unsortedSongIds The unsorted list of song IDs.
     * @param unsortedSongOrders The unsorted list of song placements (indexes have to match those in unsortedSongIds).
     * @param sortedSongOrders The sorted list of song placements.
     * @throws Exception If it fails to sort the songs.
     */
    private void sortSongsList(PlayList playList,
                                     List<Integer> unsortedSongIds,
                                     List<Integer> unsortedSongOrders,
                                     List<Integer> sortedSongOrders) throws Exception{
        try {
            for (int i : sortedSongOrders) {
                // Finds the song id of the next song in the list.
                int songId = unsortedSongIds.get(unsortedSongOrders.indexOf(i));

                // Returns a song object from given song id.
                Song song =  songDAO.getSongFromId(songId);

                // Adds the song object to the playlist object.
                playList.addSongToPlaylist(song);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to sort songs in playlist", e);
        }
    }

    /**
     * Adds a song to a playlist.
     * @param playList The playlist to add a song to.
     * @param song The song to add to the playlist.
     * @throws Exception If it fails to add the song to the playlist.
     */
    public void addSongToPlayList(PlayList playList, Song song) throws Exception{
        String sql = "INSERT INTO SongsInPlaylists (SongId, PlaylistId, NumberInPlaylist) VALUES (?,?,?);";

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            //Parameters
            int songID = song.getId();
            int playlistId = playList.getPlayListId();
            int songPlacement = playList.getSongAmount() + 1;

            //Bind parameters
            statement.setInt(1, songID);
            statement.setInt(2, playlistId);
            statement.setInt(3, songPlacement);

            //Run the specified SQL statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to add new song", e);
        }
    }

    /**
     * remove the song from the playlist in the database.
     * @param playList last selected playlist
     * @param song last selected song
     * @throws Exception
     */
    public void removeSOP(PlayList playList, Song song) throws Exception {
        String sql = "DELETE FROM SongsInPlaylists WHERE SongId = ? AND PlaylistId = ?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //Bind parameters
            statement.setInt(1, song.getId());
            statement.setInt(2, playList.getPlayListId());

            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to remove song", e);
        }
    }


    /**
     * It edits the title of the playlist in the database, that matches the id of the Playlist object.
     * @param playList last selected playlist.
     * @throws Exception if it fails to edit the database.
     */
    public void updatePlayList(PlayList playList) throws Exception {
        String sql = "UPDATE Playlists SET Title = ? WHERE Id = ?;";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, playList.getTitle());
            statement.setInt(2, playList.getPlayListId());
            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update playlist", e);
        }
    }


    /**
     * It deletes a Playlist in the database.
     * the method compares the id of the Object to the id in the database.
     * @param playList selected playlist.
     * @throws Exception if it fails to delete the playlist.
     */
    public void deletePlayList(PlayList playList) throws Exception {
        String sql = "DELETE FROM Playlists WHERE Id = ?;";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //Bind parameters
            statement.setInt(1, playList.getPlayListId());

            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to remove playlist", e);
        }
    }

    /**
     * it creates a new playlist, based on the information in the given Playlist Object.
     * it returns a new Playlist Object, with the id generated by the database.
     * @param playList the new playlist.
     * @throws Exception if it fails to create a new playlist.
     */
    public void createPlayList(PlayList playList) throws Exception {
        String sql = "INSERT INTO Playlists(Title, Creator) VALUES (?,?);";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            //Bind parameters
            statement.setString(1, playList.getTitle());
            statement.setString(2, playList.getCreatorName());

            //Run the specified SQL Statement
            statement.executeUpdate();

            //Get the generated Id from the DB
            ResultSet rs = statement.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }
            playList.setPlayListId(id);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create playlist", e);
        }
    }

    /**
     * the method handles and prepears the variables it needs to call the swapSongPlacement method.
     * It will also determine if the song is moved up or down based on the boolean in its parameters.
     * @param playList the playlist the song is moved in.
     * @param song the song that is moved.
     * @param moveUp the direction the song should be moved.
     * @throws Exception if it fails to move song in the database.
     */
    public void moveSOP(PlayList playList, Song song, Boolean moveUp) throws Exception {

        List<Song> songList = playList.getAllSongsInPlaylist();

        int songIndex = songList.indexOf(song);

        int playlistId = playList.getPlayListId();

        int songId = song.getId();


        //the chosen song gets moved up
        if (moveUp && (songIndex != 0)) {
            int previousSongIndex = songList.indexOf(song)-1;
            Song previousSong = playList.getAllSongsInPlaylist().get(previousSongIndex);

            int previousSongId = previousSong.getId();

            swapSongPlacement(playlistId, songId, previousSongId, (songIndex), previousSongIndex+2);

            songList.set(songIndex, previousSong);
            songList.set(previousSongIndex, song);
        }
        //the chosen song gets moved down
        if (!moveUp && ((songIndex+1) != songList.size())) {
            int nextSongIndex = songList.indexOf(song)+1;

            Song nextSong = songList.get(nextSongIndex);

            int nextSongId = nextSong.getId();
            swapSongPlacement(playlistId, songId, nextSongId, (songIndex +2), nextSongIndex);

            songList.set(songIndex, nextSong);
            songList.set(nextSongIndex, song);
        }
    }

    /**
     *The methods function is to move 2 song objects, based on the parameters it is given.
     * first it edits the position of the chosenSong, then it edits the position of the pushedSong.
     * This way the data does not overlap in the database.
     * @param playlistId The id of the effected playlist.
     * @param chosenSongId the id of the song that got moved.
     * @param pushedSongId the id of the song that got pushed.
     * @param chosenSongNP the new position of the song that got moved.
     * @param pushedSongNP the new position of the song that got pushed.
     * @throws Exception if it fails to move the song-
     */
    private void swapSongPlacement(int playlistId, int chosenSongId, int pushedSongId, int chosenSongNP, int pushedSongNP) throws Exception {
        String sql = "UPDATE SongsInPlaylists SET NumberInplaylist = ? WHERE PlaylistId = ? AND SongId = ?;";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
                //Bind parameters of chosenSong
                statement.setInt(1, chosenSongNP);
                statement.setInt(2, playlistId);
                statement.setInt(3, chosenSongId);
                //Run the specified SQL Statement and move the chosenSong
                statement.executeUpdate();
                //Bind parameters of pushedSong
                statement.setInt(1, pushedSongNP);
                statement.setInt(2, playlistId);
                statement.setInt(3, pushedSongId);
                //Run the specified SQL Statement and move the pushedSong
                statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update playlist", e);
        }

    }
}
