package BE;

import java.util.ArrayList;

public class PlayList {
    private String title;
    private int playListId;
    private int creatorId;
    private ArrayList<Song> songList = new ArrayList<>();

    public PlayList(int playListId, String title){
        this.playListId = playListId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayListId() {
        return playListId;
    }


    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void addSong(Song song){
        songList.add(song);
    }
    public void deleteSong(Song song){
        songList.remove(song);
    }
}
