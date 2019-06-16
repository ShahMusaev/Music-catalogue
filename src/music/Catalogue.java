package music;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;


public class Catalogue {

    private ArrayList<Artist> artists = new ArrayList<Artist>();
    private ArrayList<Genre> genres = new ArrayList<Genre>();
    private ArrayList<Song> songs = new ArrayList<Song>();
    private ArrayList<Album> albums = new ArrayList<Album>();

    public Catalogue(Reader file) {

        Object obj;
        JSONObject jo = new JSONObject();
        try {
            obj = new JSONParser().parse(file);
            jo = (JSONObject) obj;
        } catch (ParseException parse) {
            System.out.println("parsing error");
        } catch (IOException io) {
            System.out.println("IO exception");
        }

        addSong(jo);
        addGenre(jo);

    }

    private void addGenre(JSONObject jsonObj){
        boolean found = false;
        JSONArray Arrgenre = (JSONArray) jsonObj.get("genres");
        Iterator genreItr = Arrgenre.iterator();

        while(genreItr.hasNext()){
            JSONObject genre = (JSONObject) genreItr.next();
            String name = (String) genre.get("name");
            JSONArray subgenres = (JSONArray) genre.get("subgenres");
            Iterator subgenreItr = subgenres.iterator();
            Genre match_genre = new Genre();

            for(Genre _genre: genres) {
                if(_genre.getName().equals(name))
                {
                    match_genre = _genre;
                    found = true;
                }
            }
            if(found){
                addSubgenres(subgenreItr, match_genre);
            }
            if(!found){
                Genre newGenre = new Genre(name);
                genres.add(newGenre);
                addSubgenres(subgenreItr, newGenre);
            }
            found = false;
        }
    }

    private void addSubgenres(Iterator subgenreItr, Genre _genre){
        boolean match_found = false;
        while(subgenreItr.hasNext()){
            String subgenreName = (String) subgenreItr.next();
            for(Genre sub: genres) {
                if(sub.getName().equals(subgenreName)){
                    _genre.addSubGenre(sub);
                    match_found = true;
                }
            }
            if(!match_found){
                Genre newSubgenre = new Genre(subgenreName);
                _genre.addSubGenre(newSubgenre);
                genres.add(newSubgenre);
                match_found = false;
            }
        }
    }



    public void addSong(JSONObject jo) {
        JSONArray arrySongs = (JSONArray) jo.get("songs");
        Iterator ItSongs = arrySongs.iterator();


        while (ItSongs.hasNext()) {
            JSONObject song = (JSONObject) ItSongs.next();
            String name = (String) song.get("name");
            String _album = (String) song.get("album");
            String _genre = (String) song.get("genre");
            String _artist = (String) song.get("artist");


            int year = Integer.parseInt((String) song.get("year"));

            Album album = new Album(_album,_artist,year);
            Artist artist = new Artist(_artist);
            Genre genre = new Genre(_genre);



            Song newSong = new Song(name, album, artist, genre, year);
            songs.add(newSong);

            AddToAlbum(newSong);
        }

    }

    public void AddToAlbum(Song newSong) {
        boolean found = false;
        for (Album album : albums) {

            if (album.getName().equals(newSong.getAlbum())) {
                album.addSong(newSong);
                found = true;
            }
        }

        if (!found) {
            Album newAlbum = new Album(newSong.getAlbum(), newSong.getArtist(), newSong.getYear());
            newAlbum.addSong(newSong);
            albums.add(newAlbum);

            addToArtist(newAlbum);
        }


    }

    public void addToArtist(Album newAlbum) {
        boolean found = false;
        for (Artist artist : artists) {

            if (artist.getName().equals(newAlbum.getArtist())) {
                artist.addAlbum(newAlbum);
                found = true;
            }
        }

        if (!found) {
            Artist newArtist = new Artist(newAlbum.getArtist());
            newArtist.addAlbum(newAlbum);
            artists.add(newArtist);

        }

    }


    public void Search(String query) {

        ArrayList<Song> matchGenreSongs = new ArrayList<>();
        ArrayList<Song> matchYearSongs = new ArrayList<>();
        ArrayList<Song> matchSongs = new ArrayList<>();
        ArrayList<Song> matchArtists = new ArrayList<>();
        ArrayList<Song> matchAlbums = new ArrayList<>();


        for (Song song : songs) {

            if (query.equals(song.getArtist())) {
                matchArtists.add(song);
            }

            if (query.equals(song.getName())) {
                matchSongs.add(song);
            }

            if (query.equals(String.valueOf(song.getYear()))) {
                matchYearSongs.add(song);
            }

            if(song.getGenre().equals(query)){
                matchGenreSongs.add(song);
            } else{
                Genre matchGenre = null;
                for (Genre genre: genres) {
                    if(genre.getName().equals(query)){
                        matchGenre = genre;
                    }

            }
            if((matchGenre != null) && isSubgenre(song.getGenre(), matchGenre)){
                    matchGenreSongs.add(song);
                }
            }



            if (query.equals(song.getAlbum())) {
                matchAlbums.add(song);
            }
        }

        if (!matchArtists.isEmpty()) {
            System.out.println("\t\t query: " + query + "\n");

            System.out.println("Songs:");
            for (Song song : matchArtists) {
                System.out.println(song.getName() + " " + "(" + song.getYear() + ")");
            }

            System.out.println("\nAlbums:");
            for (Song song : matchArtists) {
                System.out.println(song.getAlbum());
            }

            System.out.println("\nGenres:");
            for (Song song : matchArtists) {
                System.out.println(song.getGenre());
            }
        }

        if (!matchAlbums.isEmpty()) {
            System.out.println("\t\t query: " + query + "\n");

            System.out.println("Artist:\n" + matchAlbums.get(0).getArtist());

            System.out.println("\nSong:");
            for (Song song : matchAlbums) {
                System.out.println(song.getName() + " " + "(" + song.getYear() + ")");
            }
        }

        if (!matchSongs.isEmpty()) {

            System.out.println("\t\t query: " + query + "\n");
            System.out.println("Song:\n" + matchSongs.get(0).getName());
            System.out.println("\nYear:\n" + matchSongs.get(0).getYear());
            System.out.println("\nArtist:\n" + matchSongs.get(0).getArtist());
            System.out.println("\nAlbum:\n" + matchSongs.get(0).getAlbum());
            System.out.println("\nGenre:\n" + matchSongs.get(0).getGenre());

        }

        if (!matchYearSongs.isEmpty()) {
            System.out.println("\t\t query: " + query + "\n");

            System.out.println("Songs:");
            for (Song song : matchYearSongs) {
                System.out.println(song.getName());
            }
        }

        if(!matchGenreSongs.isEmpty()){
            System.out.println("\t\t query: " + query + "\n");
            for (Song song: matchGenreSongs) {
                System.out.println(song.getName());
                System.out.println("artist: " + song.getArtist());
                System.out.println("album: " + song.getAlbum());
                System.out.println("year: " + song.getYear() + "\n");
            }
        }
    }


    private boolean isSubgenre(String songGenre, Genre query){
        Iterator subgenreItr = query.getSubgeneresIterator();
        while(subgenreItr.hasNext()){
            Genre subgenre = (Genre) subgenreItr.next();
            if(subgenre.getName().equals(songGenre) || isSubgenre(songGenre, subgenre)){
                return true;
            }
        }
        return false;
    }


}






