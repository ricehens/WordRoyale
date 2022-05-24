package gui;

import backend.Dictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class HostMenu extends JFrame {
    private Dictionary dict;
    private JPanel panel;

    private TextField name;
    private JFormattedTextField team;
    private JFormattedTextField total;
    private JFormattedTextField port;

    public HostMenu(Dictionary dict) {
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

    private void addFields() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel fields1 = new JPanel();

        JPanel nameField = new JPanel();
        nameField.add(new JLabel("Display name:"));
        name = new TextField();
        name.setText("");
        name.setColumns(15);
        nameField.add(name);
        fields1.add(nameField);

        JPanel portField = new JPanel();
        portField.add(new JLabel("Port:"));
        port = new JFormattedTextField(NumberFormat.getNumberInstance());
        port.setValue(6969);
        port.setColumns(4);
        portField.add(port);
        fields1.add(portField);

        panel.add(fields1);

        JPanel fields2 = new JPanel();

        JPanel totalField = new JPanel();
        totalField.add(new JLabel("Total teams:"));
        total = new JFormattedTextField(NumberFormat.getNumberInstance());
        total.setValue(2);
        total.setColumns(4);
        totalField.add(total);
        fields2.add(totalField);

        JPanel teamField = new JPanel();
        teamField.add(new JLabel("Team number:"));
        team = new JFormattedTextField(NumberFormat.getNumberInstance());
        team.setValue(0);
        team.setColumns(4);
        teamField.add(team);
        fields2.add(teamField);


        panel.add(fields2);
    }

    private void addButtons() {
        JPanel buttons = new JPanel();

        JButton singlePlayer = new JButton("Join");
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
