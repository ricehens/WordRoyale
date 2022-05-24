package net;

import backend.Controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// https://stackoverflow.com/questions/58162611/how-can-a-server-broadcast-a-message-to-other-clients
public class EchoChamber extends Thread {

    private Controller game;
    // private static List<Socket> list = new ArrayList<>();
    private static List<DataOutputStream> outs = new ArrayList<>();
    private DataInputStream in;
    private Socket socket;

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

    public static void broadcast(String msg) {
        try {
            broadcast(msg, null);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

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
