package GUI.Models;


import DAL.DB.DatabaseConnector;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.sql.Connection;

public class PlayerModel {



        String dataPath = "data\\";
        Media media;
        MediaPlayer mediaPlayer;

        public PlayerModel (){
            File file = new File(dataPath += "Gravity.mp3");
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }

        public void playMedia() {
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setVolume(1.0);

            //mediaPlayer.play();
            System.out.println(mediaPlayer.getMedia().getDuration());
            System.out.println(media.getSource());
        }
    }



