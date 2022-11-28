package BE;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Song {

    private SimpleStringProperty title;
    private SimpleStringProperty authour;

    private SimpleStringProperty genre;

    private SimpleIntegerProperty time;

    public Song(String Title, String Authour, String genre, int time){
        this.title = new SimpleStringProperty(Title);
        this.authour = new SimpleStringProperty(Authour);
        this.genre = new SimpleStringProperty(genre);
        this.time = new SimpleIntegerProperty(time);
    }


    public String getTitle() {
        return title.get();
    }

    public int getTime(){
        return time.get();
    }



    public String getAuthour() {
        return authour.get();
    }
    public String getGenre() {
        return genre.get();
    }

}
