package gui;

import backend.Dictionary;
import net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinWait extends JFrame {
    private Client client;
    private JPanel panel;

    public JoinWait(Client client) {
        super("Word Royale: Join Game");
        this.client = client;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addFields();

        add(panel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addFields() {
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Please wait for the host to start the game"));
    }

    private void bye() {
        setVisible(false);
        dispose();
    }
}
