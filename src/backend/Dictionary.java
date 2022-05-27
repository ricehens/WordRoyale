package backend;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * The class for reading in the words in the dictionary we use for Word Royale.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class Dictionary {

    private Set<String> words;
    private static final String PATH = "../backend/scrabble.txt";

    /**
     * Constructs a Dictionary.
     */
    public Dictionary() {
        words = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("scrabble.txt")))) {
            String line;
            while ((line = br.readLine()) != null)
                if (line.length() >= 3)
                    words.add(line);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Checks if a word guess is an actual word.
     * @param word word guess
     * @return true if it is a word, false if not
     */
    public boolean isWord(String word) {
        return words.contains(word);
    }

}
