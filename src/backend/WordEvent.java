package backend;

public class WordEvent {

    private int player;
    private String word;
    private long ms; // time

    public WordEvent(int player, String word, long ms) {
        this.player = player;
        this.word = word;
        this.ms = ms;
    }

    public int getPlayer() {
        return player;
    }

    public String getWord() {
        return word;
    }

    public long getTime() {
        return ms;
    }

}
