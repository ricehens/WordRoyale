package net;

import backend.Controller;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Client extends Thread {

    private Controller game;

    private String serverAddress;
    private int port;
    private Queue<String> mailbox;

    public Client(Controller game, String serverAddress, int port) {
        this.game = game;
        this.serverAddress = serverAddress;
        this.port = port;
        mailbox = new LinkedList<>();
    }

    @Override public void run() {
        try (Socket server = new Socket(serverAddress, port);
             DataInputStream in = new DataInputStream(new BufferedInputStream(server.getInputStream()));
             DataOutputStream out = new DataOutputStream(server.getOutputStream()))
        {
            new Thread() {
                @Override public void run() {
                    String line;
                    do {
                        try {
                            line = in.readUTF();
                            System.out.println(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    } while (!line.equals("EOF"));
                }
            }.start();

            for (String msg : mailbox)
                out.writeUTF(msg);
            // out.writeUTF("EOF");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
