package music;

import java.util.ArrayList;
import java.util.Iterator;

public class Artist {

    private String name;
    private ArrayList<Album> discography;

    public Artist(String name) {
        this.name = name;
        discography = new ArrayList<>();
    }

    public void addAlbum(Album album){
        discography.add(album);
    }

    public String getName() {
        return name;
    }

    public Album getAlbum(int i){
        return discography.get(i);
    }

    public Iterator getDiscographyIterator(){
        return discography.iterator();
    }
}

