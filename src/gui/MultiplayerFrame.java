package gui;

import backend.Controller;
import backend.Dictionary;

import javax.swing.*;

public class MultiplayerFrame extends JFrame {
    Controller game;
    GridPanel panel;
    ScoreboardPanel score;

    public MultiplayerFrame(Dictionary dict) {
        this(dict, 4, 60);
    }

    public MultiplayerFrame(Dictionary dict, int gridSize, int time) {
        super("Word Royale: Single Player");
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        game = new Controller(dict, gridSize, time);
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

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        new MultiplayerFrame(dict, 8, 120);
    }
}