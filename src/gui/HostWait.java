package gui;

import backend.Dictionary;
import net.Client;
import net.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class HostWait extends JFrame {
    private Server server;
    private Client client;
    private JPanel panel;

    public HostWait(Server server, Client client) {
        super("Word Royale: Host Game");
        this.server = server;
        this.client = client;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addFields();
        addButtons();

        add(panel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addFields() {
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(String.format("Number of connections: %d", -1)));
    }

    private void addButtons() {
        JPanel buttons = new JPanel();

        JButton singlePlayer = new JButton("Begin");
        singlePlayer.setPreferredSize(new Dimension(100, 100));
        singlePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bye();
            }
        });
        buttons.add(singlePlayer);

        panel.add(buttons);
    }

    private void bye() {
        setVisible(false);
        dispose();
    }
}
