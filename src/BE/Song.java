package BE;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Song {

    private String title;
    private String artist;
    private String genre;
    private int time;
    /*private SimpleStringProperty title;
    private SimpleStringProperty authour;

    private SimpleStringProperty genre;

    private SimpleIntegerProperty time;*/

    public Song(String title, String artist, String genre, int time){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.time = time;
        /*this.title = new SimpleStringProperty(Title);
        this.authour = new SimpleStringProperty(Authour);
        this.genre = new SimpleStringProperty(genre);
        this.time = new SimpleIntegerProperty(time);*/
    }


    public String getTitle() {
        return title;
    }

    public int getTime(){
        return time;
    }



    public String getAuthour() {
        return artist;
    }
    public String getGenre() {
        return genre;
    }

}
