package music;

import java.util.ArrayList;
import java.util.Iterator;

public class Genre {
    private String name;
    private ArrayList<Genre> subGenre;

    public Genre(){}

    public Genre(String name) {
        this.name = name;
        subGenre = new ArrayList<Genre>();

    }



    public void addSubGenre(Genre sub){
        subGenre.add(sub);
    }

    public String getName(){
        return name;
    }

    public Iterator getSubgeneresIterator()
    {
        return subGenre.iterator();
    }
}
