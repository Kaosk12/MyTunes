package GUI.Models;

import BE.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MediaModel {


    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    private Song chosenSong;

    public MediaModel(){

    }

    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * if the song is playing it will pause, else it will start playing.
     * finds the path of chosen song, the makes it a media and starts playing.
     */
    public void playMedia(Song song) {


        if(isPlaying){//if the song is playing pause it
            mediaPlayer.pause();
            isPlaying = false;
        }
        else if(chosenSong == null || chosenSong != song){//crete a new media file and plays it
            mediaPlayer = new MediaPlayer(createMedia(song));
            mediaPlayer.play();
            isPlaying = true;
            chosenSong = song;
        }else {//starts the song
            mediaPlayer.play();
            isPlaying = true;
        }
    }

    /**
     * starts the new song if the song was playing.
     * else it just switches to a new song and waits.
     * @param song
     */
    public void skipSong(Song song){
        if(isPlaying){
            mediaPlayer.pause();
            mediaPlayer = new MediaPlayer(createMedia(song));
            mediaPlayer.play();
            System.out.println(chosenSong);
        }
    }

    public Duration getCurrentTime(){
        Duration time = mediaPlayer.getCurrentTime();
        return time;
    }

    /**
     * finds the path of chosen song and creates a media form the file
     * @param song
     * @return
     */
    public Media createMedia(Song song){
        Media media;
        String dataPath = "data\\";
        File file = new File(dataPath += song.getPath());
        return media = new Media(file.toURI().toString());

    }


    /**
     * make it play previously song
     * @param song
     */
    public void previousSong(Song song) {
        skipSong(song);
    }
}
