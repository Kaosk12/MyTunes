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

    public String getTitle() {
        return title;
    }

    public int getPlayListId() {
        return playListId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    /**
     gets the time of each song and adds them all together, then
     * @return returns the total time for all the songs in the playlist.
     */
    public int getTime(){
        int totalTime = 0;
        for (Song song:songList){
            totalTime = totalTime + song.getTime();
        }
        return time = totalTime;
    }
    public int getSongAmount(){
        return songAmount = songList.size();
    }

    public List<Song> getAllSongsInPlaylist(){
        return songList;
    }

    public void addSongToPlaylist(Song song){
        songList.add(song);
    }
    public void removeSOP(Song song){
        songList.remove(song);
    }

}
