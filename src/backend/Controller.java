package backend;

import net.Client;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Manages the whole backend portion of the game.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class Controller {

    private Dictionary dict;
    private LetterGrid grid;
    private Map<String, WordEvent> words;
    private Stack<String> queue;

    private int numTeams;
    private int[] cnt;
    private int[] score;
    private int indivCnt;
    private int indivScore;

    private int time;
    private long start;
    private int team;

    private Client client;

    private List<JPanel> updates;

    /**
     * Constructs a Controller with a specified grid size.
     * @param dict dictionary of words
     * @param gridSize grid size
     * @param time time on the clock
     * @param team team number
     * @param numTeams number of teams
     */
    public Controller(Dictionary dict, int gridSize, int time, int team, int numTeams) {
        this(dict, new LetterGrid(gridSize), time, team, numTeams);
    }

    /**
     * Constructs a Controller with a specified letter grid.
     * @param dict dictionary of words
     * @param grid letter grid
     * @param time time on the clock
     * @param team team number
     * @param numTeams number of teams
     */
    public Controller(Dictionary dict, LetterGrid grid, int time, int team, int numTeams) {
        this(dict, grid, time, team, numTeams, System.currentTimeMillis());
    }

    /**
     * Constructs a Controller with a specified letter grid and ?????.
     * @param dict dictionary of words
     * @param grid letter grid
     * @param time time on the clock
     * @param team team number
     * @param numTeams number of teams
     * @param start ?????
     */
    public Controller(Dictionary dict, LetterGrid grid, int time, int team, int numTeams, long start) {
        this.dict = dict;
        this.grid = grid;
        this.time = time;
        this.team = team;
        this.numTeams = numTeams;

        cnt = new int[numTeams];
        score = new int[numTeams];
        words = new TreeMap<>();
        /*
        queue = new PriorityQueue<>((x, y) ->
                ((words.containsKey(x) ? -Long.valueOf(words.get(x).getTime()).intValue() : -1)
                        + (words.containsKey(y) ? Long.valueOf(words.get(y).getTime()).intValue() : -1)));
         */
        queue = new Stack<>();
        updates = new ArrayList<>();
        this.start = start;
    }

    /**
     * Links the client to a specified client.
     * @param client client to link
     */
    public void linkClient(Client client) {
        this.client = client;
    }

    /**
     * Links to a specified panel.
     * @param panel panel to link
     */
    public void linkPanel(JPanel panel) {
        updates.add(panel);
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
            return receive(true, word, team, System.currentTimeMillis() - start);
        return false;
    }

    public boolean receive(boolean self, String word, int player, long time) {
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
        if (self) {
            indivCnt++;
            indivScore += score(word.length());
        }
        if (client != null && player == team)
            client.broadcast(we2);
        queue.push(word);
        for (JPanel p : updates) p.repaint();
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

    /**
     * Returns the letter grid.
     * @return the letter grid
     */
    public LetterGrid getGrid() {
        return grid;
    }

    /**
     * Returns a individual count.
     * @return the individual count
     */
    public int getCnt() {
        return indivCnt;
    }

    /**
     * Returns the count of a team.
     * @param team team of players
     * @return the count of the specified team
     */
    public int getCnt(int team) {
        return cnt[team];
    }

    /**
     * Returns an individual score.
     * @return the individual score.
     */
    public int getScore() {
        return indivScore;
    }

    /**
     * Returns the score of a team.
     * @param team team of players
     * @return the team's score
     */
    public int getScore(int team) {
        return score[team];
    }

    /**
     * Returns the team.
     * @return the team
     */
    public int getTeam() {
        return team;
    }

    /**
     * Returns the number of teams playing.
     * @return the number of teams playing
     */
    public int getNumTeams() {
        return numTeams;
    }

    /**
     * Returns a WordEvent for one guess of a word.
     * @param word word
     * @return
     */
    public WordEvent getEvent(String word) {
        return words.get(word);
    }

    /**
     * Returns the time left in the game.
     * @return the time left in the game
     */
    public double timeLeft() {
        return Math.max(0.0, time - .001 * (System.currentTimeMillis() - start));
    }

    public Stack<String> getStack() {
        return queue;
    }

    /**
     * Returns the score for a certain word length.
     * @param wordLen length of the word
     * @return the score associated with that word
     */
    private int score(int wordLen) {
        if (wordLen <= 2) return 0; // this should never happen
        if (wordLen == 3) return 100;
        if (wordLen == 4) return 400;
        if (wordLen == 5) return 800;
        return 1400 + 400 * (wordLen - 6);
    }

}
