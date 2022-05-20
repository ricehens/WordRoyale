package gui;

import backend.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ScoreboardPanel extends JPanel {

    private Controller game;
    private int width;
    private int height;

    private Thread update;

    public ScoreboardPanel(Controller game) {
        this.game = game;
        this.setPreferredSize(new Dimension(width = 500, height = 120));

        update = new Thread() {
            public void run() {
                while (game.timeLeft() > 0) {
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {}
                    System.out.println(game.timeLeft());
                }
            }
        };
        update.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.BLUE);
        updateScore(g2D);
    }

    private void updateScore(Graphics2D g2D) {
        g2D.drawRect(10, 10, 480, 100);
        g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        g2D.drawString("Words: " + game.getCnt(), 50, 35);
        g2D.drawString(String.format("Time: %.1f", game.timeLeft()), 50, 105);
        g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
        g2D.drawString("Score: " + game.getScore(), 50, 75);
    }


}
