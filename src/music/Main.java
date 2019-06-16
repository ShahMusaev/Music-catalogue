package music;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Catalogue cat = new Catalogue(new FileReader("C:\\Users\\Shahrom\\IdeaProjects\\music-catalogue\\src\\music\\catalogue\\Catalog.json"));

            cat.Search("vaporwave");
            cat.Search("NEWS AT 11");
            cat.Search("Goodmorning America!");
            cat.Search("2016");


        } catch(IOException e){
            System.out.println("File doesn't exist");

        }



    }
}
