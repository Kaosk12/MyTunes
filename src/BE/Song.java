package BE;

public class Song {

    private String title;
    private String artist;
    private String genre;
    private int time;

    public Song(String title, String artist, String genre, int time){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.time = time;

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

    public void h(){
        
    }

}
