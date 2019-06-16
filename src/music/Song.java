package music;

public class Song {

    private String name;
    private Album album;
    private Artist artist;
    private Genre genre;
    private int year;

    public Song(String name, Album album, Artist artist, Genre genre, int year) {
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
    }
    public int getYear() {
        return year;
    }
    public String getAlbum() {
        return album.getName();
    }
    public String getArtist() {
        return artist.getName();
    }
    public String getGenre() {
        return genre.getName();
    }
    public String getName() {
        return name;
    }

}
