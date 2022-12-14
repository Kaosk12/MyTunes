package BE;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String genre;
    private int duration;
    private String path;
    private String coverPath;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Song(String title, String artist, String genre, int time, String path, int id, String coverPath){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = time;
        this.path = path;
        this.id = id;
        this.coverPath = coverPath;
    }

    public Song(String title, String artist, String genre, int time, String path, String coverPath){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = time;
        this.path = path;
        this.coverPath = coverPath;
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

    public String getCoverPath() { return coverPath; }

    @Override
    public String toString() {
        return title;
    }
}
