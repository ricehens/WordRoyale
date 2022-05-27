package backend;

import java.util.Stack;

/**
 * Represents a selection of letters during the swiping process.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class Selection {

    private LetterGrid grid;
    private Stack<Integer> row;
    private Stack<Integer> col;
    private StringBuilder sb;
    private boolean[][] marked;

    private final int[] dx = new int[] {1, 1, 0, -1, -1, -1, 0, 1};
    private final int[] dy = new int[] {0, 1, 1, 1, 0, -1, -1, -1};

    /**
     * Initializes a selection starting at coordinates (x, y)
     * @param grid the grid
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Selection(LetterGrid grid, int x, int y) {
        this.grid = grid;
        row = new Stack<>();
        col = new Stack<>();
        row.push(x);
        col.push(y);
        sb = new StringBuilder("" + grid.get(x, y));
        marked = new boolean[grid.size()][grid.size()];
        marked[x][y] = true;
    }

    /**
     * Adds letter at (x,y) to the word
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if successful
     */
    public boolean add(int x, int y) {
        if (!grid.isValid(x, y) || !isAdj(x, y)) return false;
        if (isUndo(x, y)) {
            marked[row.pop()][col.pop()] = false;
            sb.deleteCharAt(getSize());
        } else {
            if (!marked[x][y]) {
                row.push(x);
                col.push(y);
                sb.append(grid.get(x, y));
                marked[x][y] = true;
            }
        }
        return true;
    }

    /**
     * Checks whether a square on the grid is marked.
     * @param x row number
     * @param y column number
     * @return true if the the square is marked, false if not
     */
    public boolean marked(int x, int y) {
        return grid.isValid(x, y) && marked[x][y];
    }

    /**
     * Returns the word that the selection represents.
     * @return the String word
     */
    public String word() {
        return sb.toString();
    }

    /**
     * Returns the row size of the selection.
     * @return row size of selection
     */
    public int getSize() {
        return row.size();
    }

    /**
     * Returns whether another square is adjacent to a square.
     * @param x row number of other square
     * @param y column number of other square
     * @return true if the square is adjacent, false if not
     */
    private boolean isAdj(int x, int y) {
        int x0 = row.peek(), y0 = col.peek();
        for (int i = 0; i < 8; i++)
            if (x == x0 + dx[i] && y == y0 + dy[i])
                return true;
        return false;
    }

    /**
     * Checks whether a backtrace occurs to undo a letter in the selection.
     * @param x row number
     * @param y column number
     * @return true if it is an undo, false if not
     */
    private boolean isUndo(int x, int y) {
        int x0 = row.pop(), y0 = col.pop();
        boolean ret = getSize() > 0 && x == row.peek() && y == col.peek();
        row.push(x0); col.push(y0);
        return ret;
    }

}
