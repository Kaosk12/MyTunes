package BE;

import java.util.ArrayList;
import java.util.List;

public class PlayList {
    private String title;
    private int playListId;
    private int creatorId;
    private int time;
    private int songAmount;
    private ArrayList<Song> songList = new ArrayList<>();

    public PlayList(int playListId, String title){
        this.playListId = playListId;
        this.title = title;
    }

    /**
     * returns the tile of the playlist.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the id of the playlist.
     * @return
     */
    public int getPlayListId() {
        return playListId;
    }

    /**
     * retuns the id of the user of made the playList--CURRENTLY NOT WORKING--
     * @return
     */
    public int getCreatorId() {
        return creatorId;
    }



    /**
     * gets the time of each song and adds them all together, then
     * returns the total time for all the songs in the playlist.
     * @return
     */
    public int getTime(){
        int totalTime = 0;
        for (Song song:songList){
            totalTime = totalTime + song.getTime();
        }
        return time = time + totalTime;
    }

    /**
     * Returns the amount of songs in the playlist.
     * @return
     */
    public int getSongAmount(){
        return songAmount = songList.size();
    }

    public List<Song> getAllSongsInPlaylist(){
        return songList;
    }

}
