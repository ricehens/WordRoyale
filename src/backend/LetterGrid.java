package backend;

/**
 * Represents a grid of letters, with a certain size.
 * 
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class LetterGrid {

    // char distribution
    private final char[] dist = new char[] {
            'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E',
            'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A',
            'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I',
            'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O',
            'N', 'N', 'N', 'N', 'N', 'N',
            'R', 'R', 'R', 'R', 'R', 'R',
            'T', 'T', 'T', 'T', 'T', 'T',
            'L', 'L', 'L', 'L', 'S', 'S', 'S', 'S',
            'U', 'U', 'U', 'U', 'D', 'D', 'D', 'D',
            'G', 'G', 'G', 'B', 'B', 'C', 'C', 'M', 'M', 'P', 'P',
            'F', 'F', 'H', 'H', 'V', 'V', 'W', 'W', 'Y', 'Y',
            'K', 'J', 'X', 'Q', 'Z'
    };

    // dimension
    private int dim;

    // all upper case
    private char[][] board;

    /**
     * Generates a letter grid with the specified dimension
     * @param dim the size of the board
     */
    public LetterGrid(int dim) {
        this.dim = dim;
        board = new char[dim][dim];

        do {
            generate();
        } while (!acceptable());
    }

    /**
     * Generates a letter grid with specified values
     * @param dim the size of the board
     * @param blob the String of values that go in the grid
     */
    public LetterGrid(int dim, String blob) {
        this.dim = dim;
        board = new char[dim][dim];

        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = blob.charAt(dim * i + j);
    }

    /**
     * Generates a random grid
     */
    private void generate() {
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = genChar();
    }

    /**
     * Generates a random character to go on a spot on the grid
     * @return a random character from the character distribution array
     */
    private char genChar() {
        return dist[(int) (dist.length * Math.random())];
    }

    /**
     * Checks if the current grid is acceptable
     * @return true if acceptable, false if not
     */
    private boolean acceptable() {
        return true;
    }

    /**
     * Prints the letter grid
     */
    private void print() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim - 1; j++)
                System.out.print(board[i][j] + " ");
            System.out.println(board[i][dim - 1]);
        }
    }

    /**
     * Gets a letter from a specified position on the letter grid
     * @param x row number
     * @param y column number
     * @return the letter at the specified position
     */
    public char get(int x, int y) {
        return board[x][y];
    }

    /**
     * Returns the size of the letter grid
     * @return size of the letter grid
     */
    public int size() {
        return dim;
    }

    /**
     * Checks whether a position on the grid is within the bounds
     * @param x row number
     * @param y column number
     * @return true if the position is within the grid bounds, false if not
     */
    public boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < size() && y < size();
    }

    /**
     * Main method for testing the LetterGrid class
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            LetterGrid lg = new LetterGrid(4);
            lg.print();
            System.out.println();
        }
    }

}
