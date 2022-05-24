package backend;

import net.Client;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

// TODO connect to network
public class Controller {

    protected Dictionary dict;
    protected LetterGrid grid;
    protected Map<String, WordEvent> words;

    protected int numTeams;
    protected int[] cnt;
    protected int[] score;

    protected int time;
    protected long start;
    protected int team;

    protected Client client;

    public Controller(Dictionary dict, int gridSize, int time, int team, int numTeams) {
        this(dict, new LetterGrid(gridSize), time, team, numTeams);
    }

    public Controller(Dictionary dict, LetterGrid grid, int time, int team, int numTeams) {
        this(dict, grid, time, team, numTeams, System.currentTimeMillis());
    }

    public Controller(Dictionary dict, LetterGrid grid, int time, int team, int numTeams, long start) {
        this.dict = dict;
        this.grid = grid;
        this.time = time;
        this.team = team;

        cnt = new int[numTeams];
        score = new int[numTeams];
        words = new TreeMap<>();

        this.start = start;
    }

    // terrible design
    public void linkClient(Client client) {
        this.client = client;
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
            return receive(word, team, System.currentTimeMillis() - start);
        return false;
    }

    public boolean receive(String word, int player, long time) {
        WordEvent we = words.get(word);
        if (we != null && we.getTime() <= time)
            return false;
        WordEvent we2 = new WordEvent(player, word, time);
        words.put(word, we2);
        if (we != null) {
            cnt[we.getPlayer()]--;
            score[we.getPlayer()] -= score(word.length());
        }
        cnt[player]++;
        score[player] += score(word.length());
        if (client != null && player == team)
            client.broadcast(we2);
        return true;
    }

    public Color color(Selection sel) {
        if (timeLeft() <= 0 || sel == null)
            return Color.GRAY;
        if (words.containsKey(sel.word()))
            return Color.YELLOW;
        if (dict.isWord(sel.word()))
            return Color.GREEN;
        return Color.GRAY;
    }

    public LetterGrid getGrid() {
        return grid;
    }

    public int getCnt() {
        return getCnt(team);
    }

    public int getCnt(int team) {
        return cnt[team];
    }

    public int getScore() {
        return getScore(team);
    }

    public int getScore(int team) {
        return score[team];
    }

    public int getTeam() {
        return team;
    }

    public WordEvent getEvent(String word) {
        return words.get(word);
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
