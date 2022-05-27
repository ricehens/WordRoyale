package gui;

import backend.Dictionary;
import net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class for the multiplayer game joiner waiting display.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class JoinWait extends JFrame {
    private Client client;
    private JPanel panel;

    /**
     * Constructs a join wait display.
     * @param client the server client
     */
    public JoinWait(Client client) {
        super("Word Royale: Join Game");
        this.client = client;
        client.link(this);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addFields();

        add(panel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adds a JLabel field to the display.
     */
    private void addFields() {
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Please wait for the host to start the game"));
    }

    /**
     * Quits the join wait window.
     */
    private void bye() {
        setVisible(false);
        dispose();
    }

    /**
     * Proceeds to the multiplayer game.
     */
    public void proceed() {
        new MultiplayerFrame(client);
        bye();
    }
}
