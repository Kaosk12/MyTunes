package BLL.Util;

import BE.Song;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {
    /**
     * Filter the list of songs in library using a search query
     * @param searchBase, the list of all songs
     * @param query, the string input used to filter
     * @return a list of songs matching the query in either title, artist or category
     */
    public List<Song> search(List<Song> searchBase, String query) {
        List<Song> searchResult = new ArrayList<>();

        for (Song song: searchBase) {
            if(compareToTitle(query, song) || compareToArtist(query, song) || compareToCategory(query, song))
            {
                searchResult.add(song);
            }
        }

        return searchResult;
    }

    /**
     * Helper method to check if the song title matches the search query
     * @param query, the string input to search for
     * @param song, the song to check
     * @return true if there is a match, false if not.
     */
    private boolean compareToTitle(String query, Song song) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Helper method to check if the song artist matches the search query
     * @param query, the string input to search for
     * @param song, the song to check
     * @return true if there is a match, false if not.
     */
    private boolean compareToArtist(String query, Song song) {
        return song.getArtist().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Helper method to check if the song category/genre matches the search query
     * @param query, the string input to search for
     * @param song, the song to check
     * @return true if there is a match, false if not.
     */
    private boolean compareToCategory(String query, Song song) {
        return song.getGenre().toLowerCase().contains(query.toLowerCase());
    }
}
