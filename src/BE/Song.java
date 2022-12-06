package BE;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String genre;
    private int duration;

    private String path;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Song(String title, String artist, String genre, int time, String path, int id){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = time;
        this.path = path;
        this.id = id;

    }

    public Song(String title, String artist, String genre, int time, String path){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = time;
        this.path = path;
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


    public int getId() {
        return id;
    }

    public String getPath() { return path; }

    @Override
    public String toString() {
        return title;
    }
}
