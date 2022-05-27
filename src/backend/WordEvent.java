package backend;

/**
 * Represents for a word event.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class WordEvent {

    private int player;
    private String word;
    private long ms; // time

    /**
     * Constructs a word event.
     * @param player
     * @param word
     * @param ms
     */
    public WordEvent(int player, String word, long ms) {
        this.player = player;
        this.word = word;
        this.ms = ms;
    }

    /**
     * Returns the player who selected the word.
     * @return the player who selected the word
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Returns the word selected.
     * @return the word selected
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the time in milliseconds.
     * @return time in milliseconds
     */
    public long getTime() {
        return ms;
    }

}
