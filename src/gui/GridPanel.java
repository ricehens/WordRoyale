package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// import javax.swing.ImageIcon;
import javax.swing.JPanel;

import backend.Controller;
import backend.LetterGrid;
import backend.Selection;

/**
 * Represents a letter grid panel for the GUI.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class GridPanel extends JPanel {

    private int gridSize;
    private int cellSize;
    private Controller game;
    private Selection sel;

    private ScoreboardPanel score; // could have better design
    public void initScoreboard(ScoreboardPanel score) { this.score = score; }
    
    /**
     * Constructs a grid panel connected to a specified game controller.
     * @param game the Controller connected to the letter panel
     */
    public GridPanel(Controller game) {
        this.game = game;
        gridSize = game.getGrid().size();
        cellSize = 400 / gridSize;
        this.setPreferredSize(new Dimension(500, 500));

        implementMouseListeners();
    }

    /**
     * Draws the paint component.
     * @param g the Graphics to use
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clear

        Graphics2D g2D = (Graphics2D) g;

        g2D.setPaint(Color.BLUE);

        // g2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        // g2D.drawString("Word Royale", 100, 50);
        
        drawLetterGrid(g2D);
    }

    /**
     * Draws the letter grid specifically.
     * @param g2D the Graphics2D to use
     */
    public void drawLetterGrid(Graphics2D g2D) {
        int eps = 10;
        Color alt = game.color(sel);
        g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN, cellSize / 2));
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int xStart = i * cellSize + (500 - cellSize * gridSize) / 2;
                int yStart = j * cellSize + (500 - cellSize * gridSize) / 2 + 15;
                g2D.setPaint(sel != null && sel.marked(i, j) ? alt : Color.BLUE);
                g2D.drawRect(xStart + eps, yStart + eps, cellSize - 2 * eps, cellSize - 2 * eps);
                g2D.drawString("" + game.getGrid().get(i, j), xStart + cellSize * 3/8, yStart + cellSize * 11/16);
            }
        }
    }

    /**
     * Sets the grid size to a specified size.
     * @param size the specified size
     */
    public void setGridSize(int size) {
        gridSize = size;
    }

    /**
     * Takes in the mouse input.
     */
    private void implementMouseListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {}

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                int x = (mouseEvent.getX() - 50) / cellSize;
                int y = (mouseEvent.getY() - 65) / cellSize;
                /*
                int dx = Math.abs(mouseEvent.getX() - (x * cellSize + (500 - cellSize * gridSize) / 2 + cellSize / 2));
                int dy = Math.abs(mouseEvent.getY() - (y * cellSize + (500 - cellSize * gridSize) / 2 + cellSize / 2 + 15));
                if (4 * dx <= cellSize || 4 * dy <= cellSize)
                 */
                if (game.getGrid().isValid(x, y))
                    sel = new Selection(game.getGrid(), x, y);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                game.record(sel);
System.out.println(game.getScore() + " " + sel.word());
                if (score != null) score.repaint();
                sel = null;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                if (sel == null) return;
                int x = (mouseEvent.getX() - 50) / cellSize;
                int y = (mouseEvent.getY() - 65) / cellSize;
                int dx = Math.abs(mouseEvent.getX() - (x * cellSize + (500 - cellSize * gridSize) / 2 + cellSize / 2));
                int dy = Math.abs(mouseEvent.getY() - (y * cellSize + (500 - cellSize * gridSize) / 2 + cellSize / 2 + 15));
                if (4 * dx <= cellSize || 4 * dy <= cellSize)
                    sel.add(x, y);
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
    }
}