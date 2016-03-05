package DTO;

/*
    Song information.
 */
public class Song {
    public String id;
    public String title;
    public String artist;
    public String album;
    public String dateModified;
    public Song(String id, String title, String artist, String album, String dateModified){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.dateModified = dateModified;
    }
}
