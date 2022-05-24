package backend;

import net.Client;

public class MultiplayerController extends Controller {

    private Client client;

    public MultiplayerController(Dictionary dict, int gridSize, int time) {
        super(dict, gridSize, time);
    }

    public MultiplayerController(Dictionary dict, LetterGrid grid, int time) {
        super(dict, grid, time);
    }

    public MultiplayerController(Dictionary dict, LetterGrid grid, int time, int player, int numTeams) {
        super(dict, grid, time, player, numTeams);
    }

}
