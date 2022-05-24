package net;

import backend.Controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    private Controller game;
    private int port;
    private ServerSocket serverSocket;

    public Server(Controller game, int port) throws IOException {
        this.game = game;
        serverSocket = new ServerSocket(this.port = port);
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new EchoChamber(game, socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 6969;
    }

}
