package gui;

import backend.Controller;
import backend.Dictionary;
import net.Client;

import javax.swing.*;

public class MultiplayerFrame extends JFrame {
    Client client;
    Controller game;
    GridPanel panel;
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
        main.add(score);
        main.add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}