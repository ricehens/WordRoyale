package backend;

import java.util.Stack;

public class Selection {

    private LetterGrid grid;
    private Stack<Integer> row;
    private Stack<Integer> col;
    private StringBuilder sb;

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
    }

    /**
     * Adds letter at (x,y) to the word
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if successful
     */
    public boolean add(int x, int y) {
        if (!isAdj(x, y)) return false;
        if (isUndo(x, y)) {
            row.pop();
            col.pop();
            sb.deleteCharAt(getSize() - 1);
        } else {
            row.push(x);
            col.push(y);
            sb.append(grid.get(x, y));
        }
        return true;
    }

    public String word() {
        return sb.toString();
    }

    public int getSize() {
        return row.size();
    }

    private boolean isAdj(int x, int y) {
        int x0 = row.peek(), y0 = col.peek();
        for (int i = 0; i < 8; i++)
            if (x == x0 + dx[i] && y == y0 + dy[i])
                return true;
        return false;
    }

    private boolean isUndo(int x, int y) {
        int x0 = row.pop(), y0 = col.pop();
        boolean ret = getSize() > 0 && x == row.peek() && y == col.peek();
        row.push(x0); col.push(y0);
        return ret;
    }

}
