package gui;

import backend.Dictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class SinglePlayerMenu extends JFrame {
    private Dictionary dict;
    private JPanel panel;

    private JFormattedTextField timeLimit;
    private JFormattedTextField gridSize;

    public SinglePlayerMenu(Dictionary dict) {
        super("Word Royale: Begin Game");
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
        JPanel fields = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel timeField = new JPanel();
        timeField.add(new JLabel("Time limit:"));
        timeLimit = new JFormattedTextField(NumberFormat.getNumberInstance());
        timeLimit.setValue(60);
        timeLimit.setColumns(4);
        timeField.add(timeLimit);
        fields.add(timeField);

        JPanel sizeField = new JPanel();
        sizeField.add(new JLabel("Grid size:"));
        gridSize = new JFormattedTextField(NumberFormat.getNumberInstance());
        gridSize.setValue(4);
        gridSize.setColumns(4);
        sizeField.add(gridSize);
        fields.add(sizeField);

        panel.add(fields);
    }

    private void addButtons() {
        JPanel buttons = new JPanel();

        JButton singlePlayer = new JButton("Play");
        singlePlayer.setPreferredSize(new Dimension(100, 100));
        singlePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SinglePlayerFrame(dict, ((Number) gridSize.getValue()).intValue(), ((Number) timeLimit.getValue()).intValue());
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
