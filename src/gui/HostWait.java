package gui;

import backend.Dictionary;
import net.Client;
import net.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.text.NumberFormat;
import java.util.Enumeration;

public class HostWait extends JFrame {
    private Server server;
    private Client client;
    private JPanel panel;
    private boolean running = true;

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
        try {
            panel.add(new JLabel(String.format("IP: %s", ipAddress())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel conn = new JLabel(String.format("Number of connections: %d", server.getCnt()));
        panel.add(conn);
        new Thread() {
            @Override public void run() {
                while (running) {
                    conn.setText(String.format("Number of connections: %d", server.getCnt()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private String ipAddress() throws IOException {
        Socket s = new Socket();
        s.connect(new InetSocketAddress("google.com", 80));
        return s.getLocalAddress().toString();

    }

    private void addButtons() {
        JPanel buttons = new JPanel();

        JButton singlePlayer = new JButton("Begin");
        singlePlayer.setPreferredSize(new Dimension(100, 100));
        singlePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                server.begin();
                new MultiplayerFrame(client);
                bye();
            }
        });
        buttons.add(singlePlayer);

        panel.add(buttons);
    }

    private void bye() {
        setVisible(false);
        dispose();
        running = false;
    }
}
