package gui;

import java.awt.*;

// import javax.swing.ImageIcon;
import javax.swing.JPanel;

import backend.LetterGrid;

public class MyPanel extends JPanel {
    // private Image image;
    private int gridSize;
    private LetterGrid grid;
    
    public MyPanel() {
        this(4);
    }

    public MyPanel(int gridSize) {
        // image = new ImageIcon("sky.png").getImage();
        this.setPreferredSize(new Dimension(500, 500));
        this.gridSize = gridSize;
        grid = new LetterGrid(gridSize);
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        // g2D.drawImage(image, 0, 0, null);
        
        // g2D.setPaint(Color.BLUE);
        // g2D.setStroke(new BasicStroke(5));
        // g2D.drawLine(0, 0, 500, 500);
        // g2D.setPaint(Color.PINK);
        // g2D.fillRect(0, 0, 100, 200);
        // g2D.setPaint(Color.CYAN);
        // g2D.drawRect(0, 200, 100, 200);

        // g2D.setPaint(Color.ORANGE);
        // g2D.drawOval(0, 0, 100, 100);

        // g2D.setPaint(Color.RED);
        // g2D.fillArc(0, 0, 100, 100, 0, 180);
        // g2D.setPaint(Color.WHITE);
        // g2D.fillArc(0, 0, 100, 100, 180, 180);

        // int[] xPoints = {150, 250, 350};
        // int[] yPoints = {300, 150, 300};
        g2D.setPaint(Color.BLUE);
        // g2D.fillPolygon(xPoints, yPoints, 3);

        g2D.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        g2D.drawString("Word Royale", 100, 50);
        
        drawLetterGrid(g2D);
    }

    public void drawLetterGrid(Graphics2D g2D) {
        char[][] board = grid.getBoard();
        int cellSize = 400 / gridSize;
        g2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN, cellSize / 2));
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int xStart = i * cellSize + (500 - cellSize * gridSize) / 2;
                int yStart = j * cellSize + (500 - cellSize * gridSize) / 2 + 15;
                g2D.drawRect(xStart, yStart, cellSize, cellSize);
                g2D.drawString("" + board[i][j], xStart + cellSize * 3/8, yStart + cellSize * 11/16);
            }
        }
    }

    public void setGridSize(int size) {
        gridSize = size;
    }
}