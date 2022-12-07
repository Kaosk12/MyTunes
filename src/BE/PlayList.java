package BE;

import java.util.ArrayList;
import java.util.List;

public class PlayList {
    private String title;
    private int playListId;

    //TODO TEMPORARY SOLUTION DELETE THIS\|/
    private String creatorName = "Muck in PlayList BE";
    private int time;
    private int songAmount;
    private ArrayList<Song> songList = new ArrayList<>();

    public PlayList(int playListId, String title){
        this.playListId = playListId;
        this.title = title;
    }
    public PlayList(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getPlayListId() {
        return playListId;
    }

    public void setPlayListId(int Id){
        playListId = Id;
    }

    public String getCreatorName() {
        return creatorName;
    }
    public void setCreatorName(String creatorName){
        this.creatorName = creatorName;
    }

    /**
     * Gets the time of each song and adds them all together.
     * @return The total time for all the songs in the playlist.
     */
    public int getTime(){
        int totalTime = 0;

        for (Song song:songList){
            totalTime += song.getTime();
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
