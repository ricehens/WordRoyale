package backend;

import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

// TODO connect to network
public class Controller {

    private Dictionary dict;
    private LetterGrid grid;
    private Set<String> words;
    private int cnt;
    private int score;

    private int time = 60;

    private long start;

    public Controller(Dictionary dict, int gridSize, int time) {
        this(dict, new LetterGrid(gridSize), time);
    }

    public Controller(Dictionary dict, LetterGrid grid, int time) {
        this.dict = dict;
        this.grid = grid;
        this.time = time;
        words = new TreeSet<>();

        start = System.currentTimeMillis();
    }

    /**
     * Records the selected word
     * @param sel current selection
     * @return true if word is valid and new
     */
    public boolean record(Selection sel) {
        if (timeLeft() <= 0) return false;
        String word = sel.word();
        if (dict.isWord(word))
            if (words.add(word)) {
                cnt++;
                score += score(word.length());
                return true;
            }
        return false;
    }

    public Color color(Selection sel) {
        if (timeLeft() <= 0 || sel == null)
            return Color.GRAY;
        if (words.contains(sel.word()))
            return Color.YELLOW;
        if (dict.isWord(sel.word()))
            return Color.GREEN;
        return Color.GRAY;
    }

    public LetterGrid getGrid() {
        return grid;
    }

    public int getCnt() {
        return cnt;
    }

    public int getScore() {
        return score;
    }

    public double timeLeft() {
        return Math.max(0.0, time - .001 * (System.currentTimeMillis() - start));
    }

    private int score(int wordLen) {
        if (wordLen <= 2) return 0; // this should never happen
        if (wordLen == 3) return 100;
        if (wordLen == 4) return 400;
        if (wordLen == 5) return 800;
        return 1400 + 400 * (wordLen - 6);
    }

}
