package GUI.Models;

import BE.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;

public class MediaModel {
    private MediaPlayer mediaPlayer;
    private Song selectedSong;
    private Double volume = 1.0;
    private boolean isMute;
    private boolean repeatBtnSelected;
    private boolean shuffleBtnSelected;
    private boolean isPlaying;
    private boolean isPlaylistSelected;

    /**
     * creates a mediaPlayer when the model is set, multiple methods rely on a mediaPlayer for calls.
     * @param song
     */
    public MediaModel(Song song){
        mediaPlayer = new MediaPlayer(createMedia(song));
        mediaPlayer.setVolume(getVolume());
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

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
           mediaPlayer.setVolume(getVolume());
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
        mediaPlayer.setVolume(getVolume());
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isShuffleBtnSelected() {
        return shuffleBtnSelected;
    }

    public void setShuffleBtnSelected(boolean shuffleBtnSelected) {
        this.shuffleBtnSelected = shuffleBtnSelected;
    }

    public boolean isRepeatBtnSelected() {
        return repeatBtnSelected;
    }

    public void setRepeatBtnSelected(boolean repeatBtnSelected) {
        this.repeatBtnSelected = repeatBtnSelected;
    }

    /**
     * gets the volume unless the volume is on mute, then it returns zero.
     * @return
     */
    public Double getVolume() {
        if(isMute) {
            volume = 0.0;
        }
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public boolean isMute() {
        return isMute;
    }

    /**
     * sets the mediavolume to 0 if mute and back to normal if unmute
     * @param mute
     */
    public void setMute(boolean mute) {
        isMute = mute;
        if(mute){
            mediaPlayer.setVolume(0.0);
        }else {
            mediaPlayer.setVolume(getVolume());
        }
    }
}