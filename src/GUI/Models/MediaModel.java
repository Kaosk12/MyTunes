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
        if (isPlaying) {
            pauseMedia();
        }
        else if(chosenSong == null || chosenSong != song){//crete a new media file and plays it
            mediaPlayer = new MediaPlayer(createMedia(song));
            startMedia();
            chosenSong = song;
        }else {//starts the song
            startMedia();
        }
    }

    private void pauseMedia() {
        mediaPlayer.pause();
        isPlaying = false;
    }

    private void startMedia() {
        mediaPlayer.play();
        isPlaying = true;
    }

    /**
     * starts the new song if the song was playing.
     * else it just switches to a new song and waits.
     * @param song the new song to switch to.
     */
    public void skipSong(Song song) {
        mediaPlayer.pause();
        mediaPlayer = new MediaPlayer(createMedia(song));

        // If the previous song was playing, then start playing the next.
        if(isPlaying){
            mediaPlayer.play();
        }
    }

    public Duration getCurrentTime(){
        Duration time = mediaPlayer.getCurrentTime();
        return time;
    }

    /**
     * finds the path of chosen song and creates a media object from the file
     * @param song The song to get the path of.
     * @return A new media object, with the given song.
     */
    public Media createMedia(Song song){
        Media media;
        File file = new File(song.getPath());
        return media = new Media(file.toURI().toString());
    }

    public void restartSong(){
        mediaPlayer.seek(Duration.millis(0));
    }

}
