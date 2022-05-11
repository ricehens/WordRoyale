package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class Dictionary {

    private Set<String> words;

    public Dictionary() {
        words = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File("scrabble.txt")))) {
            String line;
            while ((line = br.readLine()) != null)
                if (line.length() >= 3)
                    words.add(line);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isWord(String word) {
        return words.contains(word);
    }

}
