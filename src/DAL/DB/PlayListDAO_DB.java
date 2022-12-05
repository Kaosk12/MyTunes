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

    public void createPlayList() {
        // TODO
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


    public void updatePlayList() {
        // TODO
    }


    public void deletePlayList() {
        // TODO
    }
}
