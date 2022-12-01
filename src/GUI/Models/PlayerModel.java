package GUI.Models;


import DAL.DB.DatabaseConnector;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.sql.Connection;

public class PlayerModel {



        String dataPath = "src/recourses\\";
        Media media;
        MediaPlayer mediaPlayer;

        public void playMedia() {
            File file = new File(dataPath += "Gravity.mp3");
            media = new Media(file.toURI().toString());
            //media.getMetadata();
            mediaPlayer = new MediaPlayer(media);
            System.out.println(media.getSource());
            mediaPlayer.play();
        }

    }



