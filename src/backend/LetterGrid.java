package backend;

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
            'G', 'G', 'G',  'B', 'B', 'C', 'C', 'M', 'M', 'P', 'P',
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

    // generates a random grid
    private void generate() {
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = genChar();
    }

    private char genChar() {
        return dist[(int) (dist.length * Math.random())];
    }

    // checks if the current grid is acceptable
    private boolean acceptable() {
        return true;
    }

    private void print() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim - 1; j++)
                System.out.print(board[i][j] + " ");
            System.out.println(board[i][dim - 1]);
        }
    }

    // tester
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            LetterGrid lg = new LetterGrid(4);
            lg.print();
            System.out.println();
        }
    }

}
