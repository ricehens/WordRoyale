package gui;

import backend.Controller;
import net.Client;

import javax.swing.*;

public class MultiplayerFrame extends JFrame {
    Client client;
    Controller game;
    GridPanel panel;
    MultiplayerDataPanel words;
    ScoreboardPanel score;

    public MultiplayerFrame(Client client) {
        super("Word Royale: Multiplayer");
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        this.client = client;
        game = client.getGame();
        score = new ScoreboardPanel(game);
        panel = new GridPanel(game);
        panel.initScoreboard(score);
        words = new MultiplayerDataPanel(game);
        main.add(score);
        main.add(panel);
        main.add(words);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}