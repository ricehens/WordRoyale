package net;

import backend.Controller;
import backend.Dictionary;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

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

    public Server(Dictionary dict, int dim, int time, int numTeams, int port) {
        this.dict = dict;
        this.dim = dim;
        this.time = time;
        this.numTeams = numTeams;
        this.port = port;
        init();
    }

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

    public void begin() {
        initializing = false;
        game = new Controller(dict, dim, time, 0, numTeams);

        String msg = String.format("READY %d %d %d ", dim, time, numTeams);
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                msg += game.getGrid().get(i, j);
        EchoChamber.broadcast(msg);
    }

    // number of connections
    public int getCnt() {
        return cnt;
    }

    public static void main(String[] args) {
        int port = 6969;
    }

}
