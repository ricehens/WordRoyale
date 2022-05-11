package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// import javax.swing.ImageIcon;
import javax.swing.JPanel;

import backend.LetterGrid;
import backend.Selection;

public class MyPanel extends JPanel {

    private int gridSize;
    private int cellSize;
    private LetterGrid grid;
    private Selection sel;
    
    public MyPanel() {
        this(4);
    }

    public MyPanel(int gridSize) {
        this.setPreferredSize(new Dimension(500, 500));
        this.gridSize = gridSize;
        cellSize = 400 / gridSize;
        grid = new LetterGrid(gridSize);

        implementMouseListeners();
    }

    public void paint(Graphics g) {
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
                g2D.drawString("" + grid.get(i, j), xStart + cellSize * 3/8, yStart + cellSize * 11/16);
            }
        }
    }

    public void setGridSize(int size) {
        gridSize = size;
    }

    private void implementMouseListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                sel = new Selection(grid, (mouseEvent.getX() - 50) / cellSize, (mouseEvent.getY() - 65) / cellSize);
                System.out.println(sel.word());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
    }
}