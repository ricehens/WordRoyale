package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends Thread {

    private String serverAddress;
    private int port;

    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Connecting");
                Socket server = new Socket(serverAddress, port);

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("");
                DataInputStream in = new DataInputStream(server.getInputStream());
                in.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
