package gui;

import backend.Dictionary;
import net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * The class for the multiplayer game joiner menu display.
 */
public class JoinMenu extends JFrame {
    private Dictionary dict;
    private JPanel panel;

    private TextField name;
    private JFormattedTextField team;
    private TextField host;
    private JFormattedTextField port;

    /**
     * Constructs a join menu with a certain dictionary of words.
     * @param dict the dictionary of words for the game
     */
    public JoinMenu(Dictionary dict) {
        super("Word Royale: Join Multiplayer");
        this.dict = dict;

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel fields2 = new JPanel();
        /*
        JPanel fields1 = new JPanel();

        JPanel nameField = new JPanel();
        nameField.add(new JLabel("Display name:"));
        name = new TextField();
        name.setText("");
        name.setColumns(15);
        nameField.add(name);
        fields1.add(nameField);

         */

        JPanel teamField = new JPanel();
        teamField.add(new JLabel("Team number:"));
        team = new JFormattedTextField(NumberFormat.getNumberInstance());
        team.setValue(0);
        team.setColumns(4);
        teamField.add(team);
        fields2.add(teamField);

        //panel.add(fields1);


        JPanel hostField = new JPanel();
        hostField.add(new JLabel("Host address:"));
        host = new TextField();
        host.setText("127.0.0.1");
        host.setColumns(15);
        hostField.add(host);
        fields2.add(hostField);

        JPanel portField = new JPanel();
        portField.add(new JLabel("Port:"));
        port = new JFormattedTextField(NumberFormat.getNumberInstance());
        port.setValue(6969);
        port.setColumns(4);
        portField.add(port);
        fields2.add(portField);

        panel.add(fields2);
    }

    /**
     * Adds GUI buttons to the display.
     */
    private void addButtons() {
        JPanel buttons = new JPanel();

        JButton singlePlayer = new JButton("Join");
        singlePlayer.setPreferredSize(new Dimension(100, 100));
        singlePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int teamNo = ((Number) team.getValue()).intValue();
                int portNo = ((Number) port.getValue()).intValue();
                if (teamNo >= 0 && portNo >= 0) {
                    new JoinWait(new Client(dict, teamNo,  host.getText(), portNo));
                    bye();
                }
            }
        });
        buttons.add(singlePlayer);

        panel.add(buttons);
    }

    /**
     * Quits the join menu window.
     */
    private void bye() {
        setVisible(false);
        dispose();
    }
}
