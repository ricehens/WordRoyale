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

public class GridPanel extends JPanel {

    private int gridSize;
    private int cellSize;
    private Controller game;
    private Selection sel;

    private ScoreboardPanel score; // TODO better design
    public void initScoreboard(ScoreboardPanel score) { this.score = score; }
    
    public GridPanel(Controller game) {
        this.game = game;
        gridSize = game.getGrid().size();
        cellSize = 400 / gridSize;
        this.setPreferredSize(new Dimension(500, 500));

        implementMouseListeners();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.setPaint(Color.BLUE);

        // g2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        // g2D.drawString("Word Royale", 100, 50);
        
        drawLetterGrid(g2D);
    }

    public void drawLetterGrid(Graphics2D g2D) {
        g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN, cellSize / 2));
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int xStart = i * cellSize + (500 - cellSize * gridSize) / 2;
                int yStart = j * cellSize + (500 - cellSize * gridSize) / 2 + 15;
                g2D.drawRect(xStart, yStart, cellSize, cellSize);
                g2D.drawString("" + game.getGrid().get(i, j), xStart + cellSize * 3/8, yStart + cellSize * 11/16);
            }
        }
    }

    public void setGridSize(int size) {
        gridSize = size;
    }

    private void implementMouseListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {}

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                sel = new Selection(game.getGrid(), (mouseEvent.getX() - 50) / cellSize, (mouseEvent.getY() - 65) / cellSize);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                game.record(sel);
System.out.println(game.getScore() + " " + sel.word());
                if (score != null) score.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                sel.add((mouseEvent.getX() - 50) / cellSize, (mouseEvent.getY() - 65) / cellSize);
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
    }
}