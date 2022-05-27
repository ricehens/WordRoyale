package gui;

import backend.Controller;
import backend.Dictionary;

import javax.swing.*;

/**
 * The class for the single player JFrame.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class SinglePlayerFrame extends JFrame {
    Controller game;
    GridPanel panel;
    ScoreboardPanel score;

    /**
     * Constructs a single player frame.
     * @param dict the dictionary of words to use in the game
     */
    public SinglePlayerFrame(Dictionary dict) {
        this(dict, 4, 60);
    }

    /**
     * Constructs a single player frame with a specified grid size and time.
     * @param dict the dictionary of words to use in the game
     * @param gridSize the grid size to use
     * @param time the time limit to use
     */
    public SinglePlayerFrame(Dictionary dict, int gridSize, int time) {
        super("Word Royale: Single Player");
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        game = new Controller(dict, gridSize, time, 0, 1);
        score = new ScoreboardPanel(game);
        panel = new GridPanel(game);
        panel.initScoreboard(score);
        main.add(score);
        main.add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Main method for creating the single player frame.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        new SinglePlayerFrame(dict, 8, 120);
    }
}