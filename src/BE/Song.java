package BE;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String genre;
    private int duration;

    private String path;

    public Song(String title, String artist, String genre, int time, String path, int id){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = time;
        this.path = path;
        this.id = id;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime(){
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }
}
