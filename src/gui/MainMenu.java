package gui;

import javax.swing.*;

import backend.Dictionary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

/**
 * A class for the main menu display of the game.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class MainMenu extends JFrame {
    private Dictionary dict;
    private JPanel panel;

    /*
    private JFormattedTextField timeLimit;
    private JFormattedTextField gridSize;
    */

    /**
     * Constructs a main menu with a specified dictionary to use.
     * @param dict the dictionary of words for the game
     */
    public MainMenu(Dictionary dict) {
        super("Word Royale");
        this.dict = dict;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // addFields();
        addButtons();

        add(panel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
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
    */

    /**
     * Adds GUI buttons to the display.
     */
    private void addButtons() {
        JPanel buttons = new JPanel();

        JButton singlePlayer = new JButton("Solo");
        singlePlayer.setPreferredSize(new Dimension(100, 100));
        singlePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SinglePlayerMenu(dict);
                bye();
            }
        });
        buttons.add(singlePlayer);

        JButton host = new JButton("Host");
        host.setPreferredSize(new Dimension(100, 100));
        host.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HostMenu(dict);
                bye();
            }
        });
        buttons.add(host);

        JButton join = new JButton("Join");
        join.setPreferredSize(new Dimension(100, 100));
        join.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new JoinMenu(dict);
                bye();
            }
        });
        buttons.add(join);

        panel.add(buttons);
    }

    /**
     * Quits the main menu window.
     */
    private void bye() {
        setVisible(false);
        dispose();
    }

    /**
     * Main method to create the main menu.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.close();
        System.err.close();
        new MainMenu(new Dictionary());
    }

}
