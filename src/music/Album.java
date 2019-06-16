package music;

import java.util.ArrayList;

public class Album {
    private String name;
    private String artist;
    private int year;
    private ArrayList<Song> songs;



    public Album(String name, String artist, int year) {
        this.name = name;
        this.artist = artist;
        this.year = year;
        songs = new ArrayList<>();
    }

    public void addSong(Song song){
        songs.add(song);
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public Song getSongs(int i) {
        return songs.get(i);
    }
}
