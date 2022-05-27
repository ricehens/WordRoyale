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

/**
 * A class for the host waiting display.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class HostWait extends JFrame {
    private Server server;
    private Client client;
    private JPanel panel;
    private boolean running = true;

    /**
     * Constructs a host wait display.
     * @param server the connected server
     * @param client the server client
     */
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

    /**
     * Adds GUI fields to the display.
     */
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

    /**
     * Gets the IP Address of the game host.
     * @return the IP Address
     * @throws IOException
     */
    private String ipAddress() throws IOException {
        Socket s = new Socket();
        s.connect(new InetSocketAddress("google.com", 80));
        return s.getLocalAddress().toString();

    }

    /**
     * Adds GUI buttons to the display.
     */
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

    /**
     * Quits the host wait window.
     */
    private void bye() {
        setVisible(false);
        dispose();
        running = false;
    }
}
