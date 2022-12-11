package GUI.Models;

import BE.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MediaModel {
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;


    private boolean isPlaylistSelected;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    private Song selectedSong;

    public boolean getIsPlaylistSelected() {
        return isPlaylistSelected;
    }
    public void setIsPlaylistSelected(Boolean isPlaylistSelected){
        this.isPlaylistSelected = isPlaylistSelected;
    }

    /**
     * Pauses the song if it is playing,
     * or sets a new song and starts playing,
     * else it just starts playing the song.
     * @param song If this song isn't null, then set it as the new song to play.
     */
    public void playMedia(Song song) {

        if (!isPlaying && selectedSong == song){
            //starts the song
            startMedia();
        }
        else if(selectedSong != null){
            pauseMedia();
        }

       if (selectedSong == null || selectedSong != song){
            //crete a new media file and plays it
            mediaPlayer = new MediaPlayer(createMedia(song));
            startMedia();
        }

       selectedSong = song;
    }

    private void pauseMedia() {
        mediaPlayer.pause();
        isPlaying = false;
    }

    private void startMedia() {
        mediaPlayer.play();
        isPlaying = true;
    }



    public Duration getCurrentTime(){
        Duration time = mediaPlayer.getCurrentTime();
        return time;
    }

    /**
     * finds the path of the chosen song and creates a media object from the file.
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
