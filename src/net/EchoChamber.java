package net;

import backend.Controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a communication object between the server and connected clients
 * Reference link: https://stackoverflow.com/questions/58162611/how-can-a-server-broadcast-a-message-to-other-clients
 * @author Eric Shen
 * @author Andrew Yuan
 * @author Luke Zhao
 * @version 05-23-2022
 */
public class EchoChamber extends Thread {

    private Controller game;
    private static List<DataOutputStream> outs = new ArrayList<>();
    private DataInputStream in;
    private Socket socket;

    /**
     * Constructs a communication object between the server and connected clients
     * @param game Controller of game being hosted
     * @param socket Socket of server, accesses messages from the server
     */
    public EchoChamber(Controller game, Socket socket) {
        this.game = game;
        // list.add(this.socket = socket);
        this.socket = socket;
        try {
            outs.add(new DataOutputStream(socket.getOutputStream()));
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overrides base class (Thread) run method
     */
    @Override public void run() {
        try {
            String line = null;
            do {
                //if (in.available() > 0) {
                    line = in.readUTF().trim();
                    System.out.println("[Received] " + line);
                    broadcast(line, socket);
                //}
            } while (line == null || !line.equals("done"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Broadcasts message from server to clients
     * @param msg Specified message to be broadcasted
     */
    public static void broadcast(String msg) {
        try {
            broadcast(msg, null);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    /**
     * Broadcasts message from server to clients
     * @param msg Specified message to be broadcasted
     * @param avoid Server socket
     * @throws IOException Input/output error
     */
    public static void broadcast(String msg, Socket avoid) throws IOException {
        System.out.println("broadcasting to " + outs.size());
        /*
        for (Socket dest : list) {
            if (dest == avoid) continue;
            DataOutputStream out = new DataOutputStream(dest.getOutputStream());
            out.writeUTF(msg);
        }
         */
        for (DataOutputStream out : outs) {
            out.writeUTF(msg);
        }
    }

}
