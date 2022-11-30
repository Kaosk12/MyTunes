package BE;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String genre;
    private int duration;

    private String path;

    public Song(String title, String artist, String genre, int time){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = time;

    }


    public String getTitle() {
        return title;
    }

    public int getTime(){
        return duration;
    }


    public String getArtist() {
        return artist;
    }
    public String getGenre() {
        return genre;
    }

}
