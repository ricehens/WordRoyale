package gui;

import backend.Controller;
import backend.Dictionary;

import javax.swing.*;

public class MyFrame extends JFrame {
    Controller game;
    GridPanel panel;
    ScoreboardPanel score;

    public MyFrame(Dictionary dict) {
        this(dict, 4);
    }

    public MyFrame(Dictionary dict, int gridSize) {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        game = new Controller(dict, gridSize);
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
}