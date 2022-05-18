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

    public Controller(Dictionary dict, int gridSize) {
        this(dict, new LetterGrid(gridSize));
    }

    public Controller(Dictionary dict, LetterGrid grid) {
        this.dict = dict;
        this.grid = grid;
        words = new TreeSet<>();
    }

    /**
     * Records the selected word
     * @param sel current selection
     * @return true if word is valid and new
     */
    public boolean record(Selection sel) {
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
        if (sel == null)
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

    private int score(int wordLen) {
        if (wordLen <= 2) return 0; // this should never happen
        if (wordLen == 3) return 100;
        if (wordLen == 4) return 400;
        if (wordLen == 5) return 800;
        return 1400 + 400 * (wordLen - 6);
    }

}
