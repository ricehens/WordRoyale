package gui;

import backend.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * A class for the multiplayer data display.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class MultiplayerDataPanel extends JPanel {

    private Controller game;
    private int width;
    private int height;

    /**
     * Constructs the multiplayer data panel.
     * @param game
     */
    public MultiplayerDataPanel(Controller game) {
        this.game = game;
        game.linkPanel(this);
        this.setPreferredSize(new Dimension(width = 500, height = 230));

        /*
        update = new Thread() {
            public void run() {
                while (game.timeLeft() > 0) {
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {}
                }
            }
        };
        update.start();
        */
    }

    /**
     * Adds Graphics the paint component.
     * @param g the Graphics to use
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLUE);
        listWords(g2D);
        listScores(g2D);
    }

    /**
     * Lists the words gotten on the GUI.
     * @param g2D the Graphics2D to use
     */
    private void listWords(Graphics2D g2D) {
        g2D.drawRect(60, 10, 150, 165);
        g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        Stack<String> stack = game.getStack();
        Stack<String> tmp = new Stack<>();
        int t = Math.min(5, stack.size());
        for (int i = 0; i < t; i++) {
            g2D.drawString(stack.peek(), 80, 35 + 30 * i);
            tmp.push(stack.pop());
        }
        for (int i = 0; i < t; i++) {
            stack.push(tmp.pop());
        }

    }

    /**
     * Lists the game scores on the GUI.
     * @param g2D the Graphics2D to use
     */
    private void listScores(Graphics2D g2D) {
        g2D.drawRect(260, 10, 180, 165);
        g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        for (int i = 0; i < game.getNumTeams(); i++) {
            g2D.drawString(String.format("Team %d: %d",
                    i, game.getScore(i)), 280, 35 + 30 * i);
        }
    }


}
