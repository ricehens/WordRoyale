package net;

import backend.Controller;
import backend.Dictionary;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Represents a multiplayer server where clients can join
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class Server {

    private Controller game;
    private Dictionary dict;
    private int dim;
    private int time;
    private int numTeams;

    private int port;
    private ServerSocket serverSocket;

    private int cnt = 0;
    Thread init;
    private boolean initializing = true;
    
    /**
     * Constructs a server with info about the game to be hosted
     * @param dict Dictionary of words
     * @param dim Dimensions of grid
     * @param time Time limit
     * @param numTeams Number of teams
     * @param port Specified port number
     */
    public Server(Dictionary dict, int dim, int time, int numTeams, int port) {
        this.dict = dict;
        this.dim = dim;
        this.time = time;
        this.numTeams = numTeams;
        this.port = port;
        init();
    }

    /**
     * Initializes a server with connections open to clients
     */
    public void init() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        init = new Thread() {
            @Override public void run() {
                while (initializing) {
                    try {
                        Socket socket = serverSocket.accept();
                        new EchoChamber(game, socket).start();
                        cnt++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        init.start();
    }

    /**
     * Starts the host game, broadcasts a ready message to clients
     */
    public void begin() {
        initializing = false;
        game = new Controller(dict, dim, time, 0, numTeams);

        String msg = String.format("READY %d %d %d ", dim, time, numTeams);
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                msg += game.getGrid().get(i, j);
        EchoChamber.broadcast(msg);
    }

    /**
     * Returns number of connections
     * @return Number of connections
     */
    public int getCnt() {
        return cnt;
    }

    public static void main(String[] args) {
        int port = 6969;
    }

}
